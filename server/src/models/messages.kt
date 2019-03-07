package models

import models.Games.games

class Message(val username: String, val gameId: Int, val message: String) {
    @Transient val userId = Users.getUserByUsername(username)!!.userId
    val sequenceNum: Int = games[gameId]!!.getNextMessageId()
}