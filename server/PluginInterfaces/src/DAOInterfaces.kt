import java.lang.RuntimeException

abstract class ICommandDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistCommand(command: ICommand, gameID: Int)
    abstract fun loadCommands(): List<ICommand>
    abstract fun clearCommandsForGame(gameID: Int)
}

abstract class IGameDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistGame(game: IGame)
    abstract fun loadGames(): List<IGame>
}

abstract class IUserDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistUser(user: IUser)
    abstract fun loadUsers(): List<IUser>
}

class DatabaseException(override val message: String): RuntimeException(message)