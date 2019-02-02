package commands

class CreateGameCommand : IServerCommand {
    override val type = "createGame"
    private var name = ""

    override fun execute() {}
}

class GameCreatedCommand : IServerCommand {
    override val type = "createGame"
    private val gameId = ""

    override fun execute() {}
}

class JoinGameCommand : IServerCommand {
    override val type = "joinGame"
    private val gameId = ""

    override fun execute() {}
}

class LeaveGameCommand : IServerCommand {
    override val type = "leaveGame"
    private val gameId = ""

    override fun execute() {}
}

class ListGamesCommand : IServerCommand {
    override val type = "listGames"

    override fun execute() {}
}


class LoginCommand : IServerCommand {
    override val type = "login"
    private val username = ""
    private val password = ""

    override fun execute() {}
}

class PlayerReadyCommand : IServerCommand {
    override val type = "playerReady"
    private val gameId = ""
    private val playerIsReady = false

    override fun execute() {}
}

class RegisterCommand : IServerCommand {
    override val type = "register"
    private val username = ""
    private val password = ""

    override fun execute() {}
}