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
    constructor(public cities: City[], public points: number){
        super();
    }
}

export class ShardCard extends ICard {
	private type: MaterialType

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