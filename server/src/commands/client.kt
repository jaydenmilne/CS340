package commands

class GameCreatedCommand : INormalClientCommand {
    override val type = CREATE_GAME
    private val gameId = ""
}

class LoginResultCommand : IRegisterClientCommand {
    override val type = LOGIN_RESULT
    private val authToken = ""
    private val error = ""
}

class RefreshGameListCommand : INormalClientCommand {
    //unfinished needs GamesList Model Object
    override val type = REFRESH_GAME_LIST
}

class RegisterResultCommand : IRegisterClientCommand {
    override val type = REGISTER_RESULT
    private val authToken = ""
    private val error = ""
}

class StartGameCommand : INormalClientCommand {
    override val type = START_GAME
    private val gameId = ""
}