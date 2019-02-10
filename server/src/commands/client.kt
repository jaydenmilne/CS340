package commands

import models.Games

class GameCreatedCommand : INormalClientCommand {
    override val command = CREATE_GAME
    var gameId = ""
}

class LoginResultCommand : IRegisterClientCommand {
    override val command = LOGIN_RESULT
    var authToken = ""
    var error = ""
}

class RefreshGameListCommand : INormalClientCommand {
    var games = Games.games
    override val command = REFRESH_GAME_LIST
}

class RegisterResultCommand : IRegisterClientCommand {
    override val command = REGISTER_RESULT
    var authToken = ""
    var error = ""
}

class StartGameCommand : INormalClientCommand {
    override val command = START_GAME
    var gameId = ""
}