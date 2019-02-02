package commands

class CreateGameCommand : IServerCommand {
    override val type = CREATE_GAME
    private var name = ""

    override fun execute() {}
}

class JoinGameCommand : IServerCommand {
    override val type = JOIN_GAME
    private val gameId = ""

    override fun execute() {}
}

class LeaveGameCommand : IServerCommand {
    override val type = LEAVE_GAME
    private val gameId = ""

    override fun execute() {}
}

class ListGamesCommand : IServerCommand {
    override val type = LIST_GAMES

    override fun execute() {}
}


class LoginCommand : IServerCommand {
    override val type = LOGIN
    private val username = ""
    private val password = ""

    override fun execute() {}
}

class PlayerReadyCommand : IServerCommand {
    override val type = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute() {}
}

class RegisterCommand : IServerCommand {
    override val type = REGISTER
    private val username = ""
    private val password = ""

    override fun execute() {}
}