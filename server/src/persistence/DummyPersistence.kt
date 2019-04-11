package persistence

import ICommand
import models.Game
import models.User
import IPersistenceManager
import ICommandDAO
import IUserDAO
import IGameDAO

class DummyPersistenceManager: IPersistenceManager {
    override fun openTransaction() {}

    override fun closeTransaction(commit: Boolean) {}

    override fun getCommandDAO(): ICommandDAO {}

    override fun getUserDAO(): IUserDAO {}

    override fun getGameDAO(): IGameDAO {}

    override fun initialize(): Boolean {}
}

class DummyCommandDAO: ICommandDAO {
    override fun persistCommand(command: ICommand, gameID: Int) {}

    override fun loadCommands(): List<ICommand> {
        return listOf()
    }
}

class DummyUserDAO: IUserDAO {
    override fun persistUser(user: User) {}

    override fun loadUsers(): List<User> {
        return listOf()
    }
}
class DummyGameDAO: IGameDAO {
    override fun persistGame(game: Game) {}

    override fun loadGames(): List<Game> {
        return listOf()
    }
}