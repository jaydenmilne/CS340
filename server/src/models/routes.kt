package models

class RouteList {

    var routesByRouteId = mutableMapOf<String, Route>()

    constructor() {

    }
}

enum class RouteType(val type: String) {
    ANY("any"),
    REALITY("reality"),
    SOUL("soul"),
    SPACE("space"),
    MIND("mind"),
    POWER("power"),
    TIME("time"),
    VIBRANIUM("vibranium"),
    PALLADIUM("palladium")
}

class Route(routeId: String, cities: Array<String>, numCars: Int, type: RouteType, ownerId: Int) {

}