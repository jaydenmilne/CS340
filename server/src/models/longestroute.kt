package models

import commands.UpdatePlayerCommand

class LongestRouteManager(private val game : Game) {
    var currentPlayerWithLongestRoute = -1
    var longestRoute = 0

    // All of the cities in the game
    var cities = mutableSetOf<String>()

    // All of the outgoing edges associated with a particular city
    var adjacencyList = mutableMapOf<String, MutableSet<Route>>()

    // Used to avoid loops when doing a DFS through the cities
    var markedRoutes = mutableMapOf<String, Boolean>()

    fun init() {
        // Create set of all cities
        for ((routeId, route) in game.routes.routesByRouteId) {
            cities.addAll(route.cities)
            markedRoutes.set(routeId, false)
        }

        // Create adjacency list representation of the map
        for ((_, route) in game.routes.routesByRouteId) {
            for (city in route.cities) {
                val cityList = adjacencyList.getOrPut(city) {
                    mutableSetOf()
                }
                cityList.add(route)
            }
        }

    }


    /**
     * Convenience function to find the other city in the array of cities on a route
     */
    private fun otherCity(route : Route, firstCity : String) : String {
        for (city in route.cities) {
            if (city == firstCity) continue
            return city
        }

        return "I know what I'm doing Kotlin"
    }

    /**
     * Recursive function to compute the longest route possible from a given city
     */
    private fun longestPathFromCity(city : String, longestSoFar : Int, playerid: Int) : Int {
        val outgoingRoutes = adjacencyList[city]
        var longestFromThisCity = 0

        // outgoingRoutes should never be empty but need to please the Kotlin gods
        for (route in outgoingRoutes.orEmpty()) {

            // If this route is not owned by this player or is marked (already used higher up in the stack),
            // skip it.
            if (route.ownerId != playerid || markedRoutes[route.routeId] == true)
                continue

            // Mark this route as used so that future calls to longestPathFromCity
            // don't re-use it
            markedRoutes[route.routeId] = true

            // Recursively compute the longest possible route from this city taking
            // this route
            val longestTakingThisRoute = longestPathFromCity(
                    otherCity(route, city),
                    longestSoFar + route.numCars,
                    playerid)

            // Check if this is longer than the current longest possible route
            if (longestTakingThisRoute > longestFromThisCity) {
                longestFromThisCity = longestTakingThisRoute
            }

            // At this point, the longest route possible by taking this route is
            // longest, so unmark it
            markedRoutes[route.routeId] = false

        }

        return longestFromThisCity
    }

    /**
     * Called when a player claims a route. Recomputes who has the longest route
     */
    fun playerClaimedRoute(playerid : Int) {
        // Need to re-calculate the longest route for this given player
        var playerLongest = 0

        // Assuming that each city could be the start of the longest path, compute the
        // longest path possible from each city
        for (city in cities) {
            val longestFromCity = longestPathFromCity(city, playerLongest, playerid)
            if (longestFromCity > playerLongest) {
                playerLongest = longestFromCity
            }
        }

        var playerWithLongestRoute = currentPlayerWithLongestRoute
        var previousPlayerWithLongestRoute = currentPlayerWithLongestRoute

        if (playerLongest > longestRoute) {
            // This player now has the longest route, they need the card
            previousPlayerWithLongestRoute = playerWithLongestRoute
            playerWithLongestRoute = playerid
            longestRoute = playerLongest
        }

        // Update the longest path value on the player
        for (player in game.players) {
            if (previousPlayerWithLongestRoute != -1 && player.userId == previousPlayerWithLongestRoute) {
                player.hasLongestRoute = false
            }

            if (playerWithLongestRoute != -1 && player.userId == playerWithLongestRoute) {
                player.hasLongestRoute = true
            }

            if (player.userId == playerid) {
                player.longestRouteLength = playerLongest
            }

            // Schedule an update player
            game.broadcast(UpdatePlayerCommand(player.toGamePlayer()))
        }

        // TODO: Put notification in chat that this player now has the longest route

    }

}