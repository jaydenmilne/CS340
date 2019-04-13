interface IPersistenceManager {
    fun openTransaction()
    fun closeTransaction(commit: Boolean)
    fun getCommandDAO(): ICommandDAO
    fun getUserDAO(): IUserDAO
    fun getGameDAO(): IGameDAO
    fun initialize(): Boolean
}