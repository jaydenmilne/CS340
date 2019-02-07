package commands

class GameCreatedCommand : INormalClientCommand {
    override val type = CREATE_GAME
    var gameId = ""
}

class LoginResultCommand : IRegisterClientCommand {
    override val type = LOGIN_RESULT
    var authToken = ""
    var error = ""
}

class RefreshGameListCommand : INormalClientCommand {
    //unfinished needs GamesList Model Object
    override val type = REFRESH_GAME_LIST
}

class RegisterResultCommand : IRegisterClientCommand {
    override val type = REGISTER_RESULT
    var authToken = ""
    var error = ""
}

class StartGameCommand : INormalClientCommand {
    override val type = START_GAME
    var gameId = ""
}