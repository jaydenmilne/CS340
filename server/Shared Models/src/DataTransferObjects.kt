import java.io.Serializable

class LobbyGameDTO(val gameId: Int,
                   val name: String,
                   val started: Boolean,
                   val players: MutableSet<GamePlayerDTO>): Serializable

class LoginUserDTO(val username: String,
                   val userId: Int,
                   val color: Color,
                   val ready: Boolean): Serializable

class GamePlayerDTO(val userId: Int,
                 val username: String,
                 val color: Color,
                 val ready: Boolean,
                 val points: Int,
                 val numTrainCards: Int,
                 val numDestinationCards: Int,
                 val numRemainingTrains: Int,
                 val hasLongestRoute: Boolean,
                 val longestRouteLength: Int,
                 val turnOrder: Int): Serializable