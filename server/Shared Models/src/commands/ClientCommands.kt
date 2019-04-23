package commands

import Color
import GamePlayerDTO
import IShardCard
import IDestinationCard
import LoginUserDTO
import LobbyGameDTO
import PlayerPoints
import Message

class GameCreatedCommand(val gameId: Int) : INormalClientCommand {
    override val command = GAME_CREATED
}

class LoginResultCommand(var error: String = "", var game: Int = -1, var user: LoginUserDTO = LoginUserDTO()) : IRegistrationClientCommand {
    override val command = LOGIN_RESULT
}

class RefreshGameListCommand(val games: List<LobbyGameDTO>) : INormalClientCommand {
    override val command = REFRESH_GAME_LIST
}

class StartGameCommand(val gameId: String) : INormalClientCommand {
    override val command = START_GAME
}

class UpdatePlayerCommand : INormalClientCommand {
    override val command = UPDATE_PLAYER

    constructor()

    constructor (player: GamePlayerDTO) {
        gamePlayer = player
    }

    var gamePlayer = GamePlayerDTO(0, "", Color.YELLOW, false, 0, 0, 0, 0, false, 0, 0)
}

class ChangeTurnCommand(val userId: Int = 0) : INormalClientCommand {
    override val command = CHANGE_TURN
}

class UpdateBankCommand(val faceUpCards: List<IShardCard>,
                        val shardDrawPileSize: Int,
                        val shardDiscardPileSize: Int,
                        val destinationPileSize: Int) : INormalClientCommand {
    override val command = UPDATE_BANK
}


class UpdateChatCommand(val message: Message) : INormalClientCommand {
    override val command = UPDATE_CHAT
}

class DealCardsCommand(val destinations: List<IDestinationCard>,
                       val shardCards: List<IShardCard>,
                       val minDestinations: Int = 0) : INormalClientCommand {
    override val command = DEAL_CARDS
}

class UpdateHandCommand(val destinations: List<IDestinationCard>,
                        val shardCards: List<IShardCard>) : INormalClientCommand {
    override val command = UPDATE_HAND
}

class RouteClaimedCommand(val userId: Int, val routeId: String) : INormalClientCommand {
    override val command = ROUTE_CLAIMED
}

class LastRoundCommand : INormalClientCommand {
    override val command = LAST_ROUND
}

class GameOverCommand(val players: List<PlayerPoints>) : INormalClientCommand {
    override val command = GAME_OVER
}
