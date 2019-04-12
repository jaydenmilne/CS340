package persistence

import ICommand
import IUser
import IGame
import IPersistenceManager
import ICommandDAO
import IUserDAO
import IGameDAO

class DummyPersistenceManager: IPersistenceManager {
    override fun openTransaction() {}

    override fun closeTransaction(commit: Boolean) {}

    override fun getCommandDAO(): ICommandDAO { return DummyCommandDAO()}

    override fun getUserDAO(): IUserDAO { return DummyUserDAO() }

    override fun getGameDAO(): IGameDAO { return DummyGameDAO() }

    override fun initialize(): Boolean { return true }
}

class DummyCommandDAO: ICommandDAO {
    override fun persistCommand(command: ICommand, gameID: Int) {}

    override fun loadCommands(): List<ICommand> {
        return listOf()
    }
}

class DummyUserDAO: IUserDAO {
    override fun persistUser(user: IUser) {}

    override fun loadUsers(): List<IUser> {
        return listOf()
    }
}
class DummyGameDAO: IGameDAO {
    override fun persistGame(game: IGame) {}

    override fun loadGames(): List<IGame> {
        return listOf()
    }
}