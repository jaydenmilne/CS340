package commands

import models.*

class CreateGameCommand : INormalServerCommand {
    override val command = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {
        var newGame = Game(name)
        Games.games[newGame.gameID] = newGame

        // If the user is in another game, take them out.
        Games.removeUserFromGames(user)
        // Add player to this new game's list of players
        newGame.players.add(user)
        user.ready = false

        // Notify the client that we created the game
        user.queue.push(GameCreatedCommand(newGame.gameID))
    }
}

class JoinGameCommand : INormalServerCommand {
    override val command = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {
        // Ensure that the user is only in one game
        Games.removeUserFromGames(user)
        // Add the user to the new game
        if (!Games.games.containsKey(gameId.toInt())) {
            // game does not exist
            throw CommandException("JoinGameCommand: Game does not exist")
        }

        if(Games.games[gameId.toInt()]!!.players.size < 5) {
            Games.games[gameId.toInt()]!!.players.add(user)
            user.ready = false
        }
    }
}

class LeaveGameCommand : INormalServerCommand {
    override val command = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {
        // Add the user to the new game
        if (!Games.games.containsKey(gameId.toInt())) {
            // game does not exist
            throw CommandException("LeaveGameCommand: Game does not exist")
        }
        Games.games[gameId.toInt()]!!.players.remove(user)
        user.ready = false
    }
}

class ListGamesCommand : INormalServerCommand {
    override val command = LIST_GAMES

    override fun execute(user: User) {
        var newCommand = RefreshGameListCommand()
        user.queue.push(newCommand)
    }
}


class LoginCommand : IRegisterServerCommand {
    override val command = LOGIN
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
    override val command = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute(user: User) {
        user.ready = playerIsReady
    }
}

class RegisterCommand : IRegisterServerCommand {
    override val command = REGISTER
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

class ServerCommandData : IRegisterCommand, INormalCommand {
    override val command: String = ""
}
