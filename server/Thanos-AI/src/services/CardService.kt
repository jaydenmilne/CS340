package services

import CommandRouter
import IDestinationCard
import IShardCard
import ProxyServer
import commands.*
import models.Route

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.CardService: Handles shard cards and destination cards
 */
class CardService(private val server: ProxyServer, private val cmdRouter: CommandRouter) {
    var shardCards: MutableList<IShardCard> = mutableListOf()
    var destinationCards: MutableList<IDestinationCard> = mutableListOf()
    var stagedDestCards: MutableList<IDestinationCard> = mutableListOf()

    init {
        this.cmdRouter.registerCallback(UPDATE_HAND) { handleUpdateHand(it as UpdateHandCommand) }
    }

    fun handleUpdateHand(updateHand: UpdateHandCommand){
        destinationCards = updateHand.destinations.toMutableList()
        shardCards = updateHand.shardCards.toMutableList()
    }

    fun handleDealCards(dealCards: DealCardsCommand){
        shardCards.addAll(dealCards.shardCards)
        stagedDestCards = dealCards.destinations.toMutableList()
    }

    fun selectDestinationCards(selectedCards: List<IDestinationCard>, discardedCards: List<IDestinationCard>){
        destinationCards.addAll(selectedCards)
        server.executeCommand(IDiscardDestinationsCommand(discardedCards))
    }

    fun getCardsForRoute(route: Route): List<IShardCard> {
        TODO("write algorithm to determine optimal cards to claim route")
        // If regular route type, fill with that card type, then with infinity shards
        // If any type, fill with number of shards that closest matches the number required
        // If cannot be claimed, return empty list

        // TODO: Reserve cards for a route?
    }
}