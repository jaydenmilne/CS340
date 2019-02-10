package models

import java.math.BigInteger
import java.security.SecureRandom

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

        return token
    }
}