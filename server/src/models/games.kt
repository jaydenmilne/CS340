package models

import commands.ChangeTurnCommand
import commands.CommandException
import commands.INormalClientCommand
import commands.UpdateBankCommand
import commands.UpdatePlayerCommand
import java.lang.RuntimeException

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
        return games.filter { p -> p.value.players.contains(user) }.values.toMutableList()[0]
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

    var whoseTurn: Int = -1

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


    fun updatePlayer(user: User){
        var updatePlayerCommand = UpdatePlayerCommand()
        updatePlayerCommand.gamePlayer = user.toGamePlayer()
        broadcast(updatePlayerCommand)
    }
  

    fun updatebank(){
        var updatebankCommand = UpdateBankCommand()
        updatebankCommand.faceUpCards = faceUpShardCards.cards
        updatebankCommand.shardDrawPileSize = shardCardDeck.cards.size
        updatebankCommand.shardDiscardPileSize = shardCardDiscardPile.cards.size
        updatebankCommand.destinationPileSize = destinationCardDeck.cards.size
        broadcast(updatebankCommand)
    }
  

    fun canClaimRoute(user: User, routeId: String, cards: Array<ShardCard>): Boolean {

        val currentRoute = routes.routesByRouteId[routeId]

        // Check if route is not owned
        if (currentRoute != null) {
            if (currentRoute.ownerId != null) {
                return false
            }
        }
        else {
            throw RuntimeException("Invalid route ID")
        }

        val infinityGauntlets = mutableListOf<ShardCard>()
        val secondaryCards = mutableListOf<ShardCard>()

        for (s in cards) {
            if (s.type == MaterialType.INFINITY_GAUNTLET) {
                infinityGauntlets.add(s)
            }
            else {
                secondaryCards.add(s)
            }
        }

        var numUserSecondaryCards = 0
        if (secondaryCards.size > 0) {
            if (secondaryCards.filter { card -> card.type == secondaryCards[0].type }.size != secondaryCards.size) {
                return false
            }
            numUserSecondaryCards = user.shardCards.shardCards.filter{ s -> s.type == secondaryCards[0].type}.size
        }

        // Check user's hand
        val numUserInfinityGauntlets = user.shardCards.shardCards.filter { s -> s.type == MaterialType.INFINITY_GAUNTLET }.size

        if (infinityGauntlets.size > numUserInfinityGauntlets || secondaryCards.size > numUserSecondaryCards) {
            return false
        }

        // Check route requirements
        if (currentRoute.numCars != secondaryCards.size + infinityGauntlets.size) {
            return false
        }

        // If they are claiming a route with all infinity gauntlets...
        if (infinityGauntlets.size == currentRoute.numCars) {
            return true
        }

        // Else...
        when (currentRoute.type) {
            RouteType.ANY -> return true
            RouteType.REALITY -> return secondaryCards[0].type == MaterialType.REALITY_SHARD
            RouteType.SOUL -> return secondaryCards[0].type == MaterialType.SOUL_SHARD
            RouteType.SPACE -> return secondaryCards[0].type == MaterialType.SPACE_SHARD
            RouteType.MIND -> return secondaryCards[0].type == MaterialType.MIND_SHARD
            RouteType.POWER -> return secondaryCards[0].type == MaterialType.POWER_SHARD
            RouteType.TIME -> return secondaryCards[0].type == MaterialType.TIME_SHARD
            RouteType.VIBRANIUM -> return secondaryCards[0].type == MaterialType.VIBRANIUM
            RouteType.PALLADIUM -> return secondaryCards[0].type == MaterialType.PALLADIUM
        }
    }

    fun claimRoute(userId: Int, routeId: String) {

        val route = routes.routesByRouteId[routeId] ?: throw CommandException("Invalid Route ID")

        route.ownerId = userId

    }

    fun advanceTurn(){
        if(this.whoseTurn == -1){   // check if we're done with setup
            // Check if all players have completed setup
            if (this.players.filter { p -> !p.setupComplete }.isEmpty()){
                this.incPlayerTurn()
                this.broadcast(ChangeTurnCommand(this.getTurningPlayer()?.userId!!))
            }
        } else {
            // advance to the next player
            this.incPlayerTurn()
            this.broadcast(ChangeTurnCommand(this.getTurningPlayer()?.userId!!))
        }
    }

    fun incPlayerTurn() {
        if (this.whoseTurn == -1 || this.whoseTurn == this.players.size -1){
            this.whoseTurn = 0
        } else {
            this.whoseTurn++
        }
    }

    fun getTurningPlayer(): User? {
        if (this.whoseTurn == -1){
            return null
        }
        return this.players.filter { p -> p.turnOrder == this.whoseTurn }[0]
    }
}