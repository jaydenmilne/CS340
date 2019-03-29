package commands

import models.*
import java.lang.RuntimeException


/**
 * Create a new game on the server.
 */
class CreateGameCommand : INormalServerCommand {
    override val command = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {
        var newGame = Game(name)
        Games.games[newGame.gameId] = newGame

        // If the user is in another game, take them out.
        Games.removeUserFromGames(user)

        // Add player to this new game's list of players
        newGame.players.add(user)
        user.ready = false

        // Notify the client that we created the game
        user.queue.push(GameCreatedCommand(newGame.gameId))
    }
}

/**
 * Join a game that has not yet started.
 */
class JoinGameCommand : INormalServerCommand {
    override val command = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {
        // Ensure that the user is only in one game
        Games.removeUserFromGames(user)

        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("JoinGameCommand: Game does not exist")
        }

        if (game.players.size < 5) {
            game.players.add(user)

            val availableColors = Color.values().toSet() - game.getUsedColors()
            user.color = availableColors.shuffled()[0]
            user.ready = false
        } else {
            throw CommandException("Game Full")
        }
    }
}

/**
 * Leave a game that has not yet started.
 */
class LeaveGameCommand : INormalServerCommand {
    override val command = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {
        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("LeaveGameCommand: Game does not exist")
        }

        game.players.remove(user)
        user.ready = false

        if (game.players.size == 0) {
            Games.games.remove(game.gameId)
        }
    }
}

/**
 * List all available games to play.
 */
class ListGamesCommand : INormalServerCommand {
    override val command = LIST_GAMES

    override fun execute(user: User) {
        var newCommand = RefreshGameListCommand()
        user.queue.push(newCommand)
    }
}

/**
 * Log in to the server with the provided credentials.
 */
class LoginCommand : IRegisterServerCommand {
    override val command = LOGIN
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {
        var response = LoginResultCommand()

        var user = Users.getUserByUsername(username)

        if (user == null) {
            response.error = "Authentication error."
            return response
        }

        if (user.verifyPassword(password)) {
            // TODO: what will happen if the user has started a game and reloads?
            if (user.setupComplete) {
                // The user is in a game already. Set the game value on the response
                for ((_, game) in Games.games) {
                    if (user in game.players) {
                        response.game = game.gameId
                        break
                    }
                }
            }

            val authToken = AuthTokens.makeAuthTokenForUser(user)
            response.user = ClientUser(user.userId, user.username, authToken)
            return response
        } else {
            response.error = "Authentication error."
            return response
        }
    }
}

/**
 * Change a user's ready status for a particular game.
 */
class PlayerReadyCommand : INormalServerCommand {
    override val command = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute(user: User) {
        user.ready = playerIsReady

        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("That game doesn't exist.")
        }

        val notReadyPlayers = game.players.filter { u -> !u.ready }

        if (notReadyPlayers.isEmpty() && game.players.size >= 2) {
            game.started = true
            game.shardCardDeck.shuffle()
            game.destinationCardDeck.shuffle()
            for (i in 0..4) {
                var nextFaceUpShardCard = game.shardCardDeck.getNext()
                game.faceUpShardCards.push(nextFaceUpShardCard)
            }
            game.broadcast(StartGameCommand(game.gameId.toString()))
        }
    }
}

/**
 * Register a new user with the server.
 */
class RegisterCommand : IRegisterServerCommand {
    override val command = REGISTER
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {
        var response = LoginResultCommand()

        if (Users.isUsernameTaken(username)) {
            response.error = "Username already taken."
            return response
        }

        var newUser = User(username, password)
        Users.addUser(newUser)

        var token = AuthTokens.makeAuthTokenForUser(newUser)
        newUser.authToken = token

        response.user = ClientUser(newUser.userId, newUser.username, newUser.authToken)

        return response
    }
}

/**
 * Change a user's color for a particular game.
 */
class ChangeColorCommand : INormalServerCommand {
    override val command = CHANGE_COLOR
    private val gameId = ""
    private var newColor = ""

    override fun execute(user: User) {
        val game = Games.games[gameId.toInt()]

        // Verify that the game exists
        if (game == null) {
            throw CommandException("ChangeColorCommand: Game does not exist")
        }

        // Verify that the user is in the game
        if (!game.players.contains(user)) {
            throw CommandException("ChangeColorCommand: User is not in this game")
        }

        if (game.getUsedColors().contains(Color.valueOf(newColor))) {
            throw CommandException("ChangeColorCOmmand: Color already taken")
        }

        user.color = Color.valueOf(newColor)
    }
}

/**
 * Concrete type to make Gson happy when identifying the type of Server Command.
 *
 * In a future update, this will be replaced by making INormalServerCommand concrete.
 *
 * @deprecated
 */
class ServerCommandData : IRegisterCommand, INormalCommand {
    override val command: String = ""
}

class RequestDestinationsCommand : INormalServerCommand {
    override val command = REQUEST_DESTINATIONS

    override fun execute(user: User) {
        var game = Games.getGameForPlayer(user)

        if (game == null) {
            throw RuntimeException("User not in a game")
        }
        var dealCardsCommand = DealCardsCommand()
        var dealtDestinationCards = mutableListOf<DestinationCard>()
        var dealtShardCards = mutableListOf<ShardCard>()

        if(game.whoseTurn == -1) {

            /* Deal 4 train cards to user */
            for (i in 0..3) {
                // Draw a card from the train card deck and add it to the list
                var nextShardCard = game.shardCardDeck.getNext()
                user.shardCards.push(nextShardCard)
                dealtShardCards.add(nextShardCard)
            }

        }else{
            dealCardsCommand.minDestinations = 1
            if (game.getTurningPlayer() != user) {
                throw CommandException("RequestDestinations: Not Your Turn")
            }
        }

        /* Deal 3 destination cards to user */
        for (i in 0..2) {
            //Check for an empty destination card deck
            if (game.destinationCardDeck.destinationCards.isEmpty()) {
                break
            }
            // Draw a card from the destination card deck and add it to the list
            var nextDestinationCard = game.destinationCardDeck.getNext()
            user.destinationCards.push(nextDestinationCard)
            dealtDestinationCards.add(nextDestinationCard)
        }
        if (dealtDestinationCards.isEmpty()) {
            throw CommandException("RequestDestinations: Cannot Draw on an Empty Deck")
        }
        dealCardsCommand.shardCards = dealtShardCards
        dealCardsCommand.destinations = dealtDestinationCards
        user.queue.push(dealCardsCommand)

        /* Send UpdateBankCommand to user's client */
        game.updatebank()

        /* Send UpdatePlayerCommand to user's client */
        game.updatePlayer(user)
    }
}

class RejoinGameCommand : INormalServerCommand {
    override val command = REJOIN_GAME

    override fun execute(user: User) {
        // The client has no state, send everything that it will need.

        // (note that a lot of these are broadcast (ie sent to every player)
        //  getting a little extra state can't hurt, right?)
        // 1. Send updatePlayers for every player
        val game = Games.getGameForPlayer(user) ?: throw CommandException("RejoinGameCommand: User is not in a game")

        game.players.forEach {player -> game.updatePlayer(player) }

        // 2. Send the bank info to the client
        game.updatebank()

        // 3. Send route info to the client
        game.routes.routesByRouteId.values.forEach {
            val id = it.ownerId
            if (id != null) {
                val cmd = RouteClaimedCommand()
                cmd.routeId = it.routeId
                cmd.userId = id
                user.queue.push(cmd)
            }
        }

        // 4. Send information about the players hand
        user.queue.push(UpdateHandCommand(user.destinationCards.cards, user.shardCards.cards))

        // 5. If it is the players turn, inform them of that
        if (user.userId == game.whoseTurn) {
            val cmd = ChangeTurnCommand()
            cmd.userId = user.userId
            user.queue.push(cmd)
        }

        // TODO: send lastRound if appropriate

    }
}

class DiscardDestinationsCommand : INormalServerCommand {
    override val command = DISCARD_DESTINATIONS
    private val discardedDestinations = listOf<DestinationCard>()

    override fun execute(user: User) {
        val game = Games.getGameForPlayer(user) ?: throw CommandException("DiscardDestinationsCommand Command:User not in a game")

        if (user.turnOrder == -1) {
            user.turnOrder = game.destDiscardOrder++
        }

        discardedDestinations.forEach { discarded -> game.destinationCardDeck.push(discarded) }
        // Remove the discarded cards from the player's hand
        user.destinationCards.destinationCards.removeAll(discardedDestinations)
        user.setupComplete = true   //

        // Broadcast the updated player to everyone else
        game.updatePlayer(user)
        game.updatebank()
        game.advanceTurn()
    }
}

class ClaimRouteCommand : INormalServerCommand {
    override val command = CLAIM_ROUTE
    private val routeId = ""
    private val shardsUsed = listOf<ShardCard>()

  
      override fun execute(user: User) {
        val game = Games.getGameForPlayer(user) ?: throw RuntimeException("User not in a game")

          val claimRouteResult = game.canClaimRoute(user, routeId, shardsUsed.toTypedArray())

        if (claimRouteResult == Game.CanClaimRouteResult.CLAIM_OK) {
            shardsUsed.forEach{card -> user.shardCards.shardCards.remove(card)}
            game.shardCardDiscardPile.shardCards.addAll(shardsUsed)

            game.claimRoute(user, routeId)

            val routeClaimed = RouteClaimedCommand()
            routeClaimed.routeId = this.routeId
            routeClaimed.userId = user.userId
            game.broadcast(routeClaimed)

            user.updateHand()
            val updatePlayer = UpdatePlayerCommand();
            updatePlayer.gamePlayer = user.toGamePlayer()
            game.broadcast(updatePlayer)

            game.advanceTurn()
            if(user.numRemainingTrains < 4){
                game.startLastRound(user)
            }
        }
        else {
            throw CommandException("ClaimRouteCommand: Route cannot be claimed. " + claimRouteResult.name)
        }
    }
}

class DrawShardCardCommand : INormalServerCommand {
    override val command = DRAW_SHARD_CARD
    var card = ""
    var cardToSend = ShardCard()
  
    override fun execute(user: User) {
        val game = Games.getGameForPlayer(user) ?: throw CommandException("DrawShardCard Command: User not in a game")

        if (game.getTurningPlayer() != user) {
            throw CommandException("DrawShardCard Command: Not Your Turn")
        }

        // Check if the user wants to draw from the deck or from the faceup deck
        if (card == "deck") {
            if (game.shardCardDeck.shardCards.isEmpty()) { //Check Empty Deck
                throw CommandException("DrawShardCard Command: Cannot Draw on Empy Deck")
            }
            cardToSend = game.shardCardDeck.getNext()
            user.shardCards.push(cardToSend)
            if (game.shardCardDeck.shardCards.isEmpty()) {
                game.shuffleShardCards()
            }

        } else {
            // Find how many shardCards in the faceUp deck match the requested card's material type
            val validCards = game.faceUpShardCards.shardCards.filter { s -> s.type.material == card }
            if (validCards.isNotEmpty()) {
                // Takes the first card that matches type and draws it for the user and if it is blank throws an error
                game.faceUpShardCards.shardCards.remove(validCards[0])
                //if deck is empty leave the loop
                if (game.shardCardDeck.shardCards.isNotEmpty()) {
                    game.faceUpShardCards.shardCards.add(game.shardCardDeck.getNext())
                }
                cardToSend = validCards[0]
                //check if there are more then 2 infinity gauntlets in the deck
                if ((game.faceUpShardCards.shardCards.filter{s -> s.type.material == "infinity_gauntlet"}).size > 2) {
                    game.redrawFaceUpCards()
                }
                //check if deck is empty then shuffle discard
                if (game.shardCardDeck.shardCards.isEmpty()) {
                    game.shuffleShardCards()
                }
            } else{
                throw CommandException("DrawShardCard Command: Card Does Not Exist")
            }
            user.shardCards.push(cardToSend);
        }

        val dealCardsCmd = DealCardsCommand()
        user.shardCards.shardCards.add(cardToSend)
        dealCardsCmd.shardCards.add(cardToSend)
        user.queue.push(dealCardsCmd)
        game.updatebank()
        game.updatePlayer(user)

        if (user.isDrawingSecondCard || MaterialType.INFINITY_GAUNTLET.material == card) {
            game.advanceTurn()
            user.isDrawingSecondCard = false
        } else {
            user.isDrawingSecondCard = true
        }
    }
}
