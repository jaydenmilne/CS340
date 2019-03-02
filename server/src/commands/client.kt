package commands

import models.ClientUser
import models.DestinationCard
import models.Games
import models.User

class GameCreatedCommand(val gameId: Int) : INormalClientCommand {
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

class StartGameCommand(var gameId: String) : INormalClientCommand {
    override val command = START_GAME
}


class UpdatePlayerCommand : INormalClientCommand {
    override val command = UPDATE_PLAYER
    var user = ClientUser()
}

class ChangeTurnCommand : INormalClientCommand {
    override val command = CHANGE_TURN
    var userId = 0

}

class SelectDestinationsCommand : INormalClientCommand {
    override val command = SELECT_DESTINATIONS
    var destinations = mutableListOf<DestinationCard>()
}

class UpdateBankCommand : INormalClientCommand {
    override val command = UPDATE_BANK

    //TODO
}
