package commands

class GameCreatedCommand : IClientCommand {
    override val type = CREATE_GAME
    private val gameId = ""
}

class LoginResultCommand : IClientCommand {
    override val type = LOGIN_RESULT
    private val authToken = ""
    private val error = ""
}

class RefreshGameListCommand : IClientCommand {
    //unfinished needs GamesList Model Object
    override val type = REFRESH_GAME_LIST
}

class RegisterResultCommand : IClientCommand {
    override val type = REGISTER_RESULT
    private val authToken = ""
    private val error = ""
}

class StartGameCommand : IClientCommand {
    override val type = START_GAME
    private val gameId = ""
}