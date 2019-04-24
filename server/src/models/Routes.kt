package models

import RouteType
import java.io.Serializable

class RouteList : Serializable {

    var routesByRouteId = mutableMapOf<String, Route>()

    init {
        routesByRouteId[DARKDIMENSION_GIBBORIM_1] = Route(DARKDIMENSION_GIBBORIM_1, setOf(DARK_DIMENSION, GIBBORIM), 1, RouteType.ANY, -1)
        routesByRouteId[DARKDIMENSION_GIBBORIM_2] = Route(DARKDIMENSION_GIBBORIM_2, setOf(DARK_DIMENSION, GIBBORIM), 1, RouteType.ANY, -1)
        routesByRouteId[CHITAURISANCTUARY_DARKDIMENSION] = Route(CHITAURISANCTUARY_DARKDIMENSION, setOf(DARK_DIMENSION, CHITAURI_SANCTUARY), 3, RouteType.ANY, -1)
        routesByRouteId[GALACTUS_GIBBORIM_1] = Route(GALACTUS_GIBBORIM_1, setOf(GIBBORIM, GALACTUS), 1, RouteType.ANY, -1)
        routesByRouteId[GALACTUS_GIBBORIM_2] = Route(GALACTUS_GIBBORIM_2, setOf(GIBBORIM, GALACTUS), 1, RouteType.ANY, -1)
        routesByRouteId[CHITAURISANCTUARY_GIBBORIM] = Route(CHITAURISANCTUARY_GIBBORIM, setOf(GIBBORIM, CHITAURI_SANCTUARY), 4, RouteType.ANY, -1)
        routesByRouteId[CHITAURISANCTUARY_VORMIR] = Route(CHITAURISANCTUARY_VORMIR, setOf(CHITAURI_SANCTUARY, VORMIR), 6, RouteType.SOUL, -1)
        routesByRouteId[CHITAURISANCTUARY_ZENWHOBERI] = Route(CHITAURISANCTUARY_ZENWHOBERI, setOf(CHITAURI_SANCTUARY, ZEN_WHOBERI), 4, RouteType.ANY, -1)
        routesByRouteId[GIBBORIM_ZENWHOBERI] = Route(GIBBORIM_ZENWHOBERI, setOf(GIBBORIM, ZEN_WHOBERI), 6, RouteType.MIND, -1)
        routesByRouteId[EGO_GALACTUS] = Route(EGO_GALACTUS, setOf(GALACTUS, EGO), 6, RouteType.SPACE, -1)
        routesByRouteId[EGO_ZENWHOBERI] = Route(EGO_ZENWHOBERI, setOf(EGO, ZEN_WHOBERI), 3, RouteType.REALITY, -1)
        routesByRouteId[GALACTUS_ZENNLA_1] = Route(GALACTUS_ZENNLA_1, setOf(GALACTUS, ZENN_LA), 5, RouteType.PALLADIUM, -1)
        routesByRouteId[GALACTUS_ZENNLA_2] = Route(GALACTUS_ZENNLA_2, setOf(GALACTUS, ZENN_LA), 5, RouteType.REALITY, -1)
        routesByRouteId[EGO_ZENNLA_1] = Route(EGO_ZENNLA_1, setOf(ZENN_LA, EGO), 5, RouteType.POWER, -1)
        routesByRouteId[EGO_ZENNLA_2] = Route(EGO_ZENNLA_2, setOf(ZENN_LA, EGO), 5, RouteType.SOUL, -1)
        routesByRouteId[KNOWHERE_ZENNLA_1] = Route(KNOWHERE_ZENNLA_1, setOf(ZENN_LA, KNOWHERE), 3, RouteType.REALITY, -1)
        routesByRouteId[KNOWHERE_ZENNLA_2] = Route(KNOWHERE_ZENNLA_2, setOf(ZENN_LA, KNOWHERE), 3, RouteType.MIND, -1)
        routesByRouteId[KITSON_KNOWHERE] = Route(KITSON_KNOWHERE, setOf(KNOWHERE, KITSON), 2, RouteType.ANY, -1)
        routesByRouteId[EGO_KITSON] = Route(EGO_KITSON, setOf(KITSON, EGO), 3, RouteType.POWER, -1)
        routesByRouteId[CAVEOFTHEDRAGON_KNOWHERE] = Route(CAVEOFTHEDRAGON_KNOWHERE, setOf(KNOWHERE, CAVE_OF_THE_DRAGON), 3, RouteType.ANY, -1)
        routesByRouteId[KNOWHERE_KUNLUN] = Route(KNOWHERE_KUNLUN, setOf(KNOWHERE, KUN_LUN), 6, RouteType.TIME, -1)
        routesByRouteId[CAVEOFTHEDRAGON_TITAN] = Route(CAVEOFTHEDRAGON_TITAN, setOf(CAVE_OF_THE_DRAGON, TITAN), 5, RouteType.SOUL, -1)
        routesByRouteId[CAVEOFTHEDRAGON_VANAHEIM] = Route(CAVEOFTHEDRAGON_VANAHEIM, setOf(CAVE_OF_THE_DRAGON, VANAHEIM), 3, RouteType.ANY, -1)
        routesByRouteId[CAVEOFTHEDRAGON_KUNLUN] = Route(CAVEOFTHEDRAGON_KUNLUN, setOf(CAVE_OF_THE_DRAGON, KUN_LUN), 3, RouteType.ANY, -1)
        routesByRouteId[KUNLUN_VANAHEIM] = Route(KUNLUN_VANAHEIM, setOf(VANAHEIM, KUN_LUN), 2, RouteType.ANY, -1)
        routesByRouteId[EGO_TITAN_1] = Route(EGO_TITAN_1, setOf(EGO, TITAN), 3, RouteType.VIBRANIUM, -1)
        routesByRouteId[EGO_TITAN_2] = Route(EGO_TITAN_2, setOf(EGO, TITAN), 3, RouteType.MIND, -1)
        routesByRouteId[TITAN_ZENWHOBERI] = Route(TITAN_ZENWHOBERI, setOf(ZEN_WHOBERI, TITAN), 4, RouteType.PALLADIUM, -1)
        routesByRouteId[SAKAAR_ZENWHOBERI] = Route(SAKAAR_ZENWHOBERI, setOf(ZEN_WHOBERI, SAKAAR), 6, RouteType.POWER, -1)
        routesByRouteId[VORMIR_ZENWHOBERI] = Route(VORMIR_ZENWHOBERI, setOf(ZEN_WHOBERI, VORMIR), 4, RouteType.SPACE, -1)
        routesByRouteId[XANDAR_ZENWHOBERI] = Route(XANDAR_ZENWHOBERI, setOf(ZEN_WHOBERI, XANDAR), 5, RouteType.VIBRANIUM, -1)
        routesByRouteId[TITAN_VANAHEIM] = Route(TITAN_VANAHEIM, setOf(TITAN, VANAHEIM), 2, RouteType.ANY, -1)
        routesByRouteId[CAVEOFAGES_TITAN_1] = Route(CAVEOFAGES_TITAN_1, setOf(TITAN, CAVE_OF_AGES), 4, RouteType.TIME, -1)
        routesByRouteId[CAVEOFAGES_TITAN_2] = Route(CAVEOFAGES_TITAN_2, setOf(TITAN, CAVE_OF_AGES), 4, RouteType.POWER, -1)
        routesByRouteId[TITAN_XANDAR] = Route(TITAN_XANDAR, setOf(TITAN, XANDAR), 4, RouteType.REALITY, -1)
        routesByRouteId[SURTURSLAIR_TITAN] = Route(SURTURSLAIR_TITAN, setOf(TITAN, SURTURS_LAIR), 4, RouteType.VIBRANIUM, -1)
        routesByRouteId[SURTURSLAIR_VANAHEIM] = Route(SURTURSLAIR_VANAHEIM, setOf(VANAHEIM, SURTURS_LAIR), 3, RouteType.SPACE, -1)
        routesByRouteId[KUNLUN_SURTURSLAIR] = Route(KUNLUN_SURTURSLAIR, setOf(KUN_LUN, SURTURS_LAIR), 5, RouteType.MIND, -1)
        routesByRouteId[HALA_KUNLUN] = Route(HALA_KUNLUN, setOf(KUN_LUN, HALA), 4, RouteType.VIBRANIUM, -1)
        routesByRouteId[KUNLUN_SVARTLHEIM] = Route(KUNLUN_SVARTLHEIM, setOf(KUN_LUN, SVARTLHEIM), 6, RouteType.PALLADIUM, -1)
        routesByRouteId[HONGKONGSANCTUM_VORMIR] = Route(HONGKONGSANCTUM_VORMIR, setOf(VORMIR, HONG_KONG_SANCTUM), 6, RouteType.ANY, -1)
        routesByRouteId[SAKAAR_VORMIR] = Route(SAKAAR_VORMIR, setOf(VORMIR, SAKAAR), 4, RouteType.TIME, -1)
        routesByRouteId[HONGKONGSANCTUM_SAKAAR] = Route(HONGKONGSANCTUM_SAKAAR, setOf(SAKAAR, HONG_KONG_SANCTUM), 3, RouteType.ANY, -1)
        routesByRouteId[PYMLABS_SAKAAR] = Route(PYMLABS_SAKAAR, setOf(SAKAAR, PYM_LABS), 6, RouteType.REALITY, -1)
        routesByRouteId[NIDAVELLIR_SAKAAR] = Route(NIDAVELLIR_SAKAAR, setOf(SAKAAR, NIDAVELLIR), 3, RouteType.VIBRANIUM, -1)
        routesByRouteId[SAKAAR_XANDAR_1] = Route(SAKAAR_XANDAR_1, setOf(SAKAAR, XANDAR), 2, RouteType.ANY, -1)
        routesByRouteId[SAKAAR_XANDAR_2] = Route(SAKAAR_XANDAR_2, setOf(SAKAAR, XANDAR), 2, RouteType.ANY, -1)
        routesByRouteId[CAVEOFAGES_XANDAR_1] = Route(CAVEOFAGES_XANDAR_1, setOf(XANDAR, CAVE_OF_AGES), 1, RouteType.ANY, -1)
        routesByRouteId[CAVEOFAGES_XANDAR_2] = Route(CAVEOFAGES_XANDAR_2, setOf(XANDAR, CAVE_OF_AGES), 1, RouteType.ANY, -1)
        routesByRouteId[CAVEOFAGES_SURTURSLAIR_1] = Route(CAVEOFAGES_SURTURSLAIR_1, setOf(CAVE_OF_AGES, SURTURS_LAIR), 2, RouteType.ANY, -1)
        routesByRouteId[CAVEOFAGES_SURTURSLAIR_2] = Route(CAVEOFAGES_SURTURSLAIR_2, setOf(CAVE_OF_AGES, SURTURS_LAIR), 2, RouteType.ANY, -1)
        routesByRouteId[HALA_SURTURSLAIR_1] = Route(HALA_SURTURSLAIR_1, setOf(SURTURS_LAIR, HALA), 2, RouteType.ANY, -1)
        routesByRouteId[HALA_SURTURSLAIR_2] = Route(HALA_SURTURSLAIR_2, setOf(SURTURS_LAIR, HALA), 2, RouteType.ANY, -1)
        routesByRouteId[HALA_SVARTLHEIM_1] = Route(HALA_SVARTLHEIM_1, setOf(HALA, SVARTLHEIM), 1, RouteType.ANY, -1)
        routesByRouteId[HALA_SVARTLHEIM_2] = Route(HALA_SVARTLHEIM_2, setOf(HALA, SVARTLHEIM), 1, RouteType.ANY, -1)
        routesByRouteId[NIFLHEIM_SVARTLHEIM] = Route(NIFLHEIM_SVARTLHEIM, setOf(SVARTLHEIM, NIFLHEIM), 2, RouteType.ANY, -1)
        routesByRouteId[CONTRAXIA_SURTURSLAIR] = Route(CONTRAXIA_SURTURSLAIR, setOf(SURTURS_LAIR, CONTRAXIA), 2, RouteType.ANY, -1)
        routesByRouteId[CONTRAXIA_HALA] = Route(CONTRAXIA_HALA, setOf(HALA, CONTRAXIA), 2, RouteType.ANY, -1)
        routesByRouteId[CONTRAXIA_NIFLHEIM] = Route(CONTRAXIA_NIFLHEIM, setOf(CONTRAXIA, NIFLHEIM), 3, RouteType.PALLADIUM, -1)
        routesByRouteId[ASGARD_CONTRAXIA] = Route(ASGARD_CONTRAXIA, setOf(CONTRAXIA, ASGARD), 2, RouteType.ANY, -1)
        routesByRouteId[CONTRAXIA_QUANTUMREALM] = Route(CONTRAXIA_QUANTUMREALM, setOf(CONTRAXIA, QUANTUM_REALM), 3, RouteType.SOUL, -1)
        routesByRouteId[ASGARD_CAVEOFAGES_1] = Route(ASGARD_CAVEOFAGES_1, setOf(CAVE_OF_AGES, ASGARD), 2, RouteType.REALITY, -1)
        routesByRouteId[ASGARD_CAVEOFAGES_2] = Route(ASGARD_CAVEOFAGES_2, setOf(CAVE_OF_AGES, ASGARD), 2, RouteType.SPACE, -1)
        routesByRouteId[ASGARD_QUANTUMREALM] = Route(ASGARD_QUANTUMREALM, setOf(ASGARD, QUANTUM_REALM), 2, RouteType.ANY, -1)
        routesByRouteId[ASGARD_NEWYORKCITY] = Route(ASGARD_NEWYORKCITY, setOf(ASGARD, NEW_YORK_CITY), 5, RouteType.PALLADIUM, -1)
        routesByRouteId[ASGARD_NIDAVELLIR_1] = Route(ASGARD_NIDAVELLIR_1, setOf(ASGARD, NIDAVELLIR), 2, RouteType.PALLADIUM, -1)
        routesByRouteId[ASGARD_NIDAVELLIR_2] = Route(ASGARD_NIDAVELLIR_2, setOf(ASGARD, NIDAVELLIR), 2, RouteType.SOUL, -1)
        routesByRouteId[NIDAVELLIR_XANDAR] = Route(NIDAVELLIR_XANDAR, setOf(XANDAR, NIDAVELLIR), 4, RouteType.SPACE, -1)
        routesByRouteId[NEWYORKCITY_NIDAVELLIR_1] = Route(NEWYORKCITY_NIDAVELLIR_1, setOf(NIDAVELLIR, NEW_YORK_CITY), 3, RouteType.POWER, -1)
        routesByRouteId[NEWYORKCITY_NIDAVELLIR_2] = Route(NEWYORKCITY_NIDAVELLIR_2, setOf(NIDAVELLIR, NEW_YORK_CITY), 3, RouteType.TIME, -1)
        routesByRouteId[NIDAVELLIR_PYMLABS] = Route(NIDAVELLIR_PYMLABS, setOf(NIDAVELLIR, PYM_LABS), 4, RouteType.SOUL, -1)
        routesByRouteId[HONGKONGSANCTUM_PYMLABS] = Route(HONGKONGSANCTUM_PYMLABS, setOf(HONG_KONG_SANCTUM, PYM_LABS), 2, RouteType.ANY, -1)
        routesByRouteId[HONGKONGSANCTUM_WAKANDA] = Route(HONGKONGSANCTUM_WAKANDA, setOf(HONG_KONG_SANCTUM, WAKANDA), 5, RouteType.TIME, -1)
        routesByRouteId[MUSPELHEIM_NIFLHEIM] = Route(MUSPELHEIM_NIFLHEIM, setOf(NIFLHEIM, MUSPELHEIM), 6, RouteType.VIBRANIUM, -1)
        routesByRouteId[NIFLHEIM_YOTUNHEIM_1] = Route(NIFLHEIM_YOTUNHEIM_1, setOf(NIFLHEIM, YOTUNHEIM), 4, RouteType.MIND, -1)
        routesByRouteId[NIFLHEIM_YOTUNHEIM_2] = Route(NIFLHEIM_YOTUNHEIM_2, setOf(NIFLHEIM, YOTUNHEIM), 4, RouteType.POWER, -1)
        routesByRouteId[MUSPELHEIM_YOTUNHEIM] = Route(MUSPELHEIM_YOTUNHEIM, setOf(YOTUNHEIM, MUSPELHEIM), 5, RouteType.SPACE, -1)
        routesByRouteId[QUANTUMREALM_YOTUNHEIM] = Route(QUANTUMREALM_YOTUNHEIM, setOf(YOTUNHEIM, QUANTUM_REALM), 1, RouteType.ANY, -1)
        routesByRouteId[KAMARTAJ_YOTUNHEIM] = Route(KAMARTAJ_YOTUNHEIM, setOf(YOTUNHEIM, KAMAR_TAJ), 2, RouteType.ANY, -1)
        routesByRouteId[KAMARTAJ_MUSPELHEIM] = Route(KAMARTAJ_MUSPELHEIM, setOf(KAMAR_TAJ, MUSPELHEIM), 4, RouteType.REALITY, -1)
        routesByRouteId[HELICARRIER_KAMARTAJ] = Route(HELICARRIER_KAMARTAJ, setOf(KAMAR_TAJ, HELICARRIER), 2, RouteType.ANY, -1)
        routesByRouteId[HELICARRIER_YOTUNHEIM_1] = Route(HELICARRIER_YOTUNHEIM_1, setOf(YOTUNHEIM, HELICARRIER), 2, RouteType.ANY, -1)
        routesByRouteId[HELICARRIER_YOTUNHEIM_2] = Route(HELICARRIER_YOTUNHEIM_2, setOf(YOTUNHEIM, HELICARRIER), 2, RouteType.ANY, -1)
        routesByRouteId[HELICARRIER_QUANTUMREALM] = Route(HELICARRIER_QUANTUMREALM, setOf(QUANTUM_REALM, HELICARRIER), 3, RouteType.TIME, -1)
        routesByRouteId[NEWYORKCITY_QUANTUMREALM] = Route(NEWYORKCITY_QUANTUMREALM, setOf(QUANTUM_REALM, NEW_YORK_CITY), 4, RouteType.MIND, -1)
        routesByRouteId[HELICARRIER_TRISKELION_1] = Route(HELICARRIER_TRISKELION_1, setOf(HELICARRIER, TRISKELION), 2, RouteType.ANY, -1)
        routesByRouteId[HELICARRIER_TRISKELION_2] = Route(HELICARRIER_TRISKELION_2, setOf(HELICARRIER, TRISKELION), 2, RouteType.ANY, -1)
        routesByRouteId[HELICARRIER_NEWYORKCITY] = Route(HELICARRIER_NEWYORKCITY, setOf(HELICARRIER, NEW_YORK_CITY), 2, RouteType.ANY, -1)
        routesByRouteId[NEWYORKCITY_TRISKELION] = Route(NEWYORKCITY_TRISKELION, setOf(NEW_YORK_CITY, TRISKELION), 2, RouteType.ANY, -1)
        routesByRouteId[AVENGERSHQ_TRISKELION_1] = Route(AVENGERSHQ_TRISKELION_1, setOf(TRISKELION, AVENGERS_HQ), 2, RouteType.POWER, -1)
        routesByRouteId[AVENGERSHQ_TRISKELION_2] = Route(AVENGERSHQ_TRISKELION_2, setOf(TRISKELION, AVENGERS_HQ), 2, RouteType.TIME, -1)
        routesByRouteId[AVENGERSHQ_NEWYORKCITY_1] = Route(AVENGERSHQ_NEWYORKCITY_1, setOf(NEW_YORK_CITY, AVENGERS_HQ), 2, RouteType.SOUL, -1)
        routesByRouteId[AVENGERSHQ_NEWYORKCITY_2] = Route(AVENGERSHQ_NEWYORKCITY_2, setOf(NEW_YORK_CITY, AVENGERS_HQ), 2, RouteType.PALLADIUM, -1)
        routesByRouteId[NEWYORKCITY_PYMLABS] = Route(NEWYORKCITY_PYMLABS, setOf(NEW_YORK_CITY, PYM_LABS), 2, RouteType.ANY, -1)
        routesByRouteId[PYMLABS_WAKANDA] = Route(PYMLABS_WAKANDA, setOf(PYM_LABS, WAKANDA), 3, RouteType.ANY, -1)
        routesByRouteId[AVENGERSHQ_WAKANDA] = Route(AVENGERSHQ_WAKANDA, setOf(WAKANDA, AVENGERS_HQ), 3, RouteType.SPACE, -1)
        routesByRouteId[AVENGERSHQ_SOKOVIA_1] = Route(AVENGERSHQ_SOKOVIA_1, setOf(AVENGERS_HQ, SOKOVIA), 2, RouteType.MIND, -1)
        routesByRouteId[AVENGERSHQ_SOKOVIA_2] = Route(AVENGERSHQ_SOKOVIA_2, setOf(AVENGERS_HQ, SOKOVIA), 2, RouteType.VIBRANIUM, -1)
        routesByRouteId[SOKOVIA_WAKANDA_1] = Route(SOKOVIA_WAKANDA_1, setOf(SOKOVIA, WAKANDA), 2, RouteType.ANY, -1)
        routesByRouteId[SOKOVIA_WAKANDA_2] = Route(SOKOVIA_WAKANDA_2, setOf(SOKOVIA, WAKANDA), 2, RouteType.ANY, -1)
    }

    fun pathBetweenCities(city1: String, city2: String, userId: Int): Boolean {
        val queue = mutableListOf(city1)
        val visited = mutableMapOf<String, Boolean>()

        while (queue.isNotEmpty()) {
            val node = queue.removeAt(0)
            visited[node] = true

            if (node == city2) {
                return true
            }

            var discoveredNodes = routesByRouteId.map { e -> e.value }
                    .filter { r -> r.cities.contains(node) && r.ownerId == userId }
                    .map { r -> r.cities.minus(node).elementAt(0) }

            discoveredNodes = discoveredNodes.filter { c -> !(visited[c] ?: false) }

            queue.addAll(discoveredNodes)
        }

        return false

    }
}

