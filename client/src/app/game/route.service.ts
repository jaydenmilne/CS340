import { Injectable } from '@angular/core';
import { Route } from '../core/model/route';
import { RouteName } from "../core/model/route-name.enum";
import { City } from "../core/model/city.enum";
import { RouteType } from "../core/model/route-type.enum";
import { Subject, from } from 'rxjs';
import { ErrorNotifierService } from '@core/error-notifier.service';
import { CommandRouterService } from '@core/command-router.service';
import { RouteClaimedCommand, ClaimRouteCommand } from '@core/game-commands';

import { ShardCardDeck, ShardCard } from '@core/model/cards';
import { TurnService } from '../game/turn/turn.service';
import { ServerProxyService } from '@core/server/server-proxy.service';

@Injectable({
  providedIn: 'root'
})
export class RouteService {

  public routes: Map<RouteName, Route> = new Map();
  public routeOwnershipChanged$ = new Subject<Route>();

  constructor(
    private errorNotifier : ErrorNotifierService, 
    private commandRouter : CommandRouterService,
    private turnService : TurnService,
    private server: ServerProxyService) {

    this.routes.set(RouteName.DARKDIMENSION_GIBBORIM_1,             (new Route(RouteName.DARKDIMENSION_GIBBORIM_1,             [City.DARK_DIMENSION, City.GIBBORIM],              1, RouteType.ANY, -1)));
    this.routes.set(RouteName.DARKDIMENSION_GIBBORIM_2,             (new Route(RouteName.DARKDIMENSION_GIBBORIM_2,             [City.DARK_DIMENSION, City.GIBBORIM],              1, RouteType.ANY, -1)));
    this.routes.set(RouteName.CHITAURISANCTUARY_DARKDIMENSION,      (new Route(RouteName.CHITAURISANCTUARY_DARKDIMENSION,      [City.DARK_DIMENSION, City.CHITAURI_SANCTUARY],    3, RouteType.ANY, -1)));
    this.routes.set(RouteName.GALACTUS_GIBBORIM_1,                  (new Route(RouteName.GALACTUS_GIBBORIM_1,                  [City.GIBBORIM, City.GALACTUS],                    1, RouteType.ANY, -1)));
    this.routes.set(RouteName.GALACTUS_GIBBORIM_2,                  (new Route(RouteName.GALACTUS_GIBBORIM_2,                  [City.GIBBORIM, City.GALACTUS],                    1, RouteType.ANY, -1)));
    this.routes.set(RouteName.CHITAURISANCTUARY_GIBBORIM,           (new Route(RouteName.CHITAURISANCTUARY_GIBBORIM,           [City.GIBBORIM, City.CHITAURI_SANCTUARY],          4, RouteType.ANY, -1)));
    this.routes.set(RouteName.CHITAURISANCTUARY_VORMIR,             (new Route(RouteName.CHITAURISANCTUARY_VORMIR,             [City.CHITAURI_SANCTUARY, City.VORMIR],            6, RouteType.SOUL, -1)));
    this.routes.set(RouteName.CHITAURISANCTUARY_ZENWHOBERI,         (new Route(RouteName.CHITAURISANCTUARY_ZENWHOBERI,         [City.CHITAURI_SANCTUARY, City.ZEN_WHOBERI],       4, RouteType.ANY, -1)));
    this.routes.set(RouteName.GIBBORIM_ZENWHOBERI,                  (new Route(RouteName.GIBBORIM_ZENWHOBERI,                  [City.GIBBORIM, City.ZEN_WHOBERI],                 6, RouteType.MIND, -1)));
    this.routes.set(RouteName.EGO_GALACTUS,                         (new Route(RouteName.EGO_GALACTUS,                         [City.GALACTUS, City.EGO],                         6, RouteType.SPACE, -1)));
    this.routes.set(RouteName.EGO_ZENWHOBERI,                       (new Route(RouteName.EGO_ZENWHOBERI,                       [City.EGO, City.ZEN_WHOBERI],                      3, RouteType.REALITY, -1)));
    this.routes.set(RouteName.GALACTUS_ZENNLA_1,                    (new Route(RouteName.GALACTUS_ZENNLA_1,                    [City.GALACTUS, City.ZENN_LA],                     5, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.GALACTUS_ZENNLA_2,                    (new Route(RouteName.GALACTUS_ZENNLA_2,                    [City.GALACTUS, City.ZENN_LA],                     5, RouteType.REALITY, -1)));
    this.routes.set(RouteName.EGO_ZENNLA_1,                         (new Route(RouteName.EGO_ZENNLA_1,                         [City.ZENN_LA, City.EGO],                          5, RouteType.POWER, -1)));
    this.routes.set(RouteName.EGO_ZENNLA_2,                         (new Route(RouteName.EGO_ZENNLA_2,                         [City.ZENN_LA, City.EGO],                          5, RouteType.SOUL, -1)));
    this.routes.set(RouteName.KNOWHERE_ZENNLA_1,                    (new Route(RouteName.KNOWHERE_ZENNLA_1,                    [City.ZENN_LA, City.KNOWHERE],                     3, RouteType.REALITY, -1)));
    this.routes.set(RouteName.KNOWHERE_ZENNLA_2,                    (new Route(RouteName.KNOWHERE_ZENNLA_2,                    [City.ZENN_LA, City.KNOWHERE],                     3, RouteType.MIND, -1)));
    this.routes.set(RouteName.KITSON_KNOWHERE,                      (new Route(RouteName.KITSON_KNOWHERE,                      [City.KNOWHERE, City.KITSON],                      2, RouteType.ANY, -1)));
    this.routes.set(RouteName.EGO_KITSON,                           (new Route(RouteName.EGO_KITSON,                           [City.KITSON, City.EGO],                           3, RouteType.POWER, -1)));
    this.routes.set(RouteName.CAVEOFTHEDRAGON_KNOWHERE,             (new Route(RouteName.CAVEOFTHEDRAGON_KNOWHERE,             [City.KNOWHERE, City.CAVE_OF_THE_DRAGON],          3, RouteType.ANY, -1)));
    this.routes.set(RouteName.KNOWHERE_KUNLUN,                      (new Route(RouteName.KNOWHERE_KUNLUN,                      [City.KNOWHERE, City.KUN_LUN],                     6, RouteType.TIME, -1)));
    this.routes.set(RouteName.CAVEOFTHEDRAGON_TITAN,                (new Route(RouteName.CAVEOFTHEDRAGON_TITAN,                [City.CAVE_OF_THE_DRAGON, City.TITAN],             5, RouteType.SOUL, -1)));
    this.routes.set(RouteName.CAVEOFTHEDRAGON_VANAHEIM,             (new Route(RouteName.CAVEOFTHEDRAGON_VANAHEIM,             [City.CAVE_OF_THE_DRAGON, City.VANAHEIM],          3, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFTHEDRAGON_KUNLUN,               (new Route(RouteName.CAVEOFTHEDRAGON_KUNLUN,               [City.CAVE_OF_THE_DRAGON, City.KUN_LUN],           3, RouteType.ANY, -1)));
    this.routes.set(RouteName.KUNLUN_VANAHEIM,                      (new Route(RouteName.KUNLUN_VANAHEIM,                      [City.VANAHEIM, City.KUN_LUN],                     2, RouteType.ANY, -1)));
    this.routes.set(RouteName.EGO_TITAN_1,                          (new Route(RouteName.EGO_TITAN_1,                          [City.EGO, City.TITAN],                            3, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.EGO_TITAN_2,                          (new Route(RouteName.EGO_TITAN_2,                          [City.EGO, City.TITAN],                            3, RouteType.MIND, -1)));
    this.routes.set(RouteName.TITAN_ZENWHOBERI,                     (new Route(RouteName.TITAN_ZENWHOBERI,                     [City.ZEN_WHOBERI, City.TITAN],                    4, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.SAKAAR_ZENWHOBERI,                    (new Route(RouteName.SAKAAR_ZENWHOBERI,                    [City.ZEN_WHOBERI, City.SAKAAR],                   6, RouteType.POWER, -1)));
    this.routes.set(RouteName.VORMIR_ZENWHOBERI,                    (new Route(RouteName.VORMIR_ZENWHOBERI,                    [City.ZEN_WHOBERI, City.VORMIR],                   4, RouteType.SPACE, -1)));
    this.routes.set(RouteName.XANDAR_ZENWHOBERI,                    (new Route(RouteName.XANDAR_ZENWHOBERI,                    [City.ZEN_WHOBERI, City.XANDAR],                   5, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.TITAN_VANAHEIM,                       (new Route(RouteName.TITAN_VANAHEIM,                       [City.TITAN, City.VANAHEIM],                       2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFAGES_TITAN_1,                   (new Route(RouteName.CAVEOFAGES_TITAN_1,                   [City.TITAN, City.CAVE_OF_AGES],                   4, RouteType.TIME, -1)));
    this.routes.set(RouteName.CAVEOFAGES_TITAN_2,                   (new Route(RouteName.CAVEOFAGES_TITAN_2,                   [City.TITAN, City.CAVE_OF_AGES],                   4, RouteType.POWER, -1)));
    this.routes.set(RouteName.TITAN_XANDAR,                         (new Route(RouteName.TITAN_XANDAR,                         [City.TITAN, City.XANDAR],                         4, RouteType.REALITY, -1)));
    this.routes.set(RouteName.SURTURSLAIR_TITAN,                    (new Route(RouteName.SURTURSLAIR_TITAN,                    [City.TITAN, City.SURTURS_LAIR],                   4, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.SURTURSLAIR_VANAHEIM,                 (new Route(RouteName.SURTURSLAIR_VANAHEIM,                 [City.VANAHEIM, City.SURTURS_LAIR],                3, RouteType.SPACE, -1)));
    this.routes.set(RouteName.KUNLUN_SURTURSLAIR,                   (new Route(RouteName.KUNLUN_SURTURSLAIR,                   [City.KUN_LUN, City.SURTURS_LAIR],                 5, RouteType.MIND, -1)));
    this.routes.set(RouteName.HALA_KUNLUN,                          (new Route(RouteName.HALA_KUNLUN,                          [City.KUN_LUN, City.HALA],                         4, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.KUNLUN_SVARTLHEIM,                    (new Route(RouteName.KUNLUN_SVARTLHEIM,                    [City.KUN_LUN, City.SVARTLHEIM],                   6, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.HONGKONGSANCTUM_VORMIR,               (new Route(RouteName.HONGKONGSANCTUM_VORMIR,               [City.VORMIR, City.HONG_KONG_SANCTUM],             6, RouteType.ANY, -1)));
    this.routes.set(RouteName.SAKAAR_VORMIR,                        (new Route(RouteName.SAKAAR_VORMIR,                        [City.VORMIR, City.SAKAAR],                        4, RouteType.TIME, -1)));
    this.routes.set(RouteName.HONGKONGSANCTUM_SAKAAR,               (new Route(RouteName.HONGKONGSANCTUM_SAKAAR,               [City.SAKAAR, City.HONG_KONG_SANCTUM],             3, RouteType.ANY, -1)));
    this.routes.set(RouteName.PYMLABS_SAKAAR,                       (new Route(RouteName.PYMLABS_SAKAAR,                       [City.SAKAAR, City.PYM_LABS],                      6, RouteType.REALITY, -1)));
    this.routes.set(RouteName.NIDAVELLIR_SAKAAR,                    (new Route(RouteName.NIDAVELLIR_SAKAAR,                    [City.SAKAAR, City.NIDAVELLIR],                    3, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.SAKAAR_XANDAR_1,                      (new Route(RouteName.SAKAAR_XANDAR_1,                      [City.SAKAAR, City.XANDAR],                        2, RouteType.ANY, -1)));
    this.routes.set(RouteName.SAKAAR_XANDAR_2,                      (new Route(RouteName.SAKAAR_XANDAR_2,                      [City.SAKAAR, City.XANDAR],                        2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFAGES_XANDAR_1,                  (new Route(RouteName.CAVEOFAGES_XANDAR_1,                  [City.XANDAR, City.CAVE_OF_AGES],                  1, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFAGES_XANDAR_2,                  (new Route(RouteName.CAVEOFAGES_XANDAR_2,                  [City.XANDAR, City.CAVE_OF_AGES],                  1, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFAGES_SURTURSLAIR_1,             (new Route(RouteName.CAVEOFAGES_SURTURSLAIR_1,             [City.CAVE_OF_AGES, City.SURTURS_LAIR],            2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CAVEOFAGES_SURTURSLAIR_2,             (new Route(RouteName.CAVEOFAGES_SURTURSLAIR_2,             [City.CAVE_OF_AGES, City.SURTURS_LAIR],            2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HALA_SURTURSLAIR_1,                   (new Route(RouteName.HALA_SURTURSLAIR_1,                   [City.SURTURS_LAIR, City.HALA],                    2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HALA_SURTURSLAIR_2,                   (new Route(RouteName.HALA_SURTURSLAIR_2,                   [City.SURTURS_LAIR, City.HALA],                    2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HALA_SVARTLHEIM_1,                    (new Route(RouteName.HALA_SVARTLHEIM_1,                    [City.HALA, City.SVARTLHEIM],                      1, RouteType.ANY, -1)));
    this.routes.set(RouteName.HALA_SVARTLHEIM_2,                    (new Route(RouteName.HALA_SVARTLHEIM_2,                    [City.HALA, City.SVARTLHEIM],                      1, RouteType.ANY, -1)));
    this.routes.set(RouteName.NIFLHEIM_SVARTLHEIM,                  (new Route(RouteName.NIFLHEIM_SVARTLHEIM,                  [City.SVARTLHEIM, City.NIFLHEIM],                  2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CONTRAXIA_SURTURSLAIR,                (new Route(RouteName.CONTRAXIA_SURTURSLAIR,                [City.SURTURS_LAIR, City.CONTRAXIA],               2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CONTRAXIA_HALA,                       (new Route(RouteName.CONTRAXIA_HALA,                       [City.HALA, City.CONTRAXIA],                       2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CONTRAXIA_NIFLHEIM,                   (new Route(RouteName.CONTRAXIA_NIFLHEIM,                   [City.CONTRAXIA, City.NIFLHEIM],                   3, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.ASGARD_CONTRAXIA,                     (new Route(RouteName.ASGARD_CONTRAXIA,                     [City.CONTRAXIA, City.ASGARD],                     2, RouteType.ANY, -1)));
    this.routes.set(RouteName.CONTRAXIA_QUANTUMREALM,               (new Route(RouteName.CONTRAXIA_QUANTUMREALM,               [City.CONTRAXIA, City.QUANTUM_REALM],              3, RouteType.SOUL, -1)));
    this.routes.set(RouteName.ASGARD_CAVEOFAGES_1,                  (new Route(RouteName.ASGARD_CAVEOFAGES_1,                  [City.CAVE_OF_AGES, City.ASGARD],                  2, RouteType.REALITY, -1)));
    this.routes.set(RouteName.ASGARD_CAVEOFAGES_2,                  (new Route(RouteName.ASGARD_CAVEOFAGES_2,                  [City.CAVE_OF_AGES, City.ASGARD],                  2, RouteType.SPACE, -1)));
    this.routes.set(RouteName.ASGARD_QUANTUMREALM,                  (new Route(RouteName.ASGARD_QUANTUMREALM,                  [City.ASGARD, City.QUANTUM_REALM],                 2, RouteType.ANY, -1)));
    this.routes.set(RouteName.ASGARD_NEWYORKCITY,                   (new Route(RouteName.ASGARD_NEWYORKCITY,                   [City.ASGARD, City.NEW_YORK_CITY],                 5, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.ASGARD_NIDAVELLIR_1,                  (new Route(RouteName.ASGARD_NIDAVELLIR_1,                  [City.ASGARD, City.NIDAVELLIR],                    2, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.ASGARD_NIDAVELLIR_2,                  (new Route(RouteName.ASGARD_NIDAVELLIR_2,                  [City.ASGARD, City.NIDAVELLIR],                    2, RouteType.SOUL, -1)));
    this.routes.set(RouteName.NIDAVELLIR_XANDAR,                    (new Route(RouteName.NIDAVELLIR_XANDAR,                    [City.XANDAR, City.NIDAVELLIR],                    4, RouteType.SPACE, -1)));
    this.routes.set(RouteName.NEWYORKCITY_NIDAVELLIR_1,             (new Route(RouteName.NEWYORKCITY_NIDAVELLIR_1,             [City.NIDAVELLIR, City.NEW_YORK_CITY],             3, RouteType.POWER, -1)));
    this.routes.set(RouteName.NEWYORKCITY_NIDAVELLIR_2,             (new Route(RouteName.NEWYORKCITY_NIDAVELLIR_2,             [City.NIDAVELLIR, City.NEW_YORK_CITY],             3, RouteType.TIME, -1)));
    this.routes.set(RouteName.NIDAVELLIR_PYMLABS,                   (new Route(RouteName.NIDAVELLIR_PYMLABS,                   [City.NIDAVELLIR, City.PYM_LABS],                  4, RouteType.SOUL, -1)));
    this.routes.set(RouteName.HONGKONGSANCTUM_PYMLABS,              (new Route(RouteName.HONGKONGSANCTUM_PYMLABS,              [City.HONG_KONG_SANCTUM, City.PYM_LABS],           2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HONGKONGSANCTUM_WAKANDA,              (new Route(RouteName.HONGKONGSANCTUM_WAKANDA,              [City.HONG_KONG_SANCTUM, City.WAKANDA],            5, RouteType.TIME, -1)));
    this.routes.set(RouteName.MUSPELHEIM_NIFLHEIM,                  (new Route(RouteName.MUSPELHEIM_NIFLHEIM,                  [City.NIFLHEIM, City.MUSPELHEIM],                  6, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.NIFLHEIM_YOTUNHEIM_1,                 (new Route(RouteName.NIFLHEIM_YOTUNHEIM_1,                 [City.NIFLHEIM, City.YOTUNHEIM],                   4, RouteType.MIND, -1)));
    this.routes.set(RouteName.NIFLHEIM_YOTUNHEIM_2,                 (new Route(RouteName.NIFLHEIM_YOTUNHEIM_2,                 [City.NIFLHEIM, City.YOTUNHEIM],                   4, RouteType.POWER, -1)));
    this.routes.set(RouteName.MUSPELHEIM_YOTUNHEIM,                 (new Route(RouteName.MUSPELHEIM_YOTUNHEIM,                 [City.YOTUNHEIM, City.MUSPELHEIM],                 5, RouteType.SPACE, -1)));
    this.routes.set(RouteName.QUANTUMREALM_YOTUNHEIM,               (new Route(RouteName.QUANTUMREALM_YOTUNHEIM,               [City.YOTUNHEIM, City.QUANTUM_REALM],              1, RouteType.ANY, -1)));
    this.routes.set(RouteName.KAMARTAJ_YOTUNHEIM,                   (new Route(RouteName.KAMARTAJ_YOTUNHEIM,                   [City.YOTUNHEIM, City.KAMAR_TAJ],                  2, RouteType.ANY, -1)));
    this.routes.set(RouteName.KAMARTAJ_MUSPELHEIM,                  (new Route(RouteName.KAMARTAJ_MUSPELHEIM,                  [City.KAMAR_TAJ, City.MUSPELHEIM],                 4, RouteType.REALITY, -1)));
    this.routes.set(RouteName.HELICARRIER_KAMARTAJ,                 (new Route(RouteName.HELICARRIER_KAMARTAJ,                 [City.KAMAR_TAJ, City.HELICARRIER],                2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HELICARRIER_YOTUNHEIM_1,              (new Route(RouteName.HELICARRIER_YOTUNHEIM_1,              [City.YOTUNHEIM, City.HELICARRIER],                2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HELICARRIER_YOTUNHEIM_2,              (new Route(RouteName.HELICARRIER_YOTUNHEIM_2,              [City.YOTUNHEIM, City.HELICARRIER],                2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HELICARRIER_QUANTUMREALM,             (new Route(RouteName.HELICARRIER_QUANTUMREALM,             [City.QUANTUM_REALM, City.HELICARRIER],            3, RouteType.TIME, -1)));
    this.routes.set(RouteName.NEWYORKCITY_QUANTUMREALM,             (new Route(RouteName.NEWYORKCITY_QUANTUMREALM,             [City.QUANTUM_REALM, City.NEW_YORK_CITY],          4, RouteType.MIND, -1)));
    this.routes.set(RouteName.HELICARRIER_TRISKELION_1,             (new Route(RouteName.HELICARRIER_TRISKELION_1,             [City.HELICARRIER, City.TRISKELION],               2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HELICARRIER_TRISKELION_2,             (new Route(RouteName.HELICARRIER_TRISKELION_2,             [City.HELICARRIER, City.TRISKELION],               2, RouteType.ANY, -1)));
    this.routes.set(RouteName.HELICARRIER_NEWYORKCITY,              (new Route(RouteName.HELICARRIER_NEWYORKCITY,              [City.HELICARRIER, City.NEW_YORK_CITY],            2, RouteType.ANY, -1)));
    this.routes.set(RouteName.NEWYORKCITY_TRISKELION,               (new Route(RouteName.NEWYORKCITY_TRISKELION,               [City.NEW_YORK_CITY, City.TRISKELION],             2, RouteType.ANY, -1)));
    this.routes.set(RouteName.AVENGERSHQ_TRISKELION_1,              (new Route(RouteName.AVENGERSHQ_TRISKELION_1,              [City.TRISKELION, City.AVENGERS_HQ],               2, RouteType.POWER, -1)));
    this.routes.set(RouteName.AVENGERSHQ_TRISKELION_2,              (new Route(RouteName.AVENGERSHQ_TRISKELION_2,              [City.TRISKELION, City.AVENGERS_HQ],               2, RouteType.TIME, -1)));
    this.routes.set(RouteName.AVENGERSHQ_NEWYORKCITY_1,             (new Route(RouteName.AVENGERSHQ_NEWYORKCITY_1,             [City.NEW_YORK_CITY, City.AVENGERS_HQ],            2, RouteType.SOUL, -1)));
    this.routes.set(RouteName.AVENGERSHQ_NEWYORKCITY_2,             (new Route(RouteName.AVENGERSHQ_NEWYORKCITY_2,             [City.NEW_YORK_CITY, City.AVENGERS_HQ],            2, RouteType.PALLADIUM, -1)));
    this.routes.set(RouteName.NEWYORKCITY_PYMLABS,                  (new Route(RouteName.NEWYORKCITY_PYMLABS,                  [City.NEW_YORK_CITY, City.PYM_LABS],               2, RouteType.ANY, -1)));
    this.routes.set(RouteName.PYMLABS_WAKANDA,                      (new Route(RouteName.PYMLABS_WAKANDA,                      [City.PYM_LABS, City.WAKANDA],                     3, RouteType.ANY, -1)));
    this.routes.set(RouteName.AVENGERSHQ_WAKANDA,                   (new Route(RouteName.AVENGERSHQ_WAKANDA,                   [City.WAKANDA, City.AVENGERS_HQ],                  3, RouteType.SPACE, -1)));
    this.routes.set(RouteName.AVENGERSHQ_SOKOVIA_1,                 (new Route(RouteName.AVENGERSHQ_SOKOVIA_1,                 [City.AVENGERS_HQ, City.SOKOVIA],                  2, RouteType.MIND, -1)));
    this.routes.set(RouteName.AVENGERSHQ_SOKOVIA_2,                 (new Route(RouteName.AVENGERSHQ_SOKOVIA_2,                 [City.AVENGERS_HQ, City.SOKOVIA],                  2, RouteType.VIBRANIUM, -1)));
    this.routes.set(RouteName.SOKOVIA_WAKANDA_1,                    (new Route(RouteName.SOKOVIA_WAKANDA_1,                    [City.SOKOVIA, City.WAKANDA],                      2, RouteType.ANY, -1)));
    this.routes.set(RouteName.SOKOVIA_WAKANDA_2,                    (new Route(RouteName.SOKOVIA_WAKANDA_2,                    [City.SOKOVIA, City.WAKANDA],                      2, RouteType.ANY, -1)));

    this.commandRouter.routeClaimed$.subscribe( cmd => this.onRouteClaimed(cmd));
  }

  public updateOwnership(routeName: RouteName, ownerId: number) {
    const route = this.routes.get(routeName);
    if (!route) {
      this.errorNotifier.notifyHeading('RouteService::updateOwnership', 'Cannot update ownership, route not found: ' + routeName);
      return;
    }
    route.ownerId = ownerId;
    this.routeOwnershipChanged$.next(route);
  }

  public onRouteClaimed(routeClaimedCommand : RouteClaimedCommand) {
    this.updateOwnership(routeClaimedCommand.routeId as RouteName, routeClaimedCommand.userId);
  }

  /* Look up route by ID*/
  public getRouteById(routeName: RouteName): Route {
    return this.routes.get(routeName);
  }

  /* Determine if the provided hand contains enough cards to claim the specified route.
    * @return true if route claim is possible, false if impossible
    * @param route Route we would like to claim
    * @param hand Users current hand
    * */
  public claimRoutePossible(route: Route, hand: ShardCardDeck): boolean {
    if (!this.turnService.canClaimRoutes()) {
      return false;
    }
    let numGoodCards: number;
    if (route.type === RouteType.ANY) {
      return this.canClaimAnyRouteType(route.numCars, hand);
    } else {
        return this.canClaimRouteType(route.type, hand, route.numCars);
    }
  }

  private canClaimAnyRouteType(cardsNeeded: number, hand: ShardCardDeck): boolean {
    const types = [RouteType.MIND, RouteType.PALLADIUM, RouteType.POWER, RouteType.REALITY, RouteType.SOUL, RouteType.SPACE, RouteType.TIME, RouteType.VIBRANIUM];
  for (const type of types) {
    if (this.canClaimRouteType(type, hand, cardsNeeded)) {
      return true;
    }
  }
  return false;
}

  private canClaimRouteType(type: RouteType, hand: ShardCardDeck, cardsNeeded: number): boolean {
    if (cardsNeeded <= hand.cards.filter(card => ShardCard.typeMap[card.type] === type || ShardCard.typeMap[card.type] === RouteType.ANY).length) {
      return true;
    }
    return false;
  }

    /* Determine if the supplied cards can be used to claim the specified route.
    * @return true if the cards are an exact match to claim the route, false if too many, two few, or the wrong cards are provided.
    * @param route  Route we would like to claim
    * @param cards  ShardCards we would like to use to claim the route
    * */
  public claimRouteValid(route: Route, cards: ShardCardDeck): boolean {
    if (this.claimRoutePossible(route, cards) && cards.size() === route.numCars) {
        return true;
    } else {
     return false;
    }
  }

    /* Claims the route using the specified cards by sending a ClaimRouteCommand to the server.
    * @param route  Route to claim
    * @param cards  ShardCards used to claim the route
    * */
   public claimRoute(route: Route, cards: ShardCardDeck) {
    this.server.executeCommand(new ClaimRouteCommand(route.routeName, cards.cards));
   }
}
