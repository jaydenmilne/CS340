import { City, RouteType, Route } from './route';

export enum MaterialType {
    REALITY_SHARD = 'reality_shard',
    SOUL_SHARD = 'soul_shard',
    SPACE_SHARD = 'space_shard',
    MIND_SHARD = 'mind_shard',
    POWER_SHARD = 'power_shard',
    TIME_SHARD = 'time_shard',
    VIBRANIUM = 'vibranium',
    PALLADIUM = 'palladium',
    INFINITY_GAUNTLET = 'infinity_gauntlet'
}

abstract class ICard {}

export class DestinationCard extends ICard {
    public cities: City[];
    public points: number;

    constructor(destinationCard: any) {
        super();
        if (!('cities' in destinationCard && 'points' in destinationCard)) {
            throw new TypeError('Unable to deserialize DestinationCard object, ' + JSON.stringify(destinationCard));

        }

        this.cities = [];
        this.points = destinationCard.points;
        destinationCard.cities.forEach(city => {
            this.cities.push(city as City);
        });
    }
}

export class ShardCard extends ICard {

    constructor(shardCard: any) {
        super();
        if (!('type' in shardCard)) {
            throw new TypeError('Unable to deserialize ShardCard object, ' + JSON.stringify(shardCard));
        }

        this.type = <MaterialType> MaterialType[shardCard.type];
    }

    private static readonly shardImageMap: {[material: string]: string} = {
        'reality_shard' : 'reality_stone.svg',
        'soul_shard' : 'soul_stone.svg',
        'space_shard' : 'space_stone.svg',
        'mind_shard' : 'mind_stone.svg',
        'power_shard' : 'power_stone.svg',
        'time_shard' : 'time_stone.svg',
        'vibranium' : 'vibranium.svg',
        'palladium' : 'palladium.svg',
        'infinity_gauntlet' : 'gauntlet.svg',
    };

    private static readonly printNamesMap: {[material: string]: string} = {
        'reality_shard' : 'Reality Stone',
        'soul_shard' : 'Soul Stone',
        'space_shard' : 'Space Stone',
        'mind_shard' : 'Mind Stone',
        'power_shard' : 'Power Stone',
        'time_shard' : 'Time Stone',
        'vibranium' : 'Vibranium',
        'palladium' : 'Palladium',
        'infinity_gauntlet' : 'Infinity Gauntlet',
    };

    private static readonly typeMap: {[material: string]: RouteType} = {
        'reality_shard' : RouteType.REALITY,
        'soul_shard' : RouteType.SOUL,
        'space_shard' : RouteType.SPACE,
        'mind_shard' : RouteType.MIND,
        'power_shard' : RouteType.POWER,
        'time_shard' : RouteType.TIME,
        'vibranium' : RouteType.VIBRANIUM,
        'palladium' : RouteType.PALLADIUM,
        'infinity_gauntlet' : RouteType.ANY,
    };
    public type: MaterialType;

    public static getPrintName(type: MaterialType): string {
        return this.printNamesMap[type];
    }
    public static getImage(type: MaterialType): string {
        return this.shardImageMap[type];
    }
}

abstract class ICardDeck<T> {
	public cards: T[];
    abstract size(): Number;

    constructor(cards: T[]) {
        this.cards = cards;
    }
}

export class DestinationCardDeck extends ICardDeck<DestinationCard> {
	public size(): Number {
		return this.cards.length;
	}
}

export class ShardCardDeck extends ICardDeck<ShardCard> {
    public types: Set<MaterialType> = new Set<MaterialType>();

    constructor(cards: ShardCard[]) {
        super(cards);
        const types: Set<MaterialType> = new Set<MaterialType>();
        cards.forEach(card => {
            types.add(card.type);
        });
        this.types = types;
    }

	public size(): Number {
		return this.cards.length;
    }

    public getCountOf(type: MaterialType): number {
        return this.cards.filter(card => card.type === type).length;
    }
}
