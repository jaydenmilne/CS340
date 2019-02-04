package models

private var nextUserId = -1

fun getNextUserID(): Int {
    nextUserId++
    return nextUserId
}

object Users {
    private var users = mutableMapOf<Int, User>()

    // Autoincrement
    private var i = 0

    fun addUser(user: User) {
        users.put(user.userID, user)
    }

    fun removeUser(user: User) {
        users.remove(user.userID)
    }
}

class User(var username: String) {

    var userID = getNextUserID()
    private var passwordHash = ""
    var queue = CommandQueue()

    fun updatePassword(password: String) {
        this.passwordHash = PasswordStorage.createHash(password)
    }

    fun verifyPassword(password: String): Boolean {
        return PasswordStorage.verifyPassword(password, passwordHash)
    }
}