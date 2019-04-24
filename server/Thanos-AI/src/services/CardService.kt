package services

import CommandRouter
import IDestinationCard
import IShardCard
import ProxyServer
import commands.*

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
}