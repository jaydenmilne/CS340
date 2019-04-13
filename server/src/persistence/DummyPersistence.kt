package persistence

import commands.ICommand
import models.Game
import models.User

class DummyPersistenceManager: IPersistenceManager {
    override fun openTransaction() {}

    override fun closeTransaction(commit: Boolean) {}

    override fun getCommandDAO(): ICommandDAO {return DummyCommandDAO()}

    override fun getUserDAO(): IUserDAO {return DummyUserDAO()}

    override fun getGameDAO(): IGameDAO {return DummyGameDAO()}

    override fun initialize(): Boolean {return true}
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