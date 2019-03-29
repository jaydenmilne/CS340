package models

import commands.CommandException
import java.lang.Exception

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
const val VORMIR = "vormir"
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
const val PYM_LABS = "pymlabs"
const val AVENGERS_HQ = "avengershq"
const val NEW_YORK_CITY = "newyorkcity"
const val TRISKELION = "triskelion"
const val HELICARRIER = "helicarrier"
const val KAMAR_TAJ = "kamartaj"
const val MUSPELHEIM = "muspelheim"

/* ROUTE NAMES */
const val DARKDIMENSION_GIBBORIM_1 = "darkdimension_gibborim_1"
const val DARKDIMENSION_GIBBORIM_2 = "darkdimension_gibborim_2"
const val CHITAURISANCTUARY_DARKDIMENSION = "chitaurisanctuary_darkdimension"
const val GALACTUS_GIBBORIM_1 = "galactus_gibborim_1"
const val GALACTUS_GIBBORIM_2 = "galactus_gibborim_2"
const val CHITAURISANCTUARY_GIBBORIM = "chitaurisanctuary_gibborim"
const val CHITAURISANCTUARY_VORMIR = "chitaurisanctuary_vormir"
const val CHITAURISANCTUARY_ZENWHOBERI = "chitaurisanctuary_zenwhoberi"
const val GIBBORIM_ZENWHOBERI = "gibborim_zenwhoberi"
const val EGO_GALACTUS = "ego_galactus"
const val EGO_ZENWHOBERI = "ego_zenwhoberi"
const val GALACTUS_ZENNLA_1 = "galactus_zennla_1"
const val GALACTUS_ZENNLA_2 = "galactus_zennla_2"
const val EGO_ZENNLA_1 = "ego_zennla_1"
const val EGO_ZENNLA_2 = "ego_zennla_2"
const val KNOWHERE_ZENNLA_1 = "knowhere_zennla_1"
const val KNOWHERE_ZENNLA_2 = "knowhere_zennla_2"
const val KITSON_KNOWHERE = "kitson_knowhere"
const val EGO_KITSON = "ego_kitson"
const val CAVEOFTHEDRAGON_KNOWHERE = "caveofthedragon_knowhere"
const val KNOWHERE_KUNLUN = "knowhere_kunlun"
const val CAVEOFTHEDRAGON_TITAN = "caveofthedragon_titan"
const val CAVEOFTHEDRAGON_VANAHEIM = "caveofthedragon_vanaheim"
const val CAVEOFTHEDRAGON_KUNLUN = "caveofthedragon_kunlun"
const val KUNLUN_VANAHEIM = "kunlun_vanaheim"
const val EGO_TITAN_1 = "ego_titan_1"
const val EGO_TITAN_2 = "ego_titan_2"
const val TITAN_ZENWHOBERI = "titan_zenwhoberi"
const val SAKAAR_ZENWHOBERI = "sakaar_zenwhoberi"
const val VORMIR_ZENWHOBERI = "vormir_zenwhoberi"
const val XANDAR_ZENWHOBERI = "xandar_zenwhoberi"
const val TITAN_VANAHEIM = "titan_vanaheim"
const val CAVEOFAGES_TITAN_1 = "caveofages_titan_1"
const val CAVEOFAGES_TITAN_2 = "caveofages_titan_2"
const val TITAN_XANDAR = "titan_xandar"
const val SURTURSLAIR_TITAN = "surturslair_titan"
const val SURTURSLAIR_VANAHEIM = "surturslair_vanaheim"
const val KUNLUN_SURTURSLAIR = "kunlun_surturslair"
const val HALA_KUNLUN = "hala_kunlun"
const val KUNLUN_SVARTLHEIM = "kunlun_svartlheim"
const val HONGKONGSANCTUM_VORMIR = "hongkongsanctum_vormir"
const val SAKAAR_VORMIR = "sakaar_vormir"
const val HONGKONGSANCTUM_SAKAAR = "hongkongsanctum_sakaar"
const val PYMLABS_SAKAAR = "pymlabs_sakaar"
const val NIDAVELLIR_SAKAAR = "nidavellir_sakaar"
const val SAKAAR_XANDAR_1 = "sakaar_xandar_1"
const val SAKAAR_XANDAR_2 = "sakaar_xandar_2"
const val CAVEOFAGES_XANDAR_1 = "caveofages_xandar_1"
const val CAVEOFAGES_XANDAR_2 = "caveofages_xandar_2"
const val CAVEOFAGES_SURTURSLAIR_1 = "caveofages_surturslair_1"
const val CAVEOFAGES_SURTURSLAIR_2 = "caveofages_surturslair_2"
const val HALA_SURTURSLAIR_1 = "hala_surturslair_1"
const val HALA_SURTURSLAIR_2 = "hala_surturslair_2"
const val HALA_SVARTLHEIM_1 = "hala_svartlheim_1"
const val HALA_SVARTLHEIM_2 = "hala_svartlheim_2"
const val NIFLHEIM_SVARTLHEIM = "niflheim_svartlheim"
const val CONTRAXIA_SURTURSLAIR = "contraxia_surturslair"
const val CONTRAXIA_HALA = "contraxia_hala"
const val CONTRAXIA_NIFLHEIM = "contraxia_niflheim"
const val ASGARD_CONTRAXIA = "asgard_contraxia"
const val CONTRAXIA_QUANTUMREALM = "contraxia_quantumrealm"
const val ASGARD_CAVEOFAGES_1 = "asgard_caveofages_1"
const val ASGARD_CAVEOFAGES_2 = "asgard_caveofages_2"
const val ASGARD_QUANTUMREALM = "asgard_quantumrealm"
const val ASGARD_NEWYORKCITY = "asgard_newyorkcity"
const val ASGARD_NIDAVELLIR_1 = "asgard_nidavellir_1"
const val ASGARD_NIDAVELLIR_2 = "asgard_nidavellir_2"
const val NIDAVELLIR_XANDAR = "nidavellir_xandar"
const val NEWYORKCITY_NIDAVELLIR_1 = "newyorkcity_nidavellir_1"
const val NEWYORKCITY_NIDAVELLIR_2 = "newyorkcity_nidavellir_2"
const val NIDAVELLIR_PYMLABS = "nidavellir_pymlabs"
const val HONGKONGSANCTUM_PYMLABS = "hongkongsanctum_pymlabs"
const val HONGKONGSANCTUM_WAKANDA = "hongkongsanctum_wakanda"
const val MUSPELHEIM_NIFLHEIM = "muspelheim_niflheim"
const val NIFLHEIM_YOTUNHEIM_1 = "niflheim_yotunheim_1"
const val NIFLHEIM_YOTUNHEIM_2 = "niflheim_yotunheim_2"
const val MUSPELHEIM_YOTUNHEIM = "muspelheim_yotunheim"
const val QUANTUMREALM_YOTUNHEIM = "quantumrealm_yotunheim"
const val KAMARTAJ_YOTUNHEIM = "kamartaj_yotunheim"
const val KAMARTAJ_MUSPELHEIM = "kamartaj_muspelheim"
const val HELICARRIER_KAMARTAJ = "helicarrier_kamartaj"
const val HELICARRIER_YOTUNHEIM_1 = "helicarrier_yotunheim_1"
const val HELICARRIER_YOTUNHEIM_2 = "helicarrier_yotunheim_2"
const val HELICARRIER_QUANTUMREALM = "helicarrier_quantumrealm"
const val NEWYORKCITY_QUANTUMREALM = "newyorkcity_quantumrealm"
const val HELICARRIER_TRISKELION_1 = "helicarrier_triskelion_1"
const val HELICARRIER_TRISKELION_2 = "helicarrier_triskelion_2"
const val HELICARRIER_NEWYORKCITY = "helicarrier_newyorkcity"
const val NEWYORKCITY_TRISKELION = "newyorkcity_triskelion"
const val AVENGERSHQ_TRISKELION_1 = "avengershq_triskelion_1"
const val AVENGERSHQ_TRISKELION_2 = "avengershq_triskelion_2"
const val AVENGERSHQ_NEWYORKCITY_1 = "avengershq_newyorkcity_1"
const val AVENGERSHQ_NEWYORKCITY_2 = "avengershq_newyorkcity_2"
const val NEWYORKCITY_PYMLABS = "newyorkcity_pymlabs"
const val PYMLABS_WAKANDA = "pymlabs_wakanda"
const val AVENGERSHQ_WAKANDA = "avengershq_wakanda"
const val AVENGERSHQ_SOKOVIA_1 = "avengershq_sokovia_1"
const val AVENGERSHQ_SOKOVIA_2 = "avengershq_sokovia_2"
const val SOKOVIA_WAKANDA_1 = "sokovia_wakanda_1"
const val SOKOVIA_WAKANDA_2 = "sokovia_wakanda_2"

class RouteList {

    var routesByRouteId = mutableMapOf<String, Route>()

    init {
        routesByRouteId.put(DARKDIMENSION_GIBBORIM_1,           Route(DARKDIMENSION_GIBBORIM_1,             setOf(DARK_DIMENSION, GIBBORIM),              1, RouteType.ANY, -1))
        routesByRouteId.put(DARKDIMENSION_GIBBORIM_2,           Route(DARKDIMENSION_GIBBORIM_2,             setOf(DARK_DIMENSION, GIBBORIM),              1, RouteType.ANY, -1))
        routesByRouteId.put(CHITAURISANCTUARY_DARKDIMENSION,    Route(CHITAURISANCTUARY_DARKDIMENSION,      setOf(DARK_DIMENSION, CHITAURI_SANCTUARY),    3, RouteType.ANY, -1))
        routesByRouteId.put(GALACTUS_GIBBORIM_1,                Route(GALACTUS_GIBBORIM_1,                  setOf(GIBBORIM, GALACTUS),                    1, RouteType.ANY, -1))
        routesByRouteId.put(GALACTUS_GIBBORIM_2,                Route(GALACTUS_GIBBORIM_2,                  setOf(GIBBORIM, GALACTUS),                    1, RouteType.ANY, -1))
        routesByRouteId.put(CHITAURISANCTUARY_GIBBORIM,         Route(CHITAURISANCTUARY_GIBBORIM,           setOf(GIBBORIM, CHITAURI_SANCTUARY),          4, RouteType.ANY, -1))
        routesByRouteId.put(CHITAURISANCTUARY_VORMIR,           Route(CHITAURISANCTUARY_VORMIR,             setOf(CHITAURI_SANCTUARY, VORMIR),            6, RouteType.SOUL, -1))
        routesByRouteId.put(CHITAURISANCTUARY_ZENWHOBERI,       Route(CHITAURISANCTUARY_ZENWHOBERI,         setOf(CHITAURI_SANCTUARY, ZEN_WHOBERI),       4, RouteType.ANY, -1))
        routesByRouteId.put(GIBBORIM_ZENWHOBERI,                Route(GIBBORIM_ZENWHOBERI,                  setOf(GIBBORIM, ZEN_WHOBERI),                 6, RouteType.MIND, -1))
        routesByRouteId.put(EGO_GALACTUS,                       Route(EGO_GALACTUS,                         setOf(GALACTUS, EGO),                         6, RouteType.SPACE, -1))
        routesByRouteId.put(EGO_ZENWHOBERI,                     Route(EGO_ZENWHOBERI,                       setOf(EGO, ZEN_WHOBERI),                      3, RouteType.REALITY, -1))
        routesByRouteId.put(GALACTUS_ZENNLA_1,                  Route(GALACTUS_ZENNLA_1,                    setOf(GALACTUS, ZENN_LA),                     5, RouteType.PALLADIUM, -1))
        routesByRouteId.put(GALACTUS_ZENNLA_2,                  Route(GALACTUS_ZENNLA_2,                    setOf(GALACTUS, ZENN_LA),                     5, RouteType.REALITY, -1))
        routesByRouteId.put(EGO_ZENNLA_1,                       Route(EGO_ZENNLA_1,                         setOf(ZENN_LA, EGO),                          5, RouteType.POWER, -1))
        routesByRouteId.put(EGO_ZENNLA_2,                       Route(EGO_ZENNLA_2,                         setOf(ZENN_LA, EGO),                          5, RouteType.SOUL, -1))
        routesByRouteId.put(KNOWHERE_ZENNLA_1,                  Route(KNOWHERE_ZENNLA_1,                    setOf(ZENN_LA, KNOWHERE),                     3, RouteType.REALITY, -1))
        routesByRouteId.put(KNOWHERE_ZENNLA_2,                  Route(KNOWHERE_ZENNLA_2,                    setOf(ZENN_LA, KNOWHERE),                     3, RouteType.MIND, -1))
        routesByRouteId.put(KITSON_KNOWHERE,                    Route(KITSON_KNOWHERE,                      setOf(KNOWHERE, KITSON),                      2, RouteType.ANY, -1))
        routesByRouteId.put(EGO_KITSON,                         Route(EGO_KITSON,                           setOf(KITSON, EGO),                           3, RouteType.POWER, -1))
        routesByRouteId.put(CAVEOFTHEDRAGON_KNOWHERE,           Route(CAVEOFTHEDRAGON_KNOWHERE,             setOf(KNOWHERE, CAVE_OF_THE_DRAGON),          3, RouteType.ANY, -1))
        routesByRouteId.put(KNOWHERE_KUNLUN,                    Route(KNOWHERE_KUNLUN,                      setOf(KNOWHERE, KUN_LUN),                     6, RouteType.TIME, -1))
        routesByRouteId.put(CAVEOFTHEDRAGON_TITAN,              Route(CAVEOFTHEDRAGON_TITAN,                setOf(CAVE_OF_THE_DRAGON, TITAN),             5, RouteType.SOUL, -1))
        routesByRouteId.put(CAVEOFTHEDRAGON_VANAHEIM,           Route(CAVEOFTHEDRAGON_VANAHEIM,             setOf(CAVE_OF_THE_DRAGON, VANAHEIM),          3, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFTHEDRAGON_KUNLUN,             Route(CAVEOFTHEDRAGON_KUNLUN,               setOf(CAVE_OF_THE_DRAGON, KUN_LUN),           3, RouteType.ANY, -1))
        routesByRouteId.put(KUNLUN_VANAHEIM,                    Route(KUNLUN_VANAHEIM,                      setOf(VANAHEIM, KUN_LUN),                     2, RouteType.ANY, -1))
        routesByRouteId.put(EGO_TITAN_1,                        Route(EGO_TITAN_1,                          setOf(EGO, TITAN),                            3, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(EGO_TITAN_2,                        Route(EGO_TITAN_2,                          setOf(EGO, TITAN),                            3, RouteType.MIND, -1))
        routesByRouteId.put(TITAN_ZENWHOBERI,                   Route(TITAN_ZENWHOBERI,                     setOf(ZEN_WHOBERI, TITAN),                    4, RouteType.PALLADIUM, -1))
        routesByRouteId.put(SAKAAR_ZENWHOBERI,                  Route(SAKAAR_ZENWHOBERI,                    setOf(ZEN_WHOBERI, SAKAAR),                   6, RouteType.POWER, -1))
        routesByRouteId.put(VORMIR_ZENWHOBERI,                  Route(VORMIR_ZENWHOBERI,                    setOf(ZEN_WHOBERI, VORMIR),                   4, RouteType.SPACE, -1))
        routesByRouteId.put(XANDAR_ZENWHOBERI,                  Route(XANDAR_ZENWHOBERI,                    setOf(ZEN_WHOBERI, XANDAR),                   5, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(TITAN_VANAHEIM,                     Route(TITAN_VANAHEIM,                       setOf(TITAN, VANAHEIM),                       2, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFAGES_TITAN_1,                 Route(CAVEOFAGES_TITAN_1,                   setOf(TITAN, CAVE_OF_AGES),                   4, RouteType.TIME, -1))
        routesByRouteId.put(CAVEOFAGES_TITAN_2,                 Route(CAVEOFAGES_TITAN_2,                   setOf(TITAN, CAVE_OF_AGES),                   4, RouteType.POWER, -1))
        routesByRouteId.put(TITAN_XANDAR,                       Route(TITAN_XANDAR,                         setOf(TITAN, XANDAR),                         4, RouteType.REALITY, -1))
        routesByRouteId.put(SURTURSLAIR_TITAN,                  Route(SURTURSLAIR_TITAN,                    setOf(TITAN, SURTURS_LAIR),                   4, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(SURTURSLAIR_VANAHEIM,               Route(SURTURSLAIR_VANAHEIM,                 setOf(VANAHEIM, SURTURS_LAIR),                3, RouteType.SPACE, -1))
        routesByRouteId.put(KUNLUN_SURTURSLAIR,                 Route(KUNLUN_SURTURSLAIR,                   setOf(KUN_LUN, SURTURS_LAIR),                 5, RouteType.MIND, -1))
        routesByRouteId.put(HALA_KUNLUN,                        Route(HALA_KUNLUN,                          setOf(KUN_LUN, HALA),                         4, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(KUNLUN_SVARTLHEIM,                  Route(KUNLUN_SVARTLHEIM,                    setOf(KUN_LUN, SVARTLHEIM),                   6, RouteType.PALLADIUM, -1))
        routesByRouteId.put(HONGKONGSANCTUM_VORMIR,             Route(HONGKONGSANCTUM_VORMIR,               setOf(VORMIR, HONG_KONG_SANCTUM),             6, RouteType.ANY, -1))
        routesByRouteId.put(SAKAAR_VORMIR,                      Route(SAKAAR_VORMIR,                        setOf(VORMIR, SAKAAR),                        4, RouteType.TIME, -1))
        routesByRouteId.put(HONGKONGSANCTUM_SAKAAR,             Route(HONGKONGSANCTUM_SAKAAR,               setOf(SAKAAR, HONG_KONG_SANCTUM),             3, RouteType.ANY, -1))
        routesByRouteId.put(PYMLABS_SAKAAR,                     Route(PYMLABS_SAKAAR,                       setOf(SAKAAR, PYM_LABS),                      6, RouteType.REALITY, -1))
        routesByRouteId.put(NIDAVELLIR_SAKAAR,                  Route(NIDAVELLIR_SAKAAR,                    setOf(SAKAAR, NIDAVELLIR),                    3, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(SAKAAR_XANDAR_1,                    Route(SAKAAR_XANDAR_1,                      setOf(SAKAAR, XANDAR),                        2, RouteType.ANY, -1))
        routesByRouteId.put(SAKAAR_XANDAR_2,                    Route(SAKAAR_XANDAR_2,                      setOf(SAKAAR, XANDAR),                        2, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFAGES_XANDAR_1,                Route(CAVEOFAGES_XANDAR_1,                  setOf(XANDAR, CAVE_OF_AGES),                  1, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFAGES_XANDAR_2,                Route(CAVEOFAGES_XANDAR_2,                  setOf(XANDAR, CAVE_OF_AGES),                  1, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFAGES_SURTURSLAIR_1,           Route(CAVEOFAGES_SURTURSLAIR_1,             setOf(CAVE_OF_AGES, SURTURS_LAIR),            2, RouteType.ANY, -1))
        routesByRouteId.put(CAVEOFAGES_SURTURSLAIR_2,           Route(CAVEOFAGES_SURTURSLAIR_2,             setOf(CAVE_OF_AGES, SURTURS_LAIR),            2, RouteType.ANY, -1))
        routesByRouteId.put(HALA_SURTURSLAIR_1,                 Route(HALA_SURTURSLAIR_1,                   setOf(SURTURS_LAIR, HALA),                    2, RouteType.ANY, -1))
        routesByRouteId.put(HALA_SURTURSLAIR_2,                 Route(HALA_SURTURSLAIR_2,                   setOf(SURTURS_LAIR, HALA),                    2, RouteType.ANY, -1))
        routesByRouteId.put(HALA_SVARTLHEIM_1,                  Route(HALA_SVARTLHEIM_1,                    setOf(HALA, SVARTLHEIM),                      1, RouteType.ANY, -1))
        routesByRouteId.put(HALA_SVARTLHEIM_2,                  Route(HALA_SVARTLHEIM_2,                    setOf(HALA, SVARTLHEIM),                      1, RouteType.ANY, -1))
        routesByRouteId.put(NIFLHEIM_SVARTLHEIM,                Route(NIFLHEIM_SVARTLHEIM,                  setOf(SVARTLHEIM, NIFLHEIM),                  2, RouteType.ANY, -1))
        routesByRouteId.put(CONTRAXIA_SURTURSLAIR,              Route(CONTRAXIA_SURTURSLAIR,                setOf(SURTURS_LAIR, CONTRAXIA),               2, RouteType.ANY, -1))
        routesByRouteId.put(CONTRAXIA_HALA,                     Route(CONTRAXIA_HALA,                       setOf(HALA, CONTRAXIA),                       2, RouteType.ANY, -1))
        routesByRouteId.put(CONTRAXIA_NIFLHEIM,                 Route(CONTRAXIA_NIFLHEIM,                   setOf(CONTRAXIA, NIFLHEIM),                   3, RouteType.PALLADIUM, -1))
        routesByRouteId.put(ASGARD_CONTRAXIA,                   Route(ASGARD_CONTRAXIA,                     setOf(CONTRAXIA, ASGARD),                     2, RouteType.ANY, -1))
        routesByRouteId.put(CONTRAXIA_QUANTUMREALM,             Route(CONTRAXIA_QUANTUMREALM,               setOf(CONTRAXIA, QUANTUM_REALM),              3, RouteType.SOUL, -1))
        routesByRouteId.put(ASGARD_CAVEOFAGES_1,                Route(ASGARD_CAVEOFAGES_1,                  setOf(CAVE_OF_AGES, ASGARD),                  2, RouteType.REALITY, -1))
        routesByRouteId.put(ASGARD_CAVEOFAGES_2,                Route(ASGARD_CAVEOFAGES_2,                  setOf(CAVE_OF_AGES, ASGARD),                  2, RouteType.SPACE, -1))
        routesByRouteId.put(ASGARD_QUANTUMREALM,                Route(ASGARD_QUANTUMREALM,                  setOf(ASGARD, QUANTUM_REALM),                 2, RouteType.ANY, -1))
        routesByRouteId.put(ASGARD_NEWYORKCITY,                 Route(ASGARD_NEWYORKCITY,                   setOf(ASGARD, NEW_YORK_CITY),                 5, RouteType.PALLADIUM, -1))
        routesByRouteId.put(ASGARD_NIDAVELLIR_1,                Route(ASGARD_NIDAVELLIR_1,                  setOf(ASGARD, NIDAVELLIR),                    2, RouteType.PALLADIUM, -1))
        routesByRouteId.put(ASGARD_NIDAVELLIR_2,                Route(ASGARD_NIDAVELLIR_2,                  setOf(ASGARD, NIDAVELLIR),                    2, RouteType.SOUL, -1))
        routesByRouteId.put(NIDAVELLIR_XANDAR,                  Route(NIDAVELLIR_XANDAR,                    setOf(XANDAR, NIDAVELLIR),                    4, RouteType.SPACE, -1))
        routesByRouteId.put(NEWYORKCITY_NIDAVELLIR_1,           Route(NEWYORKCITY_NIDAVELLIR_1,             setOf(NIDAVELLIR, NEW_YORK_CITY),             3, RouteType.POWER, -1))
        routesByRouteId.put(NEWYORKCITY_NIDAVELLIR_2,           Route(NEWYORKCITY_NIDAVELLIR_2,             setOf(NIDAVELLIR, NEW_YORK_CITY),             3, RouteType.TIME, -1))
        routesByRouteId.put(NIDAVELLIR_PYMLABS,                 Route(NIDAVELLIR_PYMLABS,                   setOf(NIDAVELLIR, PYM_LABS),                  4, RouteType.SOUL, -1))
        routesByRouteId.put(HONGKONGSANCTUM_PYMLABS,            Route(HONGKONGSANCTUM_PYMLABS,              setOf(HONG_KONG_SANCTUM, PYM_LABS),           2, RouteType.ANY, -1))
        routesByRouteId.put(HONGKONGSANCTUM_WAKANDA,            Route(HONGKONGSANCTUM_WAKANDA,              setOf(HONG_KONG_SANCTUM, WAKANDA),            5, RouteType.TIME, -1))
        routesByRouteId.put(MUSPELHEIM_NIFLHEIM,                Route(MUSPELHEIM_NIFLHEIM,                  setOf(NIFLHEIM, MUSPELHEIM),                  6, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(NIFLHEIM_YOTUNHEIM_1,               Route(NIFLHEIM_YOTUNHEIM_1,                 setOf(NIFLHEIM, YOTUNHEIM),                   4, RouteType.MIND, -1))
        routesByRouteId.put(NIFLHEIM_YOTUNHEIM_2,               Route(NIFLHEIM_YOTUNHEIM_2,                 setOf(NIFLHEIM, YOTUNHEIM),                   4, RouteType.POWER, -1))
        routesByRouteId.put(MUSPELHEIM_YOTUNHEIM,               Route(MUSPELHEIM_YOTUNHEIM,                 setOf(YOTUNHEIM, MUSPELHEIM),                 5, RouteType.SPACE, -1))
        routesByRouteId.put(QUANTUMREALM_YOTUNHEIM,             Route(QUANTUMREALM_YOTUNHEIM,               setOf(YOTUNHEIM, QUANTUM_REALM),              1, RouteType.ANY, -1))
        routesByRouteId.put(KAMARTAJ_YOTUNHEIM,                 Route(KAMARTAJ_YOTUNHEIM,                   setOf(YOTUNHEIM, KAMAR_TAJ),                  2, RouteType.ANY, -1))
        routesByRouteId.put(KAMARTAJ_MUSPELHEIM,                Route(KAMARTAJ_MUSPELHEIM,                  setOf(KAMAR_TAJ, MUSPELHEIM),                 4, RouteType.REALITY, -1))
        routesByRouteId.put(HELICARRIER_KAMARTAJ,               Route(HELICARRIER_KAMARTAJ,                 setOf(KAMAR_TAJ, HELICARRIER),                2, RouteType.ANY, -1))
        routesByRouteId.put(HELICARRIER_YOTUNHEIM_1,            Route(HELICARRIER_YOTUNHEIM_1,              setOf(YOTUNHEIM, HELICARRIER),                2, RouteType.ANY, -1))
        routesByRouteId.put(HELICARRIER_YOTUNHEIM_2,            Route(HELICARRIER_YOTUNHEIM_2,              setOf(YOTUNHEIM, HELICARRIER),                2, RouteType.ANY, -1))
        routesByRouteId.put(HELICARRIER_QUANTUMREALM,           Route(HELICARRIER_QUANTUMREALM,             setOf(QUANTUM_REALM, HELICARRIER),            3, RouteType.TIME, -1))
        routesByRouteId.put(NEWYORKCITY_QUANTUMREALM,           Route(NEWYORKCITY_QUANTUMREALM,             setOf(QUANTUM_REALM, NEW_YORK_CITY),          4, RouteType.MIND, -1))
        routesByRouteId.put(HELICARRIER_TRISKELION_1,           Route(HELICARRIER_TRISKELION_1,             setOf(HELICARRIER, TRISKELION),               2, RouteType.ANY, -1))
        routesByRouteId.put(HELICARRIER_TRISKELION_2,           Route(HELICARRIER_TRISKELION_2,             setOf(HELICARRIER, TRISKELION),               2, RouteType.ANY, -1))
        routesByRouteId.put(HELICARRIER_NEWYORKCITY,            Route(HELICARRIER_NEWYORKCITY,              setOf(HELICARRIER, NEW_YORK_CITY),            2, RouteType.ANY, -1))
        routesByRouteId.put(NEWYORKCITY_TRISKELION,             Route(NEWYORKCITY_TRISKELION,               setOf(NEW_YORK_CITY, TRISKELION),             2, RouteType.ANY, -1))
        routesByRouteId.put(AVENGERSHQ_TRISKELION_1,            Route(AVENGERSHQ_TRISKELION_1,              setOf(TRISKELION, AVENGERS_HQ),               2, RouteType.POWER, -1))
        routesByRouteId.put(AVENGERSHQ_TRISKELION_2,            Route(AVENGERSHQ_TRISKELION_2,              setOf(TRISKELION, AVENGERS_HQ),               2, RouteType.TIME, -1))
        routesByRouteId.put(AVENGERSHQ_NEWYORKCITY_1,           Route(AVENGERSHQ_NEWYORKCITY_1,             setOf(NEW_YORK_CITY, AVENGERS_HQ),            2, RouteType.SOUL, -1))
        routesByRouteId.put(AVENGERSHQ_NEWYORKCITY_2,           Route(AVENGERSHQ_NEWYORKCITY_2,             setOf(NEW_YORK_CITY, AVENGERS_HQ),            2, RouteType.PALLADIUM, -1))
        routesByRouteId.put(NEWYORKCITY_PYMLABS,                Route(NEWYORKCITY_PYMLABS,                  setOf(NEW_YORK_CITY, PYM_LABS),               2, RouteType.ANY, -1))
        routesByRouteId.put(PYMLABS_WAKANDA,                    Route(PYMLABS_WAKANDA,                      setOf(PYM_LABS, WAKANDA),                     3, RouteType.ANY, -1))
        routesByRouteId.put(AVENGERSHQ_WAKANDA,                 Route(AVENGERSHQ_WAKANDA,                   setOf(WAKANDA, AVENGERS_HQ),                  3, RouteType.SPACE, -1))
        routesByRouteId.put(AVENGERSHQ_SOKOVIA_1,               Route(AVENGERSHQ_SOKOVIA_1,                 setOf(AVENGERS_HQ, SOKOVIA),                  2, RouteType.MIND, -1))
        routesByRouteId.put(AVENGERSHQ_SOKOVIA_2,               Route(AVENGERSHQ_SOKOVIA_2,                 setOf(AVENGERS_HQ, SOKOVIA),                  2, RouteType.VIBRANIUM, -1))
        routesByRouteId.put(SOKOVIA_WAKANDA_1,                  Route(SOKOVIA_WAKANDA_1,                    setOf(SOKOVIA, WAKANDA),                      2, RouteType.ANY, -1))
        routesByRouteId.put(SOKOVIA_WAKANDA_2,                  Route(SOKOVIA_WAKANDA_2,                    setOf(SOKOVIA, WAKANDA),                      2, RouteType.ANY, -1))
    }

    fun pathBetweenCities(city1: String, city2: String, userId: Int): Boolean {
        try {
            return routesByRouteId
                    .filter { entry -> entry.value.cities.contains(city1) }
                    .map { entry ->
                        pathBetweenCities(city1,
                                entry.value.cities.filter { c -> c != city1 }[0],
                                userId,
                                mapOf<Route, Boolean>(entry.value to true))
                    }
                    .reduceRight { a, b -> a || b }
        }
        catch(e: Exception) {
            return false
        }
    }

    fun pathBetweenCities(city1: String, city2: String, userId: Int, visited: Map<Route, Boolean>): Boolean {
        return routesByRouteId
                .filter { entry -> entry.value.cities.contains(city1) && !(visited[entry.value]!!) }
                .map { entry -> pathBetweenCities(city1,
                        entry.value.cities.filter { c -> c != city1 }[0],
                        userId,
                        visited.plus(entry.value to true)) }
                .reduceRight {a, b -> a || b}
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

class Route(val routeId: String, val cities: Set<String>, val numCars: Int, val type: RouteType, var ownerId: Int) {
    val points: Int
        get() {
            return when(numCars) {
                1 -> 1
                2 -> 2
                3 -> 4
                4 -> 7
                5 -> 10
                6 -> 15
                else -> throw CommandException("Invalid number of cars for route.")
            }
        }
}