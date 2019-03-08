package models

import models.Games.games

class Message(val userId : Int, val username: String, val message: String, var sequenceNum: Int) {
    constructor() : this(-1, "", "", -1)
}
