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
    INFINITY_GAUNTLET = "infinity_gauntlet",
    ANY = "any"
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
            this.cities.push(city as City);
        });
    }
}

export class ShardCard extends ICard {
    public type: MaterialType
    
    private static readonly shardImageMap: {[material: string]: string} = {
        "reality_shard" : "reality_stone.svg",
        "soul_shard" : "soul_stone.svg",
        "space_shard" : "space_stone.svg",
        "mind_shard" : "mind_stone.svg",
        "power_shard" : "power_stone.svg",
        "time_shard" : "time_stone.svg",
        "vibranium" : "vibranium.svg",
        "palladium" : "palladium.svg",
        "infinity_gauntlet" : "gauntlet.svg",
        "any" : "shard_circle.svg",
    }

    private static readonly printNamesMap: {[material: string]: string} = {
        "reality_shard" : "Reality Stone",
        "soul_shard" : "Soul Stone",
        "space_shard" : "Space Stone",
        "mind_shard" : "Mind Stone",
        "power_shard" : "Power Stone",
        "time_shard" : "Time Stone",
        "vibranium" : "Vibranium",
        "palladium" : "Palladium",
        "infinity_gauntlet" : "Infinity Gauntlet",
        "any" : "Any Type",
    }

    constructor(shardCard: any) {
        super();
        if (!("type" in shardCard)) {
            throw new TypeError('Unable to deserialize ShardCard object, ' + JSON.stringify(shardCard));
        }

        this.type = <MaterialType> MaterialType[shardCard.type];
    }

    public static getPrintName(type: MaterialType): string{
        return this.printNamesMap[type];
    }
    public static getImage(type: MaterialType): string{
        return this.shardImageMap[type];
    }
}

abstract class ICardDeck<T> {
	public cards: T[]
    abstract size(): Number;
    
    constructor(cards: T[]){
        this.cards = cards
    }
}

export class DestinationCardDeck extends ICardDeck<DestinationCard> {
	public size(): Number {
		return this.cards.length
	}
}

export class ShardCardDeck extends ICardDeck<ShardCard> {
    public types: Set<MaterialType> = new Set<MaterialType>();

    constructor(cards: ShardCard[]){
        super(cards);
        let types: Set<MaterialType> = new Set<MaterialType>();
        cards.forEach(card => {
            types.add(card.type);
        });
        this.types = types;
    }

	public size(): Number {
		return this.cards.length
    }
    
    public getCountOf(type: MaterialType): number{
        return this.cards.filter(card => card.type === type).length;
    }
}

abstract class ICardSelectionPair<ICard>{
    public card: ICard
    public selected: boolean;
    
    constructor(card: ICard){
        this.card = card
        this.selected = false;
    }
}

export class ShardCardSelectionPair extends ICardSelectionPair <ShardCard>{
    constructor(card: ShardCard){
        super(card);
    }
}

abstract class ICardSelectionDeck<ICard>{
    public cardPairs: ICardSelectionPair<ICard>[];
    public numSelected;
    
    constructor(cardPairs: ICardSelectionPair<ICard>[]){
        this.numSelected = 0;
        this.cardPairs = cardPairs;
    }
    
    public abstract fromCards(cards: ICard[]);

    public abstract getSelected(): ICardSelectionDeck<ICard>;

    public abstract toDeck();

    public abstract selectCard(cardPair: ICardSelectionPair<ICard>);
}

export class ShardCardSelectionDeck extends ICardSelectionDeck<ShardCard> {
    private selectedType: MaterialType;
    constructor(cardPairs: ICardSelectionPair<ShardCard>[]){
        super(cardPairs);
        this.selectedType = MaterialType.ANY;
    }

    public fromCards(cards: ShardCard[]) {
        let cardPairs: ShardCardSelectionPair[] = [];
        cards.forEach(card => {
            cardPairs.push(new ShardCardSelectionPair(card));
        });
        this.cardPairs = cardPairs;
    }

	public size(): Number {
		return this.cardPairs.length
    }
    
    public getSelected(): ICardSelectionDeck<ShardCard> {
        return new ShardCardSelectionDeck(this.cardPairs.filter(cardPair => cardPair.selected));
    }

    public selectCard(cardPair: ShardCardSelectionPair) {
        cardPair.selected = !cardPair.selected;
        if(cardPair.selected){
            this.numSelected++;
            this.setSelectedType(cardPair.card.type);
        } else{
            this.numSelected--;
            this.unsetSelectedType(cardPair.card.type);
        }
    }

    public toDeck(): ShardCardDeck {
        let cards: ShardCard[] = [];
        this.cardPairs.forEach(cardPair => {
            cards.push(cardPair.card);
        });
        return new ShardCardDeck(cards);
    }

    public filterOnType(types: MaterialType[]): ShardCardSelectionDeck {
        return new ShardCardSelectionDeck(this.cardPairs.filter(cardPair => types.includes(cardPair.card.type)));
    }

    public getSelectedType(): MaterialType{
        return this.selectedType;
    }

    public filterOnSelectedType(includeWild: boolean = true){
        if(includeWild){
            return this.filterOnType([this.selectedType, MaterialType.INFINITY_GAUNTLET]);
        } else {
            return this.filterOnType([this.selectedType]);
        }
      }

    private setSelectedType(type: MaterialType){
        if (this.selectedType === MaterialType.ANY && type !== MaterialType.INFINITY_GAUNTLET){
          this.selectedType = type;
        }
      }
    
      private unsetSelectedType(type: MaterialType){
        if (this.numSelected === 0){
          this.selectedType = MaterialType.ANY;
        }
      }
}