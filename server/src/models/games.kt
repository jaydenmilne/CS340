package models

import commands.CommandException
import commands.INormalClientCommand

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
    fun removeUserFromGames(user: User) {
        for (game in Games.games.values) {
            if (user in game.players) {
                if (game.started) {
                    throw CommandException("JoinGameCommand: User is in a started game but tried to join another!")
                } else {
                    game.players.remove(user)
                }
            }
        }

    }
}

class Game(var name: String) {
    val gameId = getNextGameID()
    var players = mutableSetOf<User>()
    var started = false

    fun broadcast(command: INormalClientCommand) {
        for (player in players) {
            player.queue.push(command)
        }
    }

    fun getUsedColors(): Set<Color> {
        var usedColors = mutableSetOf<Color>()

        for (player in players) {
            usedColors.add(player.color)
        }

        return usedColors.toSet()
    }
}