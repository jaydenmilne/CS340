package commands

import models.AuthTokens
import models.User
import models.Users

class CreateGameCommand : INormalServerCommand {
    override val type = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {}
}

class JoinGameCommand : INormalServerCommand {
    override val type = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {}
}

class LeaveGameCommand : INormalServerCommand {
    override val type = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {}
}

class ListGamesCommand : INormalServerCommand {
    override val type = LIST_GAMES

    override fun execute(user: User) {}
}


class LoginCommand : IRegisterServerCommand {
    override val type = LOGIN
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {
        var response = RegisterResultCommand()

        var user = Users.getUserByUsername(username)

        if (user == null) {
            response.error = "Authentication error."
            return response
        }

        if (user.verifyPassword(password)) {
            response.authToken = AuthTokens.makeAuthTokenForUser(user)
            return response
        } else {
            response.error = "Authentication error."
            return response
        }
    }
}

class PlayerReadyCommand : INormalServerCommand {
    override val type = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute(user: User) {}
}

class RegisterCommand : IRegisterServerCommand {
    override val type = REGISTER
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {
        var response = LoginResultCommand()

        if (Users.isUsernameTaken(username)) {
            response.error = "Username already taken."
            return response
        }

        var newUser = User(username, password)

        Users.addUser(newUser)
        var token = AuthTokens.makeAuthTokenForUser(newUser)


        response.authToken = token

        return response
    }
}