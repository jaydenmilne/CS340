import { DestinationCard } from './cards';
import { MaterialType } from "./material-type.enum";
import { RouteType } from './route-type.enum';
import { City } from './city.enum';
import { RouteName } from './route-name.enum';

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
