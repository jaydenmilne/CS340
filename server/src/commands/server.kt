package commands

import models.*

class CreateGameCommand : INormalServerCommand {
    override val type = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {
        var newGame = Game(name)
        Games.games.put(newGame.gameID, newGame)
    }
}

class JoinGameCommand : INormalServerCommand {
    override val type = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {
        if(Games.games[gameId.toInt()]!!.players.size < 5) {
            Games.games[gameId.toInt()]!!.players.add(user)
            user.ready = false
        }
    }
}

class LeaveGameCommand : INormalServerCommand {
    override val type = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {
        Games.games[gameId.toInt()]!!.players.remove(user)
        user.ready = false
    }
}

class ListGamesCommand : INormalServerCommand {
    override val type = LIST_GAMES

    override fun execute(user: User) {
        var newCommand = RefreshGameListCommand()
        user.queue.push(newCommand)
    }
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

    override fun execute(user: User) {
        user.ready = playerIsReady
    }
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