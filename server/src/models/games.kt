package models

object Games {
    var games = mutableMapOf<Int, Game>()
}

class Game(var gameid: Int, var players: Set<Player>, var name: String, var started: Boolean) {

    fun hasPlayer(player: Player): Boolean {
        return players.contains(player)
    }
}