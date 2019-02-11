package commands

import models.*

/**
 * Create a new game on the server.
 */
class CreateGameCommand : INormalServerCommand {
    override val command = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {
        var newGame = Game(name)
        Games.games[newGame.gameId] = newGame

        // If the user is in another game, take them out.
        Games.removeUserFromGames(user)

        // Add player to this new game's list of players
        newGame.players.add(user)
        user.ready = false

        // Notify the client that we created the game
        user.queue.push(GameCreatedCommand(newGame.gameId))
    }
}

/**
 * Join a game that has not yet started.
 */
class JoinGameCommand : INormalServerCommand {
    override val command = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {
        // Ensure that the user is only in one game
        Games.removeUserFromGames(user)

        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("JoinGameCommand: Game does not exist")
        }

        if (game.players.size < 5) {
            game.players.add(user)
            user.color = Color.values()[game.players.size - 1]
            user.ready = false
        } else {
            throw CommandException("Game Full")
        }
    }
}

/**
 * Leave a game that has not yet started.
 */
class LeaveGameCommand : INormalServerCommand {
    override val command = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {
        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("LeaveGameCommand: Game does not exist")
        }

        game.players.remove(user)
        user.ready = false
    }
}

/**
 * List all available games to play.
 */
class ListGamesCommand : INormalServerCommand {
    override val command = LIST_GAMES

    override fun execute(user: User) {
        var newCommand = RefreshGameListCommand()
        user.queue.push(newCommand)
    }
}

/**
 * Log in to the server with the provided credentials.
 */
class LoginCommand : IRegisterServerCommand {
    override val command = LOGIN
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {
        var response = LoginResultCommand()

        var user = Users.getUserByUsername(username)

        if (user == null) {
            response.error = "Authentication error."
            return response
        }

        if (user.verifyPassword(password)) {
            val authToken = AuthTokens.makeAuthTokenForUser(user)
            response.user = ClientUser(user.userId, user.username, authToken)
            return response
        } else {
            response.error = "Authentication error."
            return response
        }
    }
}

/**
 * Change a user's ready status for a particular game.
 */
class PlayerReadyCommand : INormalServerCommand {
    override val command = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute(user: User) {
        user.ready = playerIsReady

        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("That game doesn't exist.")
        }

        val notReadyPlayers = game.players.filter { u -> !u.ready }

        if (notReadyPlayers.isEmpty() && game.players.size >= 2) {
            game.broadcast(StartGameCommand())
        }
    }
}

/**
 * Register a new user with the server.
 */
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
        newUser.authToken = token

        response.user = ClientUser(newUser.userId, newUser.username, newUser.authToken)

        return response
    }
}

/**
 * Change a user's color for a particular game.
 */
class ChangeColorCommand : INormalServerCommand {
    override val command = CHANGE_COLOR
    private val gameId = ""
    private var newColor = ""

    override fun execute(user: User) {
        val game = Games.games[gameId.toInt()]

        // Verify that the game exists
        if (game == null) {
            throw CommandException("ChangeColorCommand: Game does not exist")
        }

        // Verify that the user is in the game
        if (!game.players.contains(user)) {
            throw CommandException("ChangeColorCommand: User is not in this game")
        }

        enumValues<Color>().forEach { if (it.rgb.equals(newColor)) user.color = it }
    }
}

/**
 * TODO: Document
 */
class ServerCommandData : IRegisterCommand, INormalCommand {
    override val command: String = ""
}
