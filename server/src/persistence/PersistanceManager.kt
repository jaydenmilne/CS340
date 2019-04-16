package persistence
import IPersistenceManager
import ICommand
import serializedCmdDTO
import ICommandDAO
import IUserDAO
import IGameDAO
import commands.INormalServerCommand
import models.Game
import models.Games
import models.User
import models.Users

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
        getUserDAO().loadUsers().forEach {
            Users.addUser(it as User)
        }

        getGameDAO().loadGames().forEach {
            Games.loadGame(it as Game)
        }

        getCommandDAO().loadCommands().forEach {
            val user = Users.getUserById(it.userId)!!
            val command = it.command as INormalServerCommand
            command.execute(user)
        }

        // Clear outgoing queues, server should now be caught up with the client
        Users.getUsers().forEach {
            it.queue.clear()
        }
    }
}