package models

object AuthTokens {

    var authTokens = mutableMapOf<String, User>()

    fun isValidAuthToken(authToken: String, user: User): Boolean {
        if (!authTokens.containsKey(authToken))
            return false
        return authTokens.get(authToken)!! == user
    }
}