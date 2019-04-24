package test

import services.RouteService
import IProxyServer
import ICommandRouter
import ICommand
import IShardCard
import MaterialType
import models.*
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DummyServer(): IProxyServer {
    override fun executeCommand(command: ICommand) { }
    override fun poll() { }
    override fun setAuthToken(authToken: String?) { }
}

class DummyCommandRouter(): ICommandRouter {
    override fun addNewCommands(commands: List<ICommand>) { }
    override fun processCommands() { }
    override fun registerCallback(command: String, callback: (cmd: ICommand) -> Unit) { }
}

private fun makeHand(cards: List<MaterialType>): List<IShardCard>{
    return cards.map { IShardCard(it) }
}

private fun makeHandOfType(type: MaterialType, numCards: Int): List<IShardCard>{
    return List(numCards) { IShardCard(type) }
}

fun testClaimRoutePossibleType(){
    println("**testClaimRoutePossibleType**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[CONTRAXIA_QUANTUMREALM]!!

    val handExact = makeHandOfType(MaterialType.SOUL_SHARD, route.numCars)
    val handExtra = makeHand(listOf(MaterialType.SOUL_SHARD, MaterialType.SOUL_SHARD, MaterialType.SOUL_SHARD, MaterialType.MIND_SHARD))
    val handInfinity = makeHandOfType(MaterialType.INFINITY_GAUNTLET, route.numCars)
    val handMixed = makeHand(listOf(MaterialType.SOUL_SHARD, MaterialType.SOUL_SHARD, MaterialType.INFINITY_GAUNTLET))


    assertTrue { routeService.claimRoutePossible(route, handExact, 100) }
    assertTrue { routeService.claimRoutePossible(route, handExtra, 100) }
    assertTrue { routeService.claimRoutePossible(route, handInfinity, 100) }
    assertTrue { routeService.claimRoutePossible(route, handMixed, 100) }
    println("--pass")
}

fun testClaimRoutePossibleAny(){
    println("**testClaimRoutePossibleAny**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[ASGARD_QUANTUMREALM]!!

    val handExact1 = makeHandOfType(MaterialType.SOUL_SHARD, route.numCars)
    val handExact2 = makeHandOfType(MaterialType.MIND_SHARD, route.numCars)
    val handExtra = makeHand(listOf(MaterialType.SOUL_SHARD, MaterialType.SOUL_SHARD, MaterialType.MIND_SHARD))
    val handInfinity = makeHandOfType(MaterialType.INFINITY_GAUNTLET, route.numCars)
    val handMixed = makeHand(listOf(MaterialType.SOUL_SHARD, MaterialType.MIND_SHARD))


    assertTrue { routeService.claimRoutePossible(route, handExact1, 100) }
    assertTrue { routeService.claimRoutePossible(route, handExact2, 100) }
    assertTrue { routeService.claimRoutePossible(route, handExtra, 100) }
    assertTrue { routeService.claimRoutePossible(route, handInfinity, 100) }
    assertFalse { routeService.claimRoutePossible(route, handMixed, 100) }
    println("--pass")
}

fun testClaimRoutePossibleNotEnoughCards(){
    println("**testClaimRoutePossibleNotEnoughCards**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[CONTRAXIA_QUANTUMREALM]!!

    val handNotEnough =  makeHandOfType(MaterialType.SOUL_SHARD, route.numCars - 1)

    assertFalse { routeService.claimRoutePossible(route, handNotEnough, 100) }
    println("--pass")
}

fun testClaimRoutePossibleDoubleRouteClaimed(){
    println("**testClaimRoutePossibleDoubleRouteClaimed**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[HELICARRIER_TRISKELION_1]!!
    map.routesByRouteId[HELICARRIER_TRISKELION_1]!!.ownerId = 1

    val hand = makeHandOfType(MaterialType.SOUL_SHARD, route.numCars)

    assertFalse { routeService.claimRoutePossible(route, hand, 100) }
    println("--pass")
}

fun testClaimRoutePossibleRouteAlreadyClaimed(){
    println("**testClaimRoutePossibleRouteAlreadyClaimed**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[HELICARRIER_TRISKELION_1]!!
    route.ownerId = 1

    val hand = makeHandOfType(MaterialType.SOUL_SHARD, route.numCars)

    assertFalse { routeService.claimRoutePossible(route, hand, 100) }
    println("--pass")
}

fun testClaimRoutePossibleNotEnoughCars(){
    println("**testClaimRoutePossibleNotEnoughCars**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[HELICARRIER_TRISKELION_1]!!

    val hand = makeHandOfType(MaterialType.SOUL_SHARD, route.numCars)

    assertTrue { routeService.claimRoutePossible(route, hand, route.numCars) }
    assertFalse { routeService.claimRoutePossible(route, hand, route.numCars - 1) }
    println("--pass")
}

fun testClaimRouteValid(){
    println("**testClaimRouteValid**")
    val map = RouteList()
    val routeService = RouteService(DummyServer(), DummyCommandRouter(), map, false)
    val route = map.routesByRouteId[GIBBORIM_ZENWHOBERI]!!

    val handExact = makeHandOfType(MaterialType.MIND_SHARD, route.numCars)
    val handNotEnough = makeHandOfType(MaterialType.MIND_SHARD, route.numCars - 1)
    val handTooMany = makeHandOfType(MaterialType.MIND_SHARD, route.numCars + 1)
    val handOtherStuff1 = handNotEnough.toMutableList(); handOtherStuff1.add(IShardCard(MaterialType.PALLADIUM))
    val handOtherStuff2 = handExact.toMutableList(); handOtherStuff2.add(IShardCard(MaterialType.PALLADIUM))

    assertTrue { routeService.claimRouteValid(route, handExact, route.numCars) }
    assertFalse { routeService.claimRouteValid(route, handNotEnough, route.numCars) }
    assertFalse { routeService.claimRouteValid(route, handTooMany, route.numCars) }
    assertFalse { routeService.claimRouteValid(route, handOtherStuff1, route.numCars) }
    assertFalse { routeService.claimRouteValid(route, handOtherStuff2, route.numCars) }
    println("--pass")
}

fun main(args: Array<String>) {
    testClaimRoutePossibleType()
    testClaimRoutePossibleAny()
    testClaimRoutePossibleNotEnoughCards()
    testClaimRoutePossibleDoubleRouteClaimed()
    testClaimRoutePossibleRouteAlreadyClaimed()
    testClaimRoutePossibleNotEnoughCars()

    testClaimRouteValid()
}
