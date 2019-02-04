package models

import java.math.BigInteger
import java.security.SecureRandom

object AuthTokens {

    private var authTokens = mutableMapOf<String, User>()

    fun verifyToken(authToken: String, user: User): Boolean {
        if (!authTokens.containsKey(authToken))
            return false
        return authTokens.get(authToken) == user
    }

    fun getUser(authToken: String): User? {
        return authTokens.get(authToken)
    }

    fun makeAuthTokenForUser(user: User): String {
        var random = SecureRandom()
        var bytes = ByteArray(256 / 8)

        random.nextBytes(bytes)

        var token = BigInteger(bytes).toString(16)

        authTokens.put(token, user)

        return token
    }
}