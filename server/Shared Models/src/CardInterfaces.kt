import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/22/2019.
 * CardInterfaces - interfaces for Card objects
 */


interface ICard : Serializable

open class IShardCard(val type: MaterialType = MaterialType.INFINITY_GAUNTLET) : ICard {
}

open class IDestinationCard(val cities: Set<String> = setOf(), val points: Int) : ICard {
}