import java.awt.font.ImageGraphicAttribute
import java.lang.RuntimeException

abstract class ICommandDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistCommand(command: serializedCmdDTO, gameID: Int)
    abstract fun loadCommands(): List<serializedCmdDTO>
    abstract fun clearCommandsForGame(gameID: Int)
}

abstract class IGameDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistGame(game: IGame)
    abstract fun removeGame(game: IGame)
    abstract fun loadGames(): List<IGame>
}

abstract class IUserDAO(val persistenceManager: IPersistenceManager) {
    abstract fun persistUser(user: IUser)
    abstract fun loadUsers(): List<IUser>
}

class DatabaseException(override val message: String): RuntimeException(message)