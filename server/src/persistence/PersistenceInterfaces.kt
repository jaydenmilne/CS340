package persistence

import commands.ICommand
import models.Game
import models.User

var manager: IPersistenceManager = DummyPersistenceManager()

interface IPersistenceManager {
    fun openTransaction()
    fun closeTransaction(commit: Boolean)
    fun getCommandDAO(): ICommandDAO
    fun getUserDAO(): IUserDAO
    fun getGameDAO(): IGameDAO
    fun initialize(): Boolean
}

interface ICommandDAO {
    fun persistCommand(command: ICommand, gameID: Int)
    fun loadCommands(): List<ICommand>
}

interface IGameDAO {
    fun persistGame(game: Game)
    fun loadGames(): List<Game>
}

interface IUserDAO {
    fun persistUser(user: User)
    fun loadUsers(): List<User>
}