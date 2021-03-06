package models

import java.math.BigInteger
import java.security.SecureRandom

/**
 * A singleton that handles authTokens throughout the server.
 *
 * Contains a map of authTokens (Strings) to Users.  Also contains helper functions to verify a particular authToken is
 * tied to a particular user, to get the user represented by an authToken, and to create new authTokens.
 */
object AuthTokens {

    private var authTokens = mutableMapOf<String, User>()

    fun verifyToken(authToken: String, user: User): Boolean {
        if (!authTokens.containsKey(authToken))
            return false
        return authTokens[authToken] == user
    }

    fun getUser(authToken: String): User? {
        return authTokens[authToken]
    }

    fun makeAuthTokenForUser(user: User): String {
        val random = SecureRandom()
        val bytes = ByteArray(256 / 8)

        random.nextBytes(bytes)

        val token = BigInteger(bytes).toString(16)

        authTokens[token] = user
        user.authToken = token

        return token
    }

    fun loadToken(token: String, user: User) {
        authTokens[token] = user
    }
}