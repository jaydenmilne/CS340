package test

import FlatFilePlugin
import kotlin.test.assertEquals


fun openDB(): FlatFilePlugin{
    val db = FlatFilePlugin()
    db.initialize()
    db.clean()
    db.openTransaction()
    return db
}


fun testDAOSerializationAndDeserialization(){
    val db = openDB()

    val command = "Command1"
    val user = "User1"
    val game = "Game1"

    val commandDAO = db.getCommandDAO()
    val userDAO = db.getUserDAO()
    val gameDAO = db.getGameDAO()

    commandDAO.persistCommand(command, 0)
    userDAO.persistUser(user)
    gameDAO.persistGame(game)

    db.closeTransaction(true)

    val retrievedCommand = commandDAO.loadCommands(db).first() as String
    val retrievedUser = userDAO.loadUsers(db).first() as String
    val retrievedGame = gameDAO.loadGames(db).first() as String

    assertEquals(command, retrievedCommand)
    assertEquals(user, retrievedUser)
    assertEquals(game, retrievedGame)
}

fun testCommitFalse(){
    val db = openDB()

    val command = "Command1"
    val user = "User1"
    val game = "Game1"

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


fun main(args: Array<String>){
    testDAOSerializationAndDeserialization()
    testCommitFalse()
}