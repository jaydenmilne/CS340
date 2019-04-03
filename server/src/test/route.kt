package test

import models.*

fun main(args: Array<String>) {
    val list = RouteList()
    list.routesByRouteId[DARKDIMENSION_GIBBORIM_1]!!.ownerId = 1

    assert(list.pathBetweenCities(DARK_DIMENSION, GIBBORIM, 1))
    assert(list.pathBetweenCities(GIBBORIM, DARK_DIMENSION, 1))
    assert(!list.pathBetweenCities(DARK_DIMENSION, GALACTUS, 1))

    list.routesByRouteId[GALACTUS_GIBBORIM_1]!!.ownerId = 1
    assert(list.pathBetweenCities(DARK_DIMENSION, GALACTUS, 1))
}