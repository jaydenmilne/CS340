package test

import FlatFilePlugin
import kotlin.test.assertEquals
import ICommand
import IUser
import IGame

class DummyCommand(val name: String, override val command: String): ICommand {

}

class DummyUser(val name: String, override val userId: Int): IUser {

}

class DummyGame(val name: String, override val gameId: Int): IGame {

}


fun openDB(): FlatFilePlugin{
    val db = FlatFilePlugin()
    db.initialize()
    db.clean()
    db.openTransaction()
    return db
}


fun testDAOSerializationAndDeserialization(){
    val db = openDB()

    val command = DummyCommand("Command1", "DummyCommand")
    val user = DummyUser("User1", 1)
    val game = DummyGame("Game1", 1)

    val commandDAO = db.getCommandDAO()
    val userDAO = db.getUserDAO()
    val gameDAO = db.getGameDAO()

    commandDAO.persistCommand(command, 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(true)

    val retrievedCommand = commandDAO.loadCommands(db).first()
    val retrievedUser = userDAO.loadUsers(db).first()
    val retrievedGame = gameDAO.loadGames(db).first()

    assertEquals(command.command, retrievedCommand.command)
    assertEquals(user.userId, retrievedUser.userId)
    assertEquals(game.gameId, retrievedGame.gameId)
}

fun testCommitFalse(){
    val db = openDB()

    val command = DummyCommand("Command1", "DummyCommand")
    val user = DummyUser("User1", 1)
    val game = DummyGame("Game1", 1)

    val commandDAO = db.getCommandDAO()
    val userDAO = db.getUserDAO()
    val gameDAO = db.getGameDAO()

    commandDAO.persistCommand(command, 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(false)

    val retrievedCommands = commandDAO.loadCommands(db)
    val retrievedUsers = userDAO.loadUsers(db)
    val retrievedGames = gameDAO.loadGames(db)

    assertEquals(0, retrievedCommands.size)
    assertEquals(0, retrievedUsers.size)
    assertEquals(0, retrievedGames.size)
}

fun testAdditionalParametersSerialize(){
    val db = openDB()

    val command = DummyCommand("Command1", "DummyCommand")
    val user = DummyUser("User1", 1)
    val game = DummyGame("Game1", 1)

    val commandDAO = db.getCommandDAO()
    val userDAO = db.getUserDAO()
    val gameDAO = db.getGameDAO()

    commandDAO.persistCommand(command, 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(true)

    val retrievedCommand = commandDAO.loadCommands(db).first() as DummyCommand
    val retrievedUser = userDAO.loadUsers(db).first() as DummyUser
    val retrievedGame = gameDAO.loadGames(db).first() as DummyGame

    assertEquals(command.name, retrievedCommand.name)
    assertEquals(user.name, retrievedUser.name)
    assertEquals(game.name, retrievedGame.name)
}

fun main(args: Array<String>){
    testDAOSerializationAndDeserialization()
    testCommitFalse()
    testAdditionalParametersSerialize()
}