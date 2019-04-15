import IPersistenceManager

class FlatFilePlugin : IPersistenceManager {

    override fun openTransaction() {
        TODO("not implemented")
    }

    override fun closeTransaction(commit: Boolean) {
        TODO("not implemented")
    }

    override fun getCommandDAO(): ICommandDAO {
        TODO("not implemented")
    }

    override fun getUserDAO(): IUserDAO {
        TODO("not implemented")
    }

    override fun getGameDAO(): IGameDAO {
        TODO("not implemented")
    }

    override fun initialize(): Boolean {
        TODO("not implemented")
    }

    override fun clear(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}