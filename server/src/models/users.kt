package models

import commands.UpdateHandCommand

private var nextUserId = -1
private const val STARTING_TRAINS = 45

fun getNextUserId(): Int {
    nextUserId++
    return nextUserId
}

object Users {
    private var usersByUserId = mutableMapOf<Int, User>()
    private var usersByUsername = mutableMapOf<String, User>()

    // Autoincrement
    private var i = 0

    fun addUser(user: User) {
        usersByUserId[user.userId] = user
        usersByUsername[user.username] = user
    }

    fun removeUser(user: User) {
        usersByUserId.remove(user.userId)
        usersByUsername.remove(user.username)
    }

    fun isUsernameTaken(username: String): Boolean {
        return usersByUsername.containsKey(username)
    }

    fun getUserByUsername(username: String): User? {
        return usersByUsername[username]
    }

    fun getUserById(id: Int): User? {
        return usersByUserId[id]
    }

}

class User(var username: String) {

    var userId = getNextUserId()
    var ready = false
    var authToken = ""
    var color = Color.YELLOW

    var points = 0
    var shardCards = ShardCardDeck(mutableListOf())
    var destinationCards = DestinationCardDeck(mutableListOf())
    var numRemainingTrains = STARTING_TRAINS;
    var hasLongestRoute = false
    var setupComplete = false

    var turnOrder = -1

    @Transient private var passwordHash = ""
    @Transient var queue = CommandQueue()
    @Transient var isDrawingSecondCard = false

    constructor(username: String, password: String) : this(username) {
        updatePassword(password)
    }

    fun updatePassword(password: String) {
        this.passwordHash = PasswordStorage.createHash(password)
    }

    fun verifyPassword(password: String): Boolean {
        return PasswordStorage.verifyPassword(password, passwordHash)
    }

    fun toGamePlayer(): GamePlayer {

        return GamePlayer(this.userId,
                this.username,
                this.color,
                this.ready,
                this.points,
                this.shardCards.size,
                this.destinationCards.size,
                this.numRemainingTrains,
                this.hasLongestRoute,
                this.turnOrder)
    }

    fun updateHand(){
        queue.push(UpdateHandCommand(destinationCards.destinationCards, shardCards.shardCards))
    }
}

class ClientUser(val userId: Int, var username: String, val authToken: String) {
    constructor() : this(0, "", "")
}

class GamePlayer(val userId: Int,
                 val username: String,
                 val color: Color,
                 val ready: Boolean,
                 val points: Int,
                 val numTrainCards: Int,
                 val numDestinationCards: Int,
                 val numRemainingTrains: Int,
                 val hasLongestRoute: Boolean,
                 val turnOrder: Int)

enum class Color(val rgb: String) {
    YELLOW("fdd835"),
    GREEN("66bb6a"),
    BLUE("1976d2"),
    PURPLE("7b1fa2"),
    RED("d32f2f"),
    ORANGE("ff5722");
}

class PlayerPoints(val userId: Int,
                   val username: String,
                   val totalPoints: Int,
                   val claimedRoutePoints: Int,
                   val completedDestinationPoints: Int,
                   val incompleteDestinationPoints: Int,
                   val longestRoutePoints: Int)