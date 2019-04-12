package models

import commands.*
import IGame

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
        return games.filter { p -> p.value.players.contains(user) }.values.firstOrNull()
    }

    fun getGameIdForPlayer(user: User): Int? {
        val gameUserIn = games.filter { p -> p.value.players.contains(user) }.values.firstOrNull()
        return gameUserIn?.gameId
    }
}

class Game(var name: String): IGame(getNextGameID()) {
    var players = mutableSetOf<User>()
    var started = false

    var shardCardDeck = ShardCardDeck(mutableListOf()).initializeDeck()
    var faceUpShardCards = ShardCardDeck(mutableListOf())
    var shardCardDiscardPile = ShardCardDeck(mutableListOf())

    @Transient  var destinationCardDeck = DestinationCardDeck(mutableListOf()).initializeDeck()


    var whoseTurn: Int = -1

    var chatMessages = mutableListOf<Message>()
    var destDiscardOrder = 0

    @Transient var longestRouteManager = LongestRouteManager(this)
    @Transient var routes = RouteList()
    @Transient private var lastRoundInitiator = User("")
    @Transient private var lastRoundStarted = false
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
        val usedColors = mutableSetOf<Color>()

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


    fun updatePlayer(user: User) {
        val updatePlayerCommand = UpdatePlayerCommand()
        updatePlayerCommand.gamePlayer = user.toGamePlayer()
        broadcast(updatePlayerCommand)
    }


    fun updatebank() {
        val updatebankCommand = UpdateBankCommand()
        updatebankCommand.faceUpCards = faceUpShardCards.cards
        updatebankCommand.shardDrawPileSize = shardCardDeck.cards.size
        updatebankCommand.shardDiscardPileSize = shardCardDiscardPile.cards.size
        updatebankCommand.destinationPileSize = destinationCardDeck.cards.size
        broadcast(updatebankCommand)
    }

    fun endGame() {
        val gameOverCommand = GameOverCommand(mutableListOf())
        players.forEach {
            gameOverCommand.players.add(it.toPlayerPoints())
            it.reset()
        }
        broadcast(gameOverCommand)
        Games.games.remove(this.gameId)
    }

    enum class CanClaimRouteResult {
        CLAIM_OK,
        ROUTE_DISABLED_LESS_THAN_THREE_PLAYERS,
        ROUTE_IS_OWNED,
        NOT_ENOUGH_ENERGY,
        NOT_ENOUGH_SHARD_CARDS_USED,
        USER_DOES_NOT_HAVE_ENOUGH_CARDS,
        WRONG_TYPE_USED_TO_CLAIM_ROUTE,
        INVALID_MIX_OF_CARDS,
        NO_CARDS_USED_TO_CLAIM
    }

    fun canClaimRoute(user: User, routeId: String, cardsUsedToClaim: Array<ShardCard>): CanClaimRouteResult {

        if (cardsUsedToClaim.size == 0) {
            return CanClaimRouteResult.NO_CARDS_USED_TO_CLAIM
        }

        val routeToClaim = routes.routesByRouteId[routeId] ?: throw RuntimeException("Invalid route ID")

        // Check if route is not owned
        if (routeToClaim.ownerId != -1) {
            return CanClaimRouteResult.ROUTE_IS_OWNED
        }

        // Check if route is disabled for 2 or 3 player mode
        if (players.size < 4) {

            val newRouteId = StringBuilder().append(routeId)
            if (routeId[routeId.lastIndex] == '1') {
                newRouteId.setCharAt(newRouteId.length - 1, '2')
            } else if (routeId[routeId.lastIndex] == '2') {
                newRouteId.setCharAt(newRouteId.length - 1, '1')
            }

            if (routes.routesByRouteId[newRouteId.toString()]!!.ownerId != -1) {
                return CanClaimRouteResult.ROUTE_DISABLED_LESS_THAN_THREE_PLAYERS
            }
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
        return routes.routesByRouteId.map { entry ->
            when (entry.value.ownerId) {
                userId -> entry.value.points
                else -> 0
            }
        }.reduce { totalPoints, points -> totalPoints + points }
    }

    fun getRouteBetweenCitiesForPlayer(userId: Int, city1: String, city2: String): Boolean {
        return routes.pathBetweenCities(city1, city2, userId)
    }

    fun claimRoute(user: User, routeId: String, cardsUsedToClaim: List<ShardCard> = listOf()) {
        val route = routes.routesByRouteId[routeId] ?: throw CommandException("Invalid Route ID")
        route.ownerId = user.userId

        // Recalculate if this player has the longest route
        longestRouteManager.playerClaimedRoute(user.userId)

        user.numRemainingTrains -= route.numCars

        if (route.cities.size == 2) {

            val uniqueCards = mutableSetOf<ShardCard>()
            uniqueCards.addAll(cardsUsedToClaim)

            var cardsUsed = ""

            for (type in uniqueCards) {
                cardsUsed += (cardsUsedToClaim.count { card -> card.type == type.type }).toString() + "x " + type.getMaterialTypeString() + ", "
            }

            cardsUsed = cardsUsed.removeRange(cardsUsed.length - 2, cardsUsed.length - 1)

            val citiesArray = route.cities.toTypedArray()

            val message = java.lang.String.format("%s claimed %s ⇔ %s using %s",
                    user.username,
                    DisplayNameFormatter().getProperCityName(citiesArray[0]),
                    DisplayNameFormatter().getProperCityName(citiesArray[1]),
                    cardsUsed)

            broadcastEvent(message)
        } else {
            throw RuntimeException("Route does not have two cities listed")
        }
        user.points += route.points
    }

    fun advanceTurn() {
        if (this.whoseTurn == -1) {   // check if we're done with setup
            // Check if all players have completed setup
            if (this.players.none { p -> !p.setupComplete }) {
                this.incPlayerTurn()
                this.broadcast(ChangeTurnCommand(this.getTurningPlayer()?.userId!!))
            }
        } else {
            //Checks for Last Round to End
            if (lastRoundInitiator == getTurningPlayer()) {
                this.endGame()
            }
            else {
                // advance to the next player
                this.incPlayerTurn()
                this.broadcast(ChangeTurnCommand(this.getTurningPlayer()?.userId!!))
            }
        }
    }

    private fun incPlayerTurn() {
        if (this.whoseTurn == -1 || this.whoseTurn == this.players.size - 1) {
            this.whoseTurn = 0
        } else {
            this.whoseTurn++
        }
    }

    fun getTurningPlayer(): User? {
        if (this.whoseTurn == -1) {
            return null
        }
        return this.players.filter { p -> p.turnOrder == this.whoseTurn }[0]
    }

    fun broadcastEvent(message: String) {
        broadcast(UpdateChatCommand(Message(-1, "", message, nextMessageId, true)))
    }

    fun shuffleShardCards() {
        shardCardDeck.shardCards.addAll(shardCardDiscardPile.shardCards)
        shardCardDiscardPile = ShardCardDeck(mutableListOf())
        shardCardDeck.shuffle()
    }

    fun redrawFaceUpCards() {
        shardCardDiscardPile.shardCards.addAll(faceUpShardCards.shardCards)
        faceUpShardCards = ShardCardDeck(mutableListOf())
        for (i in 0..4) {
            //check if deck is empty then shuffle discard
            if (shardCardDeck.shardCards.isEmpty()) {
                shuffleShardCards()
                //if deck is still empty leave the loop
                if (shardCardDeck.shardCards.isEmpty()) {
                    break
                }
            }
            faceUpShardCards.shardCards.add(shardCardDeck.getNext())//Set faceup cards
        }
        //check for more than 3 infinity_gauntlets again

        var gauntletCards = faceUpShardCards.shardCards.filter { s -> s.type.material == "infinity_gauntlet" }
        if (gauntletCards.size > 2) {
            val allCards = mutableListOf<ShardCard>()
            allCards.addAll(shardCardDiscardPile.shardCards)
            allCards.addAll(faceUpShardCards.shardCards)
            allCards.addAll(shardCardDeck.shardCards)

            gauntletCards = allCards.filter { s -> s.type.material == "infinity_gauntlet" }
            //make sure the amount of infinity gauntlets still leaves enough room for other cards(if this number is smaller too many permutations exist)
            if ((allCards.size - gauntletCards.size) >= gauntletCards.size) {
                redrawFaceUpCards()
            }
        }
    }

    fun startLastRound(user: User) {
        if (!lastRoundStarted) { //Makes Sure Last Round Isn't Already Started

            val lastRoundCommand = LastRoundCommand()
            broadcast(lastRoundCommand)
            lastRoundInitiator = user
        }
    }
}
