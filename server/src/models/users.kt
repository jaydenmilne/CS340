package models

private var nextUserId = -1

fun getNextUserID(): Int {
    nextUserId++
    return nextUserId
}

object Users {
    private var usersByUserID = mutableMapOf<Int, User>()
    private var usersByUsername = mutableMapOf<String, User>()

    // Autoincrement
    private var i = 0

    fun addUser(user: User) {
        usersByUserID[user.userID] = user
        usersByUsername[user.username] = user
    }

    fun removeUser(user: User) {
        usersByUserID.remove(user.userID)
        usersByUsername.remove(user.username)
    }

    fun isUsernameTaken(username: String): Boolean {
        return usersByUsername.containsKey(username)
    }

    fun getUserByUsername(username: String): User? {
        return usersByUsername[username]
    }

    fun getUserById(id: Int): User? {
        return usersByUserID[id]
    }

}

class User(var username: String) {

    var userID = getNextUserID()
    private var passwordHash = ""
    var queue = CommandQueue()
    var ready = false
    var authToken = ""

    constructor(username: String, password: String) : this(username) {
        updatePassword(password)
    }

    fun updatePassword(password: String) {
        this.passwordHash = PasswordStorage.createHash(password)
    }

    fun verifyPassword(password: String): Boolean {
        return PasswordStorage.verifyPassword(password, passwordHash)
    }
}

// Simplification of user class to sendt to client
class ClientUser(val userId: Int, var username: String, val authToken: String) {

}