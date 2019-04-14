package persistence
import IPersistenceManager
import ICommand
import ICommandDAO
import IUserDAO
import IGameDAO
import models.Games
import models.Users

object PersistenceManager : IPersistenceManager {
    private var persistenceManager : IPersistenceManager = DummyPersistenceManager()
    private var commandsPersistedCounter = 0
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
        clear()
        openTransaction()

        val userDAO = getUserDAO()
        val gameDAO = getGameDAO()

        for (user in Users.getUsers()) {
            userDAO.persistUser(user)
        }

        for (game in Games.getGames()) {
            gameDAO.persistGame(game)
        }

        closeTransaction(true)
    }

    fun saveCommand(command: ICommand, gameId: Int) {
        commandsPersistedCounter++

        if ((commandsPersistedCounter % commandsBetweenCheckpoints) == 0) {
            saveCheckpoint()
            commandsPersistedCounter = 0
        } else {
            openTransaction()
            getCommandDAO().persistCommand(command, gameId)
            closeTransaction(true)
        }
    }
}