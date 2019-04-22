import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/22/2019.
 * CardInterfaces - interfaces for Card objects
 */


interface ICard : Serializable

interface IShardCard : ICard {
    val type: MaterialType
}

interface IDestinationCard : ICard {
    val cities: Set<String>
    val points: Int
}