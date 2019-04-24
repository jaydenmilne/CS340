package services

import CommandRouter
import ProxyServer
import commands.*
import IShardCard
import models.Route
import models.RouteList
import RouteType

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.RouteService: Handles RouteList updates and route claiming
 */
class RouteService(private val server: ProxyServer, private val cmdRouter: CommandRouter, private val map: RouteList,
                   private val lessThan4Players: Boolean) {
    init {
        this.cmdRouter.registerCallback(ROUTE_CLAIMED) { handleRouteClaimed(it as RouteClaimedCommand) }
    }


    fun claimRoute(routeId: String, shardsUsed: List<IShardCard>){
        server.executeCommand(IClaimRouteCommand(routeId, shardsUsed))
    }

    fun handleRouteClaimed(routeClaimed: RouteClaimedCommand){
        map.routesByRouteId[routeClaimed.routeId]!!.ownerId = routeClaimed.userId
    }


    /* Determine if it is possible to claim a route with the given cards */
    fun claimRoutePossible(route: Route, hand: List<IShardCard>, numCarsRemaining: Int): Boolean {
        if(route.ownerId != -1) return false

        when (route.type){  // Check if user has the right cards
            RouteType.ANY -> if (!canClaimRouteAny(hand, route.numCars)) return false
            else -> if (!canClaimRouteType(route.type, hand, route.numCars)) return false
        }

        if(route.numCars > numCarsRemaining) return false

        if(lessThan4Players && isDoubleRoute(route) && otherDoubleRouteClaimed(route))  return false

        return true
    }

    /* Determine if the supplied cards constitute a valid route claim (no extra cards)*/
    fun claimRouteValid(route: Route, cards: List<IShardCard>, numCarsRemaining: Int): Boolean {
        return claimRoutePossible(route, cards, numCarsRemaining) && cards.size == route.numCars
    }

    private fun canClaimRouteType(type: RouteType, hand: List<IShardCard>, cardsNeeded: Int): Boolean {
        val cardCount = hand.count { MaterialType.matchesRouteType(type, it.type) }
        return cardCount >= cardsNeeded
    }

    private fun canClaimRouteAny(cards: List<IShardCard>, cardsNeeded: Int): Boolean {
        RouteType.values().forEach { if (canClaimRouteType(it, cards, cardsNeeded)) return true }
        return false        // route could not be claimed with any card type
    }

    private fun isDoubleRoute(route: Route): Boolean {
        return route.routeId.endsWith('1') || route.routeId.endsWith('2')
    }

    private fun otherDoubleRouteClaimed(route: Route): Boolean {
        return map.routesByRouteId[getOtherDoubleRouteId(route)]!!.ownerId != -1
    }

    private fun getOtherDoubleRouteId(route: Route): String{
        val routeBrother = route.routeId.dropLast(1)

        return when(route.routeId.last()){
            '1' -> routeBrother + '2'
            '2' -> routeBrother + '1'
            else -> ""      // Not a double route
        }
    }
}