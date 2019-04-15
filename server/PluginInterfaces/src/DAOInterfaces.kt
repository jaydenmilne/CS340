import java.lang.RuntimeException

interface ICommandDAO {
    fun persistCommand(command: ICommand, gameID: Int)
    fun loadCommands(persistenceManager: IPersistenceManager): List<ICommand>
    fun clearCommandsForGame(gameID: Int)
}

interface IGameDAO {
    fun persistGame(game: IGame)
    fun loadGames(persistenceManager: IPersistenceManager): List<IGame>
}

interface IUserDAO {
    fun persistUser(user: IUser)
    fun loadUsers(persistenceManager: IPersistenceManager): List<IUser>
}

class DatabaseException(override val message: String): RuntimeException(message)