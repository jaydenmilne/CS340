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

class TrainCard(val type: MaterialType) : ICard
class DestinationCard(val cities: Set<String>)

abstract class IDeck<T>(var cards: List<T>) {

    fun shuffle() {
        cards = cards.shuffled()
    }

    fun getNext(): T {
        var top = cards.first()
        cards = cards.drop(1)

        return top
    }

    val size: Int
        get() = cards.size

}

class TrainCardDeck(var trainCards: List<TrainCard>) : IDeck<TrainCard>(trainCards)
class DestinationCardDeck(var destinationCards: List<DestinationCard>) : IDeck<DestinationCard>(destinationCards)