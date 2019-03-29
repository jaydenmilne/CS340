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
    @Transient var longestRouteManager = LongestRouteManager(this)


    @Transient public var routes = RouteList()


    @Transient private var nextMessageId = -1

    init {
        longestRouteManager.init()
    }

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

    enum class CanClaimRouteResult {
        CLAIM_OK,
        ROUTE_DISABLED_LESS_THAN_THREE_PLAYERS,
        ROUTE_IS_OWNED,
        NOT_ENOUGH_ENERGY,
        NOT_ENOUGH_SHARD_CARDS_USED,
        USER_DOES_NOT_HAVE_ENOUGH_CARDS,
        WRONG_TYPE_USED_TO_CLAIM_ROUTE,
        INVALID_MIX_OF_CARDS
    }

    fun canClaimRoute(user: User, routeId: String, cardsUsedToClaim: Array<ShardCard>): CanClaimRouteResult {

        val routeToClaim = routes.routesByRouteId[routeId]

        // Check if route is disabled for 2 or 3 player mode
        if (players.size < 4) {

            var newRouteId = StringBuilder().append(routeId)
            if (routeId[routeId.lastIndex] == '1') {
                newRouteId.setCharAt(newRouteId.length - 1, '2')
            }
            else if (routeId[routeId.lastIndex] == '2') {
                newRouteId.setCharAt(newRouteId.length - 1, '1')
            }

            if (routes.routesByRouteId[newRouteId.toString()]!!.ownerId != -1) {
                return CanClaimRouteResult.ROUTE_DISABLED_LESS_THAN_THREE_PLAYERS
            }
        }

        if (routeToClaim == null) {
            throw RuntimeException("Invalid route ID")
        }
        
        // Check if route is not owned
        if (routeToClaim.ownerId != -1) {
            return CanClaimRouteResult.ROUTE_IS_OWNED
        }

        // Check user's energy
        if (user.numRemainingTrains < routeToClaim.numCars) {
            return CanClaimRouteResult.NOT_ENOUGH_ENERGY
        }

        // Check if they gave enough cards
        if (cardsUsedToClaim.size != routeToClaim.numCars) {
            return CanClaimRouteResult.NOT_ENOUGH_SHARD_CARDS_USED
        }

        // Make sure they used a valid combination of cards

        // The last type used to to claim a route. Could be INFINITY_GAUNTLET
        var lastType = cardsUsedToClaim[0].type

        for (card in cardsUsedToClaim) {
            // Check if this card is different from another card they used (all non infinity cards should be the same)
            if (lastType != MaterialType.INFINITY_GAUNTLET &&
                    card.type != MaterialType.INFINITY_GAUNTLET && // if it is infinity gauntlet, is not an invalid card
                    card.type != lastType) {
                return CanClaimRouteResult.INVALID_MIX_OF_CARDS
            } else if (lastType == MaterialType.INFINITY_GAUNTLET && card.type != MaterialType.INFINITY_GAUNTLET) {
                // In the case that the first card in cardsUsedToClaim is wild, we've now found the first non-wild card,
                // and we need to check the rest of non-wild cards to make sure they all match
                lastType = card.type
            }

            // Make sure this card type matches the route type
            if (!MaterialType.matchesRouteType(routeToClaim.type, card.type)) {
                return CanClaimRouteResult.WRONG_TYPE_USED_TO_CLAIM_ROUTE
            }
        }

        return CanClaimRouteResult.CLAIM_OK
    }

    fun getRoutePointsForPlayer(userId: Int): Int {
        return routes.routesByRouteId.map { entry -> when(entry.value.ownerId) {
            userId -> entry.value.points
            else -> 0
        } }.reduce { totalPoints, points -> totalPoints + points}
    }

    fun getRouteBetweenCitiesForPlayer(userId: Int, city1: String, city2: String): Boolean {
        return routes.pathBetweenCities(city1, city2, userId)
    }
  
    fun claimRoute(user: User, routeId: String) {
        val route = routes.routesByRouteId[routeId] ?: throw CommandException("Invalid Route ID")
        route.ownerId = user.userId

        // Recalculate if this player has the longest route
        longestRouteManager.playerClaimedRoute(user.userId)
        user.numRemainingTrains -= route.numCars
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
