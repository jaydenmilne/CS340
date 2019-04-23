import java.io.Serializable

class PlayerPoints(val userId: Int,
                   val username: String,
                   val totalPoints: Int,
                   val claimedRoutePoints: Int,
                   val completedDestinationPoints: Int,
                   val incompleteDestinationPoints: Int,
                   val longestRoutePoints: Int): Serializable