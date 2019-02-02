package commands

class GameCreatedCommand : IClientCommand {
    override val type = CREATE_GAME
    private val gameId = ""

    override fun execute() {}
}

class LoginResultCommand : IClientCommand {
    override val type = LOGIN_RESULT
    private val authToken = ""
    private val error = ""

    override fun execute() {}
}

class RefreshGameListCommand : IClientCommand {
    //unfinished needs GamesList Model Object
    override val type = REFRESH_GAME_LIST

    override fun execute() {}
}

class RegisterResultCommand : IClientCommand {
    override val type = REGISTER_RESULT
    private val authToken = ""
    private val error = ""

    override fun execute() {}
}

class StartGameCommand : IClientCommand {
    override val type = START_GAME
    private val gameId = ""

    override fun execute() {}
}