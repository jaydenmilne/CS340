package persistence

import ICommand
import IUser
import IGame
import IPersistenceManager
import ICommandDAO
import IUserDAO
import IGameDAO
import serializedCmdDTO


class DummyPersistenceManager(): IPersistenceManager {
    override fun openTransaction() {}

    override fun closeTransaction(commit: Boolean) {}

    override fun getCommandDAO(): ICommandDAO { return DummyCommandDAO(this)}

    override fun getUserDAO(): IUserDAO { return DummyUserDAO(this) }

    override fun getGameDAO(): IGameDAO { return DummyGameDAO(this) }

    override fun initialize(): Boolean { return true }


    override fun clear(): Boolean { return true }
}

class DummyCommandDAO(persistenceManager: IPersistenceManager): ICommandDAO(persistenceManager) {
    override fun persistCommand(command: serializedCmdDTO, gameID: Int) {}

    override fun loadCommands(): List<serializedCmdDTO> {
        return listOf()
    }

    override fun clearCommandsForGame(gameID: Int) {}
}

class DummyUserDAO(persistenceManager: IPersistenceManager): IUserDAO(persistenceManager) {
    override fun persistUser(user: IUser) {}

    override fun loadUsers(): List<IUser> {
        return listOf()
    }
}
class DummyGameDAO(persistenceManager: IPersistenceManager): IGameDAO(persistenceManager) {
    override fun persistGame(game: IGame) {}

    override fun loadGames(): List<IGame> {
        return listOf()
    }
}