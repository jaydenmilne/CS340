package models

import models.Games.games

class Message(val userId: Int, val gameId: Int, val message: String) {
    val username = Users.getUserById(userId)!!.username
    val sequenceNum: Int = games[gameId]!!.getNextMessageId()
}