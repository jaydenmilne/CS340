package models

private var nextGameId = -1

fun getNextGameID(): Int {
    nextGameId++
    return nextGameId
}

object Games {
    var games = mutableMapOf<Int, Game>()
}

class Game(var name: String) {

    val gameID = getNextGameID()
    var players = mutableSetOf<User>()
    var started = false
}