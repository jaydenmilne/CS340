
/* Parent Classes that models will inherit from */
open class IGame(val gameId:Int){ }
open class IUser(val userId:Int){ }

interface ICommandDAO {
    fun persistCommand(command: ICommand, gameID: Int)
    fun loadCommands(): List<ICommand>
}

interface IGameDAO {
    fun persistGame(game: IGame)
    fun loadGames(): List<IGame>
}

interface IUserDAO {
    fun persistUser(user: IUser)
    fun loadUsers(): List<IUser>
}