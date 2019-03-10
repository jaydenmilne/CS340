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

    fun getGameForPlayer(user: User): Game? {
        return games.filter { p -> p.value.players.contains(user) }[0]
    }

    fun getGameIdForPlayer(user: User): Int? {
        val gameUserIn = games.filter { p -> p.value.players.contains(user) }[0]
        return gameUserIn?.gameId
    }
}

class Game(var name: String) {
    val gameId = getNextGameID()
    var players = mutableSetOf<User>()
    var started = false

    var shardCardDeck = ShardCardDeck(mutableListOf()).initializeDeck()
    var faceUpShardCards = ShardCardDeck(mutableListOf())
    var shardCardDiscardPile = ShardCardDeck(mutableListOf())

    @Transient var destinationCardDeck = DestinationCardDeck(mutableListOf()).initializeDeck()

    var whoseTurn: User? = null

    var chatMessages = mutableListOf<Message>()

    var destDiscardOrder = 0

    @Transient private var routes = RouteList()

    @Transient private var nextMessageId = -1

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

    fun getNextMessageId(): Int {
        nextMessageId++
        return nextMessageId
    }

    fun getOrderForUser(user: User): Int {
        return players.indexOf(user)
    }
}