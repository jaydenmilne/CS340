package services

import ICommandRouter
import IDestinationCard
import IShardCard
import IProxyServer
import commands.*
import models.Route
import RouteType

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.CardService: Handles shard cards and destination cards
 */
class CardService(private val server: IProxyServer, private val cmdRouter: ICommandRouter) {
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
        // If regular route type, fill with that card type, then with infinity shards
        if (route.type != RouteType.ANY){
            val list = getCardsForRouteType(route, route.type)
            return list ?: listOf()
        }

        // If any type, fill with number of shards that closest matches the number required
        val cardListsByType = RouteType.values().map{ getCardsForRouteType(route, it) ?: listOf() }.toMutableList()
        cardListsByType.removeIf{ it.isEmpty() }      // remove types that couldn't satisfy claiming requirements

        if(cardListsByType.isNotEmpty()){
            cardListsByType.sortBy { it.size  }
            return cardListsByType.first()
        } else {
            // If cannot be claimed, return empty list
            return listOf()
        }

        // TODO: Reserve cards for a route?
    }

    fun getCardsForRouteType(route: Route, type: RouteType): List<IShardCard>? {
        val typeCards = shardCards.filter { it.type == type.getMaterialType() }

        if (typeCards.size >= route.numCars){
            return typeCards.subList(0, route.numCars)
        }

        val infinityCards = shardCards.filter { it.type == MaterialType.INFINITY_GAUNTLET }

        if (typeCards.size + infinityCards.size >= route.numCars){
            val cardsList = typeCards.toMutableList()
            cardsList.addAll(infinityCards.subList(0, route.numCars - typeCards.size))

            return cardsList
        }

        return  null
    }
}