package models

import MaterialType
import IShardCard
import IDestinationCard
import java.io.Serializable

class ShardCard(type: MaterialType) : IShardCard(type) {
    constructor(dto: IShardCard) : this(dto.type)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShardCard

        if (type != other.type) return false

        return true
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }

    fun getMaterialTypeString(): String {
        return when (type) {
            MaterialType.REALITY_SHARD -> "Reality Shard"
            MaterialType.SOUL_SHARD -> "Soul Shard"
            MaterialType.SPACE_SHARD -> "Space Shard"
            MaterialType.MIND_SHARD -> "Mind Shard"
            MaterialType.POWER_SHARD -> "Power Shard"
            MaterialType.TIME_SHARD -> "Time Shard"
            MaterialType.VIBRANIUM -> "Vibranium Shard"
            MaterialType.PALLADIUM -> "Palladium Shard"
            MaterialType.INFINITY_GAUNTLET -> "Infinity Gauntlet"
        }
    }
}


class DestinationCard(cities: Set<String>, points: Int): IDestinationCard(cities, points) {
    constructor(dto: IDestinationCard) : this(dto.cities, dto.points)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DestinationCard

        if (cities != other.cities) return false
        if (points != other.points) return false

        return true
    }

    override fun hashCode(): Int {
        var result = cities.hashCode()
        result = 31 * result + points
        return result
    }
}


abstract class IDeck<T>(var cards: MutableList<T>) : Serializable {

    fun shuffle() {
        cards.shuffle()
    }

    fun getNext(): T {
        return cards.removeAt(0)
    }

    fun push(t: T) {
        cards.add(t)
    }

    val size: Int
        get() = cards.size

}

class ShardCardDeck(var shardCards: MutableList<ShardCard>) : IDeck<ShardCard>(shardCards) {
    fun initializeDeck(): ShardCardDeck {
        for (i in 0..11) {
            shardCards.add(ShardCard(MaterialType.INFINITY_GAUNTLET))
            shardCards.add(ShardCard(MaterialType.MIND_SHARD))
            shardCards.add(ShardCard(MaterialType.PALLADIUM))
            shardCards.add(ShardCard(MaterialType.VIBRANIUM))
            shardCards.add(ShardCard(MaterialType.REALITY_SHARD))
            shardCards.add(ShardCard(MaterialType.POWER_SHARD))
            shardCards.add(ShardCard(MaterialType.SOUL_SHARD))
            shardCards.add(ShardCard(MaterialType.SPACE_SHARD))
            shardCards.add(ShardCard(MaterialType.TIME_SHARD))
            if (i < 2) {
                shardCards.add(ShardCard(MaterialType.INFINITY_GAUNTLET))
            }
        }
        return this
    }
}

class DestinationCardDeck(var destinationCards: MutableList<DestinationCard>) : IDeck<DestinationCard>(destinationCards) {
    fun initializeDeck(): DestinationCardDeck {
        destinationCards.add(DestinationCard(setOf("titan", "kunlun"), 4))
        destinationCards.add(DestinationCard(setOf("caveofages", "svartlheim"), 5))
        destinationCards.add(DestinationCard(setOf("avengershq", "yotunheim"), 6))
        destinationCards.add(DestinationCard(setOf("nidavellir", "niflheim"), 7))
        destinationCards.add(DestinationCard(setOf("chitaurisanctuary", "ego"), 7))
        destinationCards.add(DestinationCard(setOf("zenwhoberi", "knowhere"), 8))
        destinationCards.add(DestinationCard(setOf("sakaar", "svartlheim"), 8))
        destinationCards.add(DestinationCard(setOf("hongkongsanctum", "quantumrealm"), 8))
        destinationCards.add(DestinationCard(setOf("wakanda", "yotunheim"), 9))
        destinationCards.add(DestinationCard(setOf("hongkongsanctum", "surturslair"), 9))
        destinationCards.add(DestinationCard(setOf("gibborim", "knowhere"), 9))
        destinationCards.add(DestinationCard(setOf("nidavellir", "vanaheim"), 9))
        destinationCards.add(DestinationCard(setOf("sakaar", "kunlun"), 10))
        destinationCards.add(DestinationCard(setOf("pymlabs", "muspelheim"), 10))
        destinationCards.add(DestinationCard(setOf("galactus", "caveofthedragon"), 11))
        destinationCards.add(DestinationCard(setOf("hala", "avengershq"), 11))
        destinationCards.add(DestinationCard(setOf("titan", "newyorkcity"), 11))
        destinationCards.add(DestinationCard(setOf("vormir", "contraxia"), 11))
        destinationCards.add(DestinationCard(setOf("vormir", "svartlheim"), 12))
        destinationCards.add(DestinationCard(setOf("sokovia", "muspelheim"), 12))
        destinationCards.add(DestinationCard(setOf("darkdimension", "vanaheim"), 13))
        destinationCards.add(DestinationCard(setOf("chitaurisanctuary", "caveofthedragon"), 13))
        destinationCards.add(DestinationCard(setOf("wakanda", "niflheim"), 13))
        destinationCards.add(DestinationCard(setOf("knowhere", "nidavellir"), 16))
        destinationCards.add(DestinationCard(setOf("zennla", "yotunheim"), 17))
        destinationCards.add(DestinationCard(setOf("galactus", "quantumrealm"), 17))
        destinationCards.add(DestinationCard(setOf("darkdimension", "wakanda"), 20))
        destinationCards.add(DestinationCard(setOf("knowhere", "muspelheim"), 20))
        destinationCards.add(DestinationCard(setOf("knowhere", "avengershq"), 21))
        destinationCards.add(DestinationCard(setOf("gibborim", "avengershq"), 22))
        return this
    }

}