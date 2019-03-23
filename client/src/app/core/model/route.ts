import { MaterialType, DestinationCard } from './cards';

export enum RouteType {
  	ANY = 'any',
	REALITY = 'reality',
	SOUL = 'soul',
	SPACE = 'space',
	MIND = 'mind',
	POWER = 'power',
	TIME = 'time',
	VIBRANIUM = 'vibranium',
	PALLADIUM = 'palladium'
}

export enum RouteName {
	DARKDIMENSION_GIBBORIM_1 = 'darkdimension_gibborim_1',
    DARKDIMENSION_GIBBORIM_2 = 'darkdimension_gibborim_2',
    CHITAURISANCTUARY_DARKDIMENSION = 'chitaurisanctuary_darkdimension',
    GALACTUS_GIBBORIM_1 = 'galactus_gibborim_1',
    GALACTUS_GIBBORIM_2 = 'galactus_gibborim_2',
    CHITAURISANCTUARY_GIBBORIM = 'chitaurisanctuary_gibborim',
    CHITAURISANCTUARY_VORMIR = 'chitaurisanctuary_vormir',
    CHITAURISANCTUARY_ZENWHOBERI = 'chitaurisanctuary_zenwhoberi',
    GIBBORIM_ZENWHOBERI = 'gibborim_zenwhoberi',
    EGO_GALACTUS = 'ego_galactus',
    EGO_ZENWHOBERI = 'ego_zenwhoberi',
    GALACTUS_ZENNLA_1 = 'galactus_zennla_1',
    GALACTUS_ZENNLA_2 = 'galactus_zennla_2',
    EGO_ZENNLA_1 = 'ego_zennla_1',
    EGO_ZENNLA_2 = 'ego_zennla_2',
    KNOWHERE_ZENNLA_1 = 'knowhere_zennla_1',
    KNOWHERE_ZENNLA_2 = 'knowhere_zennla_2',
    KITSON_KNOWHERE = 'kitson_knowhere',
    EGO_KITSON = 'ego_kitson',
    CAVEOFTHEDRAGON_KNOWHERE = 'caveofthedragon_knowhere',
    KNOWHERE_KUNLUN = 'knowhere_kunlun',
    CAVEOFTHEDRAGON_TITAN = 'caveofthedragon_titan',
    CAVEOFTHEDRAGON_VANAHEIM = 'caveofthedragon_vanaheim',
    CAVEOFTHEDRAGON_KUNLUN = 'caveofthedragon_kunlun',
    KUNLUN_VANAHEIM = 'kunlun_vanaheim',
    EGO_TITAN_1 = 'ego_titan_1',
    EGO_TITAN_2 = 'ego_titan_2',
    TITAN_ZENWHOBERI = 'titan_zenwhoberi',
    SAKAAR_ZENWHOBERI = 'sakaar_zenwhoberi',
    VORMIR_ZENWHOBERI = 'vormir_zenwhoberi',
    XANDAR_ZENWHOBERI = 'xandar_zenwhoberi',
    TITAN_VANAHEIM = 'titan_vanaheim',
    CAVEOFAGES_TITAN_1 = 'caveofages_titan_1',
    CAVEOFAGES_TITAN_2 = 'caveofages_titan_2',
    TITAN_XANDAR = 'titan_xandar',
    SURTURSLAIR_TITAN = 'surturslair_titan',
    SURTURSLAIR_VANAHEIM = 'surturslair_vanaheim',
    KUNLUN_SURTURSLAIR = 'kunlun_surturslair',
    HALA_KUNLUN = 'hala_kunlun',
    KUNLUN_SVARTLHEIM = 'kunlun_svartlheim',
    HONGKONGSANCTUM_VORMIR = 'hongkongsanctum_vormir',
    SAKAAR_VORMIR = 'sakaar_vormir',
    HONGKONGSANCTUM_SAKAAR = 'hongkongsanctum_sakaar',
    PYMLABS_SAKAAR = 'pymlabs_sakaar',
    NIDAVELLIR_SAKAAR = 'nidavellir_sakaar',
    SAKAAR_XANDAR_1 = 'sakaar_xandar_1',
    SAKAAR_XANDAR_2 = 'sakaar_xandar_2',
    CAVEOFAGES_XANDAR_1 = 'caveofages_xandar_1',
    CAVEOFAGES_XANDAR_2 = 'caveofages_xandar_2',
    CAVEOFAGES_SURTURSLAIR_1 = 'caveofages_surturslair_1',
    CAVEOFAGES_SURTURSLAIR_2 = 'caveofages_surturslair_2',
    HALA_SURTURSLAIR_1 = 'hala_surturslair_1',
    HALA_SURTURSLAIR_2 = 'hala_surturslair_2',
    HALA_SVARTLHEIM_1 = 'hala_svartlheim_1',
    HALA_SVARTLHEIM_2 = 'hala_svartlheim_2',
    NIFLHEIM_SVARTLHEIM = 'niflheim_svartlheim',
    CONTRAXIA_SURTURSLAIR = 'contraxia_surturslair',
    CONTRAXIA_HALA = 'contraxia_hala',
    CONTRAXIA_NIFLHEIM = 'contraxia_niflheim',
    ASGARD_CONTRAXIA = 'asgard_contraxia',
    CONTRAXIA_QUANTUMREALM = 'contraxia_quantumrealm',
    ASGARD_CAVEOFAGES_1 = 'asgard_caveofages_1',
    ASGARD_CAVEOFAGES_2 = 'asgard_caveofages_2',
    ASGARD_QUANTUMREALM = 'asgard_quantumrealm',
    ASGARD_NEWYORKCITY = 'asgard_newyorkcity',
    ASGARD_NIDAVELLIR_1 = 'asgard_nidavellir_1',
    ASGARD_NIDAVELLIR_2 = 'asgard_nidavellir_2',
    NIDAVELLIR_XANDAR = 'nidavellir_xandar',
    NEWYORKCITY_NIDAVELLIR_1 = 'newyorkcity_nidavellir_1',
    NEWYORKCITY_NIDAVELLIR_2 = 'newyorkcity_nidavellir_2',
    NIDAVELLIR_PYMLABS = 'nidavellir_pymlabs',
    HONGKONGSANCTUM_PYMLABS = 'hongkongsanctum_pymlabs',
    HONGKONGSANCTUM_WAKANDA = 'hongkongsanctum_wakanda',
    MUSPELHEIM_NIFLHEIM = 'muspelheim_niflheim',
    NIFLHEIM_YOTUNHEIM_1 = 'niflheim_yotunheim_1',
    NIFLHEIM_YOTUNHEIM_2 = 'niflheim_yotunheim_2',
    MUSPELHEIM_YOTUNHEIM = 'muspelheim_yotunheim',
    QUANTUMREALM_YOTUNHEIM = 'quantumrealm_yotunheim',
    KAMARTAJ_YOTUNHEIM = 'kamartaj_yotunheim',
    KAMARTAJ_MUSPELHEIM = 'kamartaj_muspelheim',
    HELICARRIER_KAMARTAJ = 'helicarrier_kamartaj',
    HELICARRIER_YOTUNHEIM_1 = 'helicarrier_yotunheim_1',
    HELICARRIER_YOTUNHEIM_2 = 'helicarrier_yotunheim_2',
    HELICARRIER_QUANTUMREALM = 'helicarrier_quantumrealm',
    NEWYORKCITY_QUANTUMREALM = 'newyorkcity_quantumrealm',
    HELICARRIER_TRISKELION_1 = 'helicarrier_triskelion_1',
    HELICARRIER_TRISKELION_2 = 'helicarrier_triskelion_2',
    HELICARRIER_NEWYORKCITY = 'helicarrier_newyorkcity',
    NEWYORKCITY_TRISKELION = 'newyorkcity_triskelion',
    AVENGERSHQ_TRISKELION_1 = 'avengershq_triskelion_1',
    AVENGERSHQ_TRISKELION_2 = 'avengershq_triskelion_2',
    AVENGERSHQ_NEWYORKCITY_1 = 'avengershq_newyorkcity_1',
    AVENGERSHQ_NEWYORKCITY_2 = 'avengershq_newyorkcity_2',
    NEWYORKCITY_PYMLABS = 'newyorkcity_pymlabs',
    PYMLABS_WAKANDA = 'pymlabs_wakanda',
    AVENGERSHQ_WAKANDA = 'avengershq_wakanda',
    AVENGERSHQ_SOKOVIA_1 = 'avengershq_sokovia_1',
    AVENGERSHQ_SOKOVIA_2 = 'avengershq_sokovia_2',
    SOKOVIA_WAKANDA_1 = 'sokovia_wakanda_1',
    SOKOVIA_WAKANDA_2 = 'sokovia_wakanda_2'
}

export enum City {
	DARK_DIMENSION = 'darkdimension',
    GIBBORIM = 'gibborim',
    GALACTUS = 'galactus',
    ZENN_LA = 'zennla',
    KNOWHERE = 'knowhere',
    CHITAURI_SANCTUARY = 'chitaurisanctuary',
    ZEN_WHOBERI = 'zenwhoberi',
    EGO = 'ego',
    KITSON = 'kitson',
    CAVE_OF_THE_DRAGON = 'caveofthedragon',
    KUN_LUN = 'kunlun',
    VANAHEIM = 'vanaheim',
    TITAN = 'titan',
    VORMIR = 'vormir',
    SAKAAR = 'sakaar',
    XANDAR = 'xandar',
    CAVE_OF_AGES = 'caveofages',
    SURTURS_LAIR = 'surturslair',
    HALA = 'hala',
    SVARTLHEIM = 'svartlheim',
    NIFLHEIM = 'niflheim',
    CONTRAXIA = 'contraxia',
    QUANTUM_REALM = 'quantumrealm',
    ASGARD = 'asgard',
    NIDAVELLIR = 'nidavellir',
    YOTUNHEIM = 'yotunheim',
    HONG_KONG_SANCTUM = 'hongkongsanctum',
    WAKANDA = 'wakanda',
    SOKOVIA = 'sokovia',
    PYM_LABS = 'pymlabs',
    AVENGERS_HQ = 'avengershq',
    NEW_YORK_CITY = 'newyorkcity',
    TRISKELION = 'triskelion',
    HELICARRIER = 'helicarrier',
    KAMAR_TAJ = 'kamartaj',
    MUSPELHEIM = 'muspelheim'
}

export let cityPrintNames: {[index: string]: string} = {
    'darkdimension': 'Dark Dimension',
    'gibborim' : 'Gibborim',
    'galactus' : 'Galactus',
    'zennla' : 'Zenn-La',
    'knowhere' : 'Knowhere',
    'chitaurisanctuary' : 'Chitauri Sanctuary',
    'zenwhoberi' : 'Zen-Whoberi',
    'ego' : 'Ego',
    'kitson' : 'Kitson',
    'caveofthedragon' : 'Cave of the Dragon',
    'kunlun' : 'K\'un-Lun',
    'vanaheim' : 'Vanaheim',
    'titan' : 'Titan',
    'vormir' : 'Vormir',
    'sakaar' : 'Sakaar',
    'xandar' : 'Xandar',
    'caveofages' : 'Cave of Ages',
    'surturslair' : 'Surtur\'s Lair',
    'hala' : 'Hala',
    'svartlheim' : 'Svartlheim',
    'niflheim' : 'Niflheim',
    'contraxia' : 'Contraxia',
    'quantumrealm' : 'Quantum Realm',
    'asgard' : 'Asgard',
    'nidavellir' : 'Nidavellir',
    'yotunheim' : 'Yotunheim',
    'hongkongsanctum' : 'Hong Kong Sanctum',
    'wakanda' : 'Wakanda',
    'sokovia' : 'Sokovia',
    'pymlabs' : 'Pym Labs',
    'avengershq' : 'Avenger\'s HQ',
    'newyorkcity' : 'New York City',
    'triskelion' : 'Triskelion',
    'helicarrier' : 'Helicarrier',
    'kamartaj' : 'Kamar-Taj',
    'muspelheim' : 'Muspelheim'
};

export let carsToPoints: {[index: number]: number} = {
    2: 1,
    3: 4,
    4: 7,
    5: 10,
    6: 15,
};

export let typeToMaterial: {[material: string]: MaterialType} = {
    'any' : MaterialType.ANY,
	'reality' : MaterialType.REALITY_SHARD,
	'soul' : MaterialType.SOUL_SHARD,
	'space' : MaterialType.SPACE_SHARD,
	'mind' : MaterialType.MIND_SHARD,
	'power' : MaterialType.POWER_SHARD,
	'time' : MaterialType.TIME_SHARD,
	'vibranium' : MaterialType.VIBRANIUM,
	'palladium' : MaterialType.PALLADIUM
};

export let typeToClaimableMaterials: {[material: string]: MaterialType[]} = {
    'any' : [MaterialType.VIBRANIUM, MaterialType.TIME_SHARD, MaterialType.SPACE_SHARD, MaterialType.SOUL_SHARD, MaterialType.REALITY_SHARD, MaterialType.POWER_SHARD, MaterialType.PALLADIUM, MaterialType.MIND_SHARD, MaterialType.INFINITY_GAUNTLET],
	'reality' : [MaterialType.REALITY_SHARD, MaterialType.INFINITY_GAUNTLET],
	'soul' : [MaterialType.SOUL_SHARD, MaterialType.INFINITY_GAUNTLET],
	'space' : [MaterialType.SPACE_SHARD, MaterialType.INFINITY_GAUNTLET],
	'mind' : [MaterialType.MIND_SHARD, MaterialType.INFINITY_GAUNTLET],
	'power' : [MaterialType.POWER_SHARD, MaterialType.INFINITY_GAUNTLET],
	'time' : [MaterialType.TIME_SHARD, MaterialType.INFINITY_GAUNTLET],
	'vibranium' : [MaterialType.VIBRANIUM, MaterialType.INFINITY_GAUNTLET],
	'palladium' : [MaterialType.PALLADIUM, MaterialType.INFINITY_GAUNTLET]
};

export class Route {
	public routeName: RouteName;
	public cities: City[];
	public numCars: number;
	public type: RouteType;
	public ownerId: number;

	constructor(routeName: RouteName, cities: City[], numCars: number, type: RouteType, ownerId: number) {
		this.routeName = routeName;
		this.cities = cities;
		this.numCars = numCars;
		this.type = type;
		this.ownerId = ownerId;
    }

    public getPoints(): number {
        return carsToPoints[this.numCars];
    }

    public getPrintName() {
        return cityPrintNames[this.cities[0]] + ' to ' + cityPrintNames[this.cities[1]];
    }

    public getClaimableTypes(): MaterialType[] {
        return typeToClaimableMaterials[this.type];
    }

    toDestCard(): DestinationCard {
        return new DestinationCard({'cities': this.cities, 'points': this.getPoints()});
    }
}
