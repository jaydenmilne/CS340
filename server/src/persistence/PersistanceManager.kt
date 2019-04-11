package persistence
import IPersistenceManager
import ICommandDAO
import IUserDAO
import IGameDAO

object PersistenceManager : IPersistenceManager {
    private var persistanceManager : IPersistenceManager = DummyPersistenceManager()

    fun loadPersistanceManager(manager : IPersistenceManager) {
        persistanceManager = manager
    }

    override fun openTransaction() {
        persistanceManager.openTransaction()
    }

    override fun closeTransaction(commit: Boolean) {
        persistanceManager.closeTransaction(commit)
    }

    override fun getCommandDAO(): ICommandDAO {
        return persistanceManager.getCommandDAO()
    }

    override fun getUserDAO(): IUserDAO {
        return persistanceManager.getUserDAO()
    }

    override fun getGameDAO(): IGameDAO {
        return persistanceManager.getGameDAO()
    }

    override fun initialize(): Boolean {
        return persistanceManager.initialize()
    }

}