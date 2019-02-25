package models

class RouteList {

    var routesByRouteId = mutableMapOf<String, Route>()

    constructor() {

    }

    companion object {
        /* CITY NAMES */
        const val DARK_DIMENSION = "darkdimension"
        const val GIBBORIM = "gibborim"
        const val GALACTUS = "galactus"
        const val ZENN_LA = "zennla"
        const val KNOWHERE = "knowhere"
        const val CHITAURI_SANCTUARY = "chitaurisanctuary"
        const val ZEN_WHOBERI = "zenwhoberi"
        const val EGO = "ego"
        const val KITSON = "kitson"
        const val CAVE_OF_THE_DRAGON = "caveofthedragon"
        const val KUN_LUN = "kunlun"
        const val VANAHEIM = "vanaheim"
        const val TITAN = "titan"
        const val VORMIR = "vormin"
        const val SAKAAR = "sakaar"
        const val XANDAR = "xandar"
        const val CAVE_OF_AGES = "caveofages"
        const val SURTURS_LAIR = "surturslair"
        const val HALA = "hala"
        const val SVARTLHEIM = "svartlheim"
        const val NIFLHEIM = "niflheim"
        const val CONTRAXIA = "contraxia"
        const val QUANTUM_REALM = "quantumrealm"
        const val ASGARD = "asgard"
        const val NIDAVELLIR = "nidavellir"
        const val YOTUNHEIM = "yotunheim"
        const val HONG_KONG_SANCTUM = "hongkongsanctum"
        const val WAKANDA = "wakanda"
        const val SOKOVIA = "sokovia"
        const val SAN_FRANCISCO_PYM_LABS = "sanfrancisco(pymlabs)"
        const val AVENGERS_HQ = "avengershq"
        const val NEW_YORK_CITY = "newyorkcity"
        const val TRISKELION_SHIELD_HQ = "triskelion(shieldhq)"
        const val HELICARRIER = "helicarrier"
        const val KAMAR_TAJ = "kamartaj"
        const val MUSPELHEIM = "muspelheim"

        /* ROUTE NAMES */


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