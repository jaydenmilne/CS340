package test

import FlatFilePlugin
import kotlin.test.assertEquals
import ICommand
import serializedCmdDTO
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
    db.clear()
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

    commandDAO.persistCommand(serializedCmdDTO(command, 0), 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(true)

    val retrievedCommand = commandDAO.loadCommands().first()
    val retrievedUser = userDAO.loadUsers().first()
    val retrievedGame = gameDAO.loadGames().first()

    assertEquals(command.command, retrievedCommand.command.command)
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

    commandDAO.persistCommand(serializedCmdDTO(command, 0), 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(false)

    val retrievedCommands = commandDAO.loadCommands()
    val retrievedUsers = userDAO.loadUsers()
    val retrievedGames = gameDAO.loadGames()

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

    commandDAO.persistCommand(serializedCmdDTO(command, 0), 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(true)

    val retrievedCommand = commandDAO.loadCommands().first().command as DummyCommand
    val retrievedUser = userDAO.loadUsers().first() as DummyUser
    val retrievedGame = gameDAO.loadGames().first() as DummyGame

    assertEquals(command.name, retrievedCommand.name)
    assertEquals(user.name, retrievedUser.name)
    assertEquals(game.name, retrievedGame.name)
}

fun testRemoveCommands() {
    val db = openDB()

    val command1 = DummyCommand("Command1", "DummyCommand")
    val command2 = DummyCommand("Command2", "DummyCommand")
    val command3 = DummyCommand("Command3", "DummyCommand")

    val commandDAO = db.getCommandDAO()

    commandDAO.persistCommand(serializedCmdDTO(command1, 0), 1)
    commandDAO.persistCommand(serializedCmdDTO(command1, 0), 0)
    commandDAO.persistCommand(serializedCmdDTO(command2, 0), 0)
    commandDAO.persistCommand(serializedCmdDTO(command3, 0), 0)

    db.closeTransaction(true)

    db.openTransaction()
    commandDAO.clearCommandsForGame(0)
    db.closeTransaction(true)

    val retrievedCommands = commandDAO.loadCommands()

    assertEquals(1, retrievedCommands.size)
}

fun main(args: Array<String>){
    testDAOSerializationAndDeserialization()
    testCommitFalse()
    testAdditionalParametersSerialize()
    testRemoveCommands()
}