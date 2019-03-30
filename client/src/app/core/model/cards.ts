import { City } from './city.enum';
import { RouteType } from './route-type.enum';
import { MaterialType } from './material-type.enum';

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

        this.type = <MaterialType> MaterialType[(shardCard.type as string).toUpperCase()];
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
        'any' : 'shard_circle.svg',
    };

    private static readonly printNamesMap: {[material: string]: string} = {
        'reality_shard' : 'Reality Stone Shard',
        'soul_shard' : 'Soul Stone Shard',
        'space_shard' : 'Space Stone Shard',
        'mind_shard' : 'Mind Stone Shard',
        'power_shard' : 'Power Stone Shard',
        'time_shard' : 'Time Stone Shard',
        'vibranium' : 'Vibranium Shard',
        'palladium' : 'Palladium Shard',
        'infinity_gauntlet' : 'Infinity Gauntlet Shard',
        'any' : 'Any'
    };

    public static readonly typeMap: {[material: string]: RouteType} = {
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
    public static getImageByType(type: MaterialType): string {
        return this.shardImageMap[type];
    }

    public getImage(): string {
        return ShardCard.shardImageMap[this.type];
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

abstract class ICardSelectionPair<ICard> {
    public card: ICard;
    public selected: boolean;

    constructor(card: ICard) {
        this.card = card;
        this.selected = false;
    }
}

export class ShardCardSelectionPair extends ICardSelectionPair <ShardCard> {
    constructor(card: ShardCard) {
        super(card);
    }
}

export class DestCardSelectionPair extends ICardSelectionPair <DestinationCard> {
    constructor(card: DestinationCard) {
        super(card);
    }
}

abstract class ICardSelectionDeck<ICard> {
    public cardPairs: ICardSelectionPair<ICard>[];
    public numSelected;

    constructor(cardPairs: ICardSelectionPair<ICard>[]) {
        this.numSelected = 0;
        this.cardPairs = cardPairs;
    }

    public size(): number {
        return this.cardPairs.length;
    }

    public abstract fromCards(cards: ICard[]);

    public abstract getSelected(): ICardSelectionDeck<ICard>;

    public abstract toDeck();

    public selectCard(cardPair: ICardSelectionPair<ICard>) {
        cardPair.selected = !cardPair.selected;
        if (cardPair.selected) {
            this.numSelected++;
        } else {
            this.numSelected--;
        }
    }

    public getCards(): ICard[] {
        const cards: ICard[] = [];
        this.cardPairs.forEach(cardPair => {
            cards.push(cardPair.card);
        });
        return cards;
    }
}

export class ShardCardSelectionDeck extends ICardSelectionDeck<ShardCard> {
    private selectedType: MaterialType;
    constructor(cardPairs: ICardSelectionPair<ShardCard>[]) {
        super(cardPairs);
        this.selectedType = MaterialType.ANY;
    }

    public fromCards(cards: ShardCard[]) {
        const cardPairs: ShardCardSelectionPair[] = [];
        cards.forEach(card => {
            cardPairs.push(new ShardCardSelectionPair(card));
        });
        this.cardPairs = cardPairs;
    }

    public getSelected(): ICardSelectionDeck<ShardCard> {
        return new ShardCardSelectionDeck(this.cardPairs.filter(cardPair => cardPair.selected));
    }


    public toDeck(): ShardCardDeck {
        const cards: ShardCard[] = [];
        this.cardPairs.forEach(cardPair => {
            cards.push(cardPair.card);
        });
        return new ShardCardDeck(cards);
    }

    public filterOnType(types: MaterialType[]): ShardCardSelectionDeck {
        return new ShardCardSelectionDeck(this.cardPairs.filter(cardPair => types.includes(cardPair.card.type)));
    }

    public getSelectedType(): MaterialType {
        return this.selectedType;
    }

    public filterOnSelectedType(includeWild: boolean = true) {
        if (includeWild) {
            return this.filterOnType([this.selectedType, MaterialType.INFINITY_GAUNTLET]);
        } else {
            return this.filterOnType([this.selectedType]);
        }
      }

    public selectCard(cardPair: ShardCardSelectionPair) {
        super.selectCard(cardPair);
        if (cardPair.selected) {
            this.setSelectedType(cardPair.card.type);
        } else {
            this.unsetSelectedType(cardPair.card.type);
        }
    }

    private setSelectedType(type: MaterialType) {
        if (this.selectedType === MaterialType.ANY && type !== MaterialType.INFINITY_GAUNTLET) {
          this.selectedType = type;
        }
      }

      private unsetSelectedType(type: MaterialType) {
        if (this.numSelected === 0) {
          this.selectedType = MaterialType.ANY;
        }
      }
}

export class DestCardSelectionDeck extends ICardSelectionDeck<DestinationCard> {
    constructor(cardPairs: ICardSelectionPair<DestinationCard>[]) {
        super(cardPairs);
    }

    public fromCards(cards: DestinationCard[]) {
        const cardPairs: DestCardSelectionPair[] = [];
        cards.forEach(card => {
            cardPairs.push(new DestCardSelectionPair(card));
        });
        this.cardPairs = cardPairs;
    }

    public getSelected(): ICardSelectionDeck<DestinationCard> {
        return new DestCardSelectionDeck(this.cardPairs.filter(cardPair => cardPair.selected));
    }

    public getDiscarded(): ICardSelectionDeck<DestinationCard> {
        return new DestCardSelectionDeck(this.cardPairs.filter(cardPair => !cardPair.selected));
    }

    public toDeck(): DestinationCardDeck {
        const cards: DestinationCard[] = [];
        this.cardPairs.forEach(cardPair => {
            cards.push(cardPair.card);
        });
        return new DestinationCardDeck(cards);
    }
}
