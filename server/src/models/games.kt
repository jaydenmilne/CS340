package models

import commands.CommandException

private var nextGameId = -1

fun getNextGameID(): Int {
    nextGameId++
    return nextGameId
}

object Games {
    var games = mutableMapOf<Int, Game>()

    /**
     * Removes the user from any pending games. Throws a CommandException if they are in a game that has already
     * started.
     */
    fun removeUserFromGames(user : User) {
        // Check to see if the user is already in a game
        for (id_game in Games.games) {
            val game = id_game.value
            if (user in game.players) {
                // If the user is already in a started game, we have issues
                if (game.started) {
                    throw CommandException("JoinGameCommand: User is in a started game but tried to join another!")
                } else {
                    // The user is in a game that hasn't started yet - remove the player from that game
                    game.players.remove(user)
                }
            }
        }

    }
}

class Game(var name: String) {
    val gameID = getNextGameID()
    var players = mutableSetOf<User>()
    var started = false
}