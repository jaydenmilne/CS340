package commands

import models.ClientUser
import models.Games

class GameCreatedCommand(gameId: Int) : INormalClientCommand {
    override val command = GAME_CREATED
}

class LoginResultCommand : IRegisterClientCommand {
    override val command = LOGIN_RESULT
    var user = ClientUser()
    var error = ""
}

class RefreshGameListCommand : INormalClientCommand {
    var games = Games.games.values
    override val command = REFRESH_GAME_LIST
}

class StartGameCommand : INormalClientCommand {
    override val command = START_GAME
    var gameId = ""
}