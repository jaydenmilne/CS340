package services

import CommandRouter
import ProxyServer
import commands.*
import IShardCard
import models.RouteList

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.RouteService: Handles RouteList updates and route claiming
 */
class RouteService(private val server: ProxyServer, private val cmdRouter: CommandRouter, private val map: RouteList) {
    init {
        this.cmdRouter.registerCallback(ROUTE_CLAIMED) { handleRouteClaimed(it as RouteClaimedCommand) }
    }


    fun claimRoute(routeId: String, shardsUsed: List<IShardCard>){
        server.executeCommand(IClaimRouteCommand(routeId, shardsUsed))
    }


    fun handleRouteClaimed(routeClaimed: RouteClaimedCommand){
        map.routesByRouteId[routeClaimed.routeId]!!.ownerId = routeClaimed.userId
    }
}