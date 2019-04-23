package commands

import MaterialType
import models.*

class DebugHelpCommand : INormalServerCommand {
    override val command = DEBUG_HELP
    private val action = ""
    override fun execute(user: User) {
        parseCommand(user)
    }

    private fun parseCommand(user: User) {
        val first = action.split(' ')[0]
        when (first) {
            "/rainbowroad" -> rainbowCommand(user)
            "/allyourbasesaremine" -> allYourBasesCommand(user)
            "/claim" -> claimCommand(user)
            "/givethemtrainsorgivemedeath" -> giveThemTrains(user)
            "/myturn" -> changeTurn(user)
            "/heartinthecards" -> killDecks(user)
            "/finaldestination" -> finalDestination(user)
            "/newroad" -> newRoad(user)
            "/whereto" -> whereTo(user)
            "/makeitrain" -> makeItRain(user)
            "/ohsnap" -> endGame(user)
        }
    }

    private fun rainbowCommand(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.faceUpShardCards = ShardCardDeck(mutableListOf())
        for (i in 0..4) {
            game.faceUpShardCards.push(ShardCard(MaterialType.INFINITY_GAUNTLET))
        }
        game.updatebank()

    }

    private fun allYourBasesCommand(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.routes.routesByRouteId.forEach {
            if (it.value.ownerId != -1) {
                it.value.ownerId = user.userId
                val routeClaimed = RouteClaimedCommand(user.userId, it.key)
                game.broadcast(routeClaimed)
            }
        }

    }

    private fun claimCommand(user: User) {
        val player = action.split(' ')[1]
        val route = action.split(' ')[2]
        val gamePlayer = Users.getUserByUsername(player) ?: return
        val game = Games.getGameForPlayer(gamePlayer) ?: return
        val routeFound = game.routes.routesByRouteId[route] ?: return
        routeFound.ownerId = gamePlayer.userId
        val routeClaimed = RouteClaimedCommand(user.userId, route)
        game.broadcast(routeClaimed)
    }

    private fun changeTurn(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.advanceTurn()

    }

    private fun giveThemTrains(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.players.forEach {
            if (it != user) {
                it.numRemainingTrains = 100
                it.updateHand()
            }
        }
        user.numRemainingTrains = 10
        user.updateHand()
    }

    private fun killDecks(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.destinationCardDeck = DestinationCardDeck(mutableListOf())
        game.players.forEach {
            if (it != user) {
                it.destinationCards = DestinationCardDeck(mutableListOf()).initializeDeck()
                it.shardCards = ShardCardDeck(mutableListOf())
                it.updateHand()
            }
        }

        user.shardCards = ShardCardDeck(mutableListOf()).initializeDeck()
        user.updateHand()
        game.updatebank()


    }

    private fun finalDestination(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.players.forEach {
            if (it != user) {
                it.destinationCards = DestinationCardDeck(mutableListOf()).initializeDeck()
                it.updateHand()
                game.updatePlayer(it)
            }

        }
        user.destinationCards = DestinationCardDeck(mutableListOf())
        user.updateHand()
        game.updatePlayer(user)
    }

    private fun newRoad(user: User) {
        val cityOne = action.split(' ')[1]
        val cityTwo = action.split(' ')[2]
        val points = action.split(' ')[3]
        val newDestination = DestinationCard(setOf(cityOne, cityTwo), points.toInt())
        user.destinationCards.push(newDestination)
        user.updateHand()
    }

    private fun whereTo(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        val destinations = mutableListOf<DestinationCard>()
        destinations.add(game.destinationCardDeck.getNext())
        destinations.add(game.destinationCardDeck.getNext())
        destinations.add(game.destinationCardDeck.getNext())
        val dealCardsCmd = DealCardsCommand(destinations, mutableListOf(), 2)
        user.queue.push(dealCardsCmd)
        user.destinationCards.destinationCards.addAll(destinations)
    }

    private fun makeItRain(user: User) {
        val shardCards = mutableListOf<ShardCard>()
        val args = action.split(' ')
        if (args.size > 1) {
            val cardType = args[1].toUpperCase()
            try {
                for (i in 0..2) {
                    shardCards.add(ShardCard(MaterialType.valueOf(cardType)))
                }
            } catch (e: Exception) {
                throw CommandException("Type Not Found")
            }
        } else {
            for (i in 0..2) {
                shardCards.add(ShardCard(MaterialType.INFINITY_GAUNTLET))
                shardCards.add(ShardCard(MaterialType.SPACE_SHARD))
                shardCards.add(ShardCard(MaterialType.SOUL_SHARD))
                shardCards.add(ShardCard(MaterialType.VIBRANIUM))
                shardCards.add(ShardCard(MaterialType.REALITY_SHARD))
                shardCards.add(ShardCard(MaterialType.TIME_SHARD))
                shardCards.add(ShardCard(MaterialType.POWER_SHARD))
                shardCards.add(ShardCard(MaterialType.PALLADIUM))
                shardCards.add(ShardCard(MaterialType.MIND_SHARD))
            }
        }
        val dealCardsCmd = DealCardsCommand(mutableListOf(), shardCards)

        user.shardCards.shardCards.addAll(shardCards)
        user.queue.push(dealCardsCmd)

    }

    private fun endGame(user: User) {
        val game = Games.getGameForPlayer(user) ?: return
        game.endGame()
    }

}
