import { City } from './route';

export enum MaterialType {
    REALITY_SHARD = "reality_shard",
    SOUL_SHARD = "soul_shard",
    SPACE_SHARD = "space_shard",
    MIND_SHARD = "mind_shard",
    POWER_SHARD = "power_shard",
    TIME_SHARD = "time_shard",
    VIBRANIUM = "vibranium",
    PALLADIUM = "palladium",
    INFINITY_GAUNTLET = "infinity_gauntlet"
}

abstract class ICard {}

export class DestinationCard extends ICard {
	public cities: City[];
  public points: number;

    constructor(destinationCard: any) {
        super();
        if (!("cities" in destinationCard && "points" in destinationCard)) {
            throw new TypeError('Unable to deserialize DestinationCard object, ' + JSON.stringify(destinationCard));

        }

        this.cities = [];
        this.points = destinationCard.points;
        destinationCard.cities.forEach(city => {
            this.cities.push(City[city as keyof typeof City]);
        });
    }
}

export class ShardCard extends ICard {
	private type: MaterialType
    public readonly imageMap = {
        REALITY_SHARD: "reality_stone.svg",
        SOUL_SHARD: "soul_stone.svg",
        SPACE_SHARD: "space_stone.svg",
        MIND_SHARD: "mind_stone.svg",
        POWER_SHARD: "power_stone.svg",
        TIME_SHARD: "time_stone.svg",
        VIBRANIUM: "vibranium.svg",
        PALLADIUM: "palladium.svg",
        INFINITY_GAUNTLET: "gauntlet.svg"
    }

    constructor(shardCard: any) {
        super();
        if (!("type" in shardCard)) {
            throw new TypeError('Unable to deserialize ShardCard object, ' + JSON.stringify(shardCard));
        }

        this.type = shardCard.type;
    }

	public setMaterialType(type: MaterialType) {
		this.type = type;
	}

	public getMaterialType(): MaterialType {
		return this.type;
	}
}

abstract class ICardDeck<T> {
	protected cards: T[]
	abstract getTop(): T;
	abstract size(): Number;
}

export class CardDeck extends ICardDeck<DestinationCard> {
	public getTop(): DestinationCard {
		return this.cards.shift()
	}

	public size(): Number {
		return this.cards.length
	}
}

export class ShardCardDeck extends ICardDeck<ShardCard> {
	public getTop(): ShardCard {
		return this.cards.shift()
	}

	public size(): Number {
		return this.cards.length
	}
}