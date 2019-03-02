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

export enum City {
	DARK_DIMENSION = "darkdimension",
    GIBBORIM = "gibborim",
    GALACTUS = "galactus",
    ZENN_LA = "zennla",
    KNOWHERE = "knowhere",
    CHITAURI_SANCTUARY = "chitaurisanctuary",
    ZEN_WHOBERI = "zenwhoberi",
    EGO = "ego",
    KITSON = "kitson",
    CAVE_OF_THE_DRAGON = "caveofthedragon",
    KUN_LUN = "kunlun",
    VANAHEIM = "vanaheim",
    TITAN = "titan",
    VORMIR = "vormin",
    SAKAAR = "sakaar",
    XANDAR = "xandar",
    CAVE_OF_AGES = "caveofages",
    SURTURS_LAIR = "surturslair",
    HALA = "hala",
    SVARTLHEIM = "svartlheim",
    NIFLHEIM = "niflheim",
    CONTRAXIA = "contraxia",
    QUANTUM_REALM = "quantumrealm",
    ASGARD = "asgard",
    NIDAVELLIR = "nidavellir",
    YOTUNHEIM = "yotunheim",
    HONG_KONG_SANCTUM = "hongkongsanctum",
    WAKANDA = "wakanda",
    SOKOVIA = "sokovia",
    PYM_LABS = "pymlabs",
    AVENGERS_HQ = "avengershq",
    NEW_YORK_CITY = "newyorkcity",
    TRISKELION = "triskelion",
    HELICARRIER = "helicarrier",
    KAMAR_TAJ = "kamartaj",
    MUSPELHEIM = "muspelheim"
}

export class DestinationCard {
	private cities: City[]

	public setCities(cities: City[]) {
		this.cities = cities;
	}

	public getCities(): City[] {
		return this.cities;
	}
}

export class TrainCard {
	private type: MaterialType

	public setMaterialType(type: MaterialType) {
		this.type = type;
	}

	public getMaterialType(): MaterialType {
		return this.type;
	}
}

export class DestinationCardDeck {
	private cards: DestinationCard[]

	public getTop(): DestinationCard {
		return this.cards.shift()
	}

	public size(): Number {
		return this.cards.length
	}
}

export class TrainCardDeck {
	private cards: TrainCard[]

	public getTop(): TrainCard {
		return this.cards.shift()
	}

	public size(): Number {
		return this.cards.length
	}
}