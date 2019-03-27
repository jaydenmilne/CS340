package commands

import models.*
import java.lang.RuntimeException

class DebugHelpCommand : INormalServerCommand{
    override val command = DEBUG_HELP
    val action = ""
    override fun execute(user: User) {
        parseCommand(user)
    }

    private fun parseCommand(user: User){
        val first = action.split(' ')[0];
        when (first){
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
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.faceUpShardCards = ShardCardDeck(mutableListOf())
        for(i in 0..4){
            game.faceUpShardCards.push(ShardCard(MaterialType.INFINITY_GAUNTLET))
        }
        game.updatebank()

    }

    private fun allYourBasesCommand(user: User) {
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.routes.routesByRouteId.forEach{
            if(it.value.ownerId != null){
                it.value.ownerId = user.userId
                val routeClaimed = RouteClaimedCommand()
                routeClaimed.routeId = it.key
                routeClaimed.userId = user.userId
                game.broadcast(routeClaimed)
            }
        }

    }

    private fun claimCommand(user: User) {
        val player = action.split(' ')[1];
        val route = action.split(' ')[2];
        val gamePlayer = Users.getUserByUsername(player)
        if(gamePlayer == null){
            return
        }
        val game = Games.getGameForPlayer(gamePlayer)
        if(game == null){
            return
        }
        val routeFound = game.routes.routesByRouteId.get(route)
        if(routeFound == null){
            return
        }
        routeFound.ownerId = gamePlayer.userId
        val routeClaimed = RouteClaimedCommand()
        routeClaimed.routeId = route
        routeClaimed.userId = user.userId
        game.broadcast(routeClaimed)
    }

    private fun changeTurn(user: User) {
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.advanceTurn()

    }

    private fun giveThemTrains(user: User) {
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.players.forEach {
            if(it != user) {
                it.numRemainingTrains = 100
                it.updateHand()
            }
        }
        user.numRemainingTrains = 10
        user.updateHand()
    }

    private fun killDecks(user: User) {
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.destinationCardDeck = DestinationCardDeck(mutableListOf())
        game.players.forEach {
            if(it != user) {
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
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        game.players.forEach {
            if(it != user) {
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
        val cityOne = action.split(' ')[1];
        val cityTwo = action.split(' ')[2];
        val points = action.split(' ')[3];
        val newDestination = DestinationCard(setOf(cityOne, cityTwo), points.toInt())
        user.destinationCards.push(newDestination);
        user.updateHand()
    }

    private fun whereTo(user: User) {
        val game = Games.getGameForPlayer(user)
        if(game == null){
            return
        }
        var dealCardsCmd = DealCardsCommand()
        dealCardsCmd.destinations.add(game.destinationCardDeck.getNext())
        dealCardsCmd.destinations.add(game.destinationCardDeck.getNext())
        dealCardsCmd.destinations.add(game.destinationCardDeck.getNext())
        dealCardsCmd.minDestinations = 2;
        user.queue.push(dealCardsCmd)
        user.destinationCards.destinationCards.addAll(dealCardsCmd.destinations)
    }

    private fun makeItRain(user: User) {
        val args = action.split(' ')
        var dealCardsCmd = DealCardsCommand()
        if(args.size > 1){
            val cardType = args[1].toUpperCase();
            if (MaterialType.valueOf(cardType) === null) { return; }
            for(i in 0..2) {
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.valueOf(cardType)))
            }
        }else{
            for(i in 0..2) {
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.INFINITY_GAUNTLET))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.SPACE_SHARD))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.SOUL_SHARD))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.VIBRANIUM))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.REALITY_SHARD))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.TIME_SHARD))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.POWER_SHARD))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.PALLADIUM))
                dealCardsCmd.shardCards.add(ShardCard(MaterialType.MIND_SHARD))
            }
        }
        user.shardCards.shardCards.addAll(dealCardsCmd.shardCards)
        user.queue.push(dealCardsCmd)

    }

    private fun endGame(user:User){

    }

}
