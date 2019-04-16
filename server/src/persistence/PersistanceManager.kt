package persistence
import IPersistenceManager
import ICommand
import serializedCmdDTO
import ICommandDAO
import IUserDAO
import IGameDAO
import commands.INormalServerCommand
import models.*
import java.io.InvalidClassException

object PersistenceManager : IPersistenceManager {
    private var persistenceManager : IPersistenceManager = DummyPersistenceManager()
    private var commandsPersistedCounter = mutableMapOf<Int, Int>()
    private var commandsBetweenCheckpoints = 1

    fun loadPersistenceManager(manager : IPersistenceManager) {
        persistenceManager = manager
    }

    override fun openTransaction() {
        persistenceManager.openTransaction()
    }

    override fun closeTransaction(commit: Boolean) {
        persistenceManager.closeTransaction(commit)
    }

    override fun getCommandDAO(): ICommandDAO {
        return persistenceManager.getCommandDAO()
    }

    override fun getUserDAO(): IUserDAO {
        return persistenceManager.getUserDAO()
    }

    override fun getGameDAO(): IGameDAO {
        return persistenceManager.getGameDAO()
    }

    override fun initialize(): Boolean {
        return persistenceManager.initialize()
    }

    override fun clear(): Boolean {
        return persistenceManager.clear()
    }

    fun setCommandsBetweenCheckpoints(commandsBetweenCheckpoints: Int) {
        this.commandsBetweenCheckpoints = commandsBetweenCheckpoints
        println("Using N=$commandsBetweenCheckpoints commands between checkpoints")
    }

    fun saveCheckpoint() {
        openTransaction()
        clear()

        val userDAO = getUserDAO()
        val gameDAO = getGameDAO()
        val commandDAO = getCommandDAO()


        for (user in Users.getUsers()) {
            userDAO.persistUser(user)
        }

        for (game in Games.getGames()) {
            gameDAO.persistGame(game)
        }

        closeTransaction(true)
    }

    fun saveCheckpoint(gameId: Int) {
        openTransaction()

        val userDAO = getUserDAO()
        val gameDAO = getGameDAO()
        val commandDAO = getCommandDAO()

        commandDAO.clearCommandsForGame(gameId)

        for (user in Users.getUsers()) {
            userDAO.persistUser(user)
        }

        gameDAO.persistGame(Games.games[gameId]!!)

        closeTransaction(true)
    }

    fun saveCommand(command: serializedCmdDTO, gameId: Int) {
        commandsPersistedCounter[gameId] = 1 + (commandsPersistedCounter[gameId] ?: 0)

        if (((commandsPersistedCounter[gameId] ?: 0) % commandsBetweenCheckpoints) == 0) {
            saveCheckpoint(gameId)
            commandsPersistedCounter[gameId] = 0
        } else {
            openTransaction()
            getCommandDAO().persistCommand(command, gameId)
            closeTransaction(true)
        }
    }

    fun saveUsers() {
        val userDAO = getUserDAO()
        openTransaction()
        for (user in Users.getUsers()) {
            userDAO.persistUser(user)
        }
        closeTransaction(true)
    }

    fun restoreDB(){
        try {
            println("Loading database...")
            getUserDAO().loadUsers().forEach {
                val user = it as User
                Users.loadUser(user)
                AuthTokens.loadToken(user.authToken, user)
            }

            println("\t- Loaded ${Users.getUsers().size} users")

            getGameDAO().loadGames().forEach {
                Games.loadGame(it as Game)
            }

            println("\t- Loaded ${Games.getGames().size} games")

            println("\t- Loading commands...")
            val commandDao = getCommandDAO()
            commandDao.loadCommands().forEach {
                val user = Users.getUserById(it.userId)!!
                val command = it.command as INormalServerCommand
                println("\t\t- Re-executing ${command.command} for user ${user.username}")
                command.execute(user)
            }

            // Clear commands for all games
            for (game in Games.getGames()) {
                commandDao.clearCommandsForGame(game.gameId)
            }

            // Clear outgoing queues, server should now be caught up with the client
            Users.getUsers().forEach {
                it.queue.clear()
            }

            println("Database loaded!")
        } catch (e : Exception) {
            System.err.println("Unhandled exception loading database!")
            clear() // automatically delete, but crash anyway because the server is in an undefined state
            throw e
        }

    }
}