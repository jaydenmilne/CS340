package models

import commands.CommandException
import commands.UpdateHandCommand
import IUser

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

    fun getUsers(): List<User> {
        return usersByUserId.map { e -> e.value }
    }

}

class User(var username: String): IUser {
    override var userId = getNextUserId()
    var ready = false
    var authToken = ""
    var color = Color.BLUE

    var points = 0
    var shardCards = ShardCardDeck(mutableListOf())
    var destinationCards = DestinationCardDeck(mutableListOf())
    var numRemainingTrains = STARTING_TRAINS
    var hasLongestRoute = false
    var longestRouteLength = 0
    var setupComplete = false

    var turnOrder = -1

    private var passwordHash = ""
    var queue = CommandQueue()
    var isDrawingSecondCard = false

    constructor(username: String, password: String) : this(username) {
        updatePassword(password)
    }

    private fun updatePassword(password: String) {
        this.passwordHash = PasswordStorage.createHash(password)
    }

    fun verifyPassword(password: String): Boolean {
        return PasswordStorage.verifyPassword(password, passwordHash)
    }

    fun toGamePlayer(): GamePlayerDTO {
        return GamePlayerDTO(this.userId,
                this.username,
                this.color,
                this.ready,
                this.points,
                this.shardCards.size,
                this.destinationCards.size,
                this.numRemainingTrains,
                this.hasLongestRoute,
                this.longestRouteLength,
                this.turnOrder)
    }

    fun toLoginUserDTO(): LoginUserDTO {
        return LoginUserDTO(this.username,
                            this.userId,
                            this.color,
                            this.ready)
    }

    fun updateHand() {
        queue.push(UpdateHandCommand(destinationCards.destinationCards, shardCards.shardCards))
    }

    fun toPlayerPoints(): PlayerPoints {
        return PlayerPoints(this.userId,
                this.username,
                getRoutePoints() + getCompletedDestPoints() + getIncompleteDestPoints(),
                getRoutePoints(),
                getCompletedDestPoints(),
                getIncompleteDestPoints(),
                if (this.hasLongestRoute) 10 else 0
        )
    }

    private fun getCompletedDestPoints(): Int {
        val game = Games.getGameForPlayer(this) ?: throw CommandException("Bad game.")
        var points = 0

        this.destinationCards.destinationCards.forEach {
            if (game.getRouteBetweenCitiesForPlayer(this.userId, it.cities.elementAt(0), it.cities.elementAt(1))) {
                points += it.points
            }
        }

        return points
    }

    private fun getIncompleteDestPoints(): Int {
        val game = Games.getGameForPlayer(this) ?: throw CommandException("Bad game.")

        var points = 0

        this.destinationCards.destinationCards.forEach {
            if (!game.getRouteBetweenCitiesForPlayer(this.userId, it.cities.elementAt(0), it.cities.elementAt(1))) {
                points -= it.points
            }
        }

        return points
    }

    private fun getRoutePoints(): Int {
        val game = Games.getGameForPlayer(this) ?: return 0

        return game.getRoutePointsForPlayer(this.userId)
    }

    fun reset() {
        ready = false
        points = 0
        shardCards.cards.clear()
        destinationCards.cards.clear()
        numRemainingTrains = STARTING_TRAINS
        hasLongestRoute = false
        longestRouteLength = 0
        setupComplete = false
        turnOrder = -1
        isDrawingSecondCard = false
    }
}

class ClientUser(val userId: Int, var username: String, val authToken: String) {
    constructor() : this(0, "", "")
}

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