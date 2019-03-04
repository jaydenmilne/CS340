package models

enum class MaterialType(val material: String) {
    REALITY_SHARD("reality_shard"),
    SOUL_SHARD("soul_shard"),
    SPACE_SHARD("space_shard"),
    MIND_SHARD("mind_shard"),
    POWER_SHARD("power_shard"),
    TIME_SHARD("time_shard"),
    VIBRANIUM("vibranium"),
    PALLADIUM("palladium"),
    INFINITY_GAUNTLET("infinity_gauntlet")
}

interface ICard

class ShardCard(val type: MaterialType) : ICard
class DestinationCard(val cities: Set<String>)

abstract class IDeck<T>(var cards: MutableList<T>) {

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

class ShardCardDeck(var shardCards: MutableList<ShardCard>) : IDeck<ShardCard>(shardCards)
class DestinationCardDeck(var destinationCards: MutableList<DestinationCard>) : IDeck<DestinationCard>(destinationCards)