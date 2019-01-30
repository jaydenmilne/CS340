package models

object Users {
    var users = mutableMapOf<Int, User>()

    fun addUser(user: User) {
        users.put(user.userid, user)
    }

    fun removeUser(user: User) {
        users.remove(user.userid)
    }
}

class User(var userid: Int, var username: String, var passwordHash: String) {
    fun isValidPassword(password: String) {
        // TODO
    }
}