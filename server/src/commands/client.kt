package commands

import models.*

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
    var gamePlayer = GamePlayer(0, "", Color.YELLOW, false, 0, 0, 0, 0, false, 0 )
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
    var faceUpCards = listOf<ShardCard>()
    var shardDrawPileSize = 0
    var shardDiscardPileSize = 0
    var destinationPileSize = 0
}


class UpdateChatCommand(val message: Message) : INormalClientCommand {
    override val command = UPDATE_CHAT
}


class DealCardsCommand : INormalClientCommand {
    override val command = DEAL_CARDS
    var destinations = mutableListOf<DestinationCard>()
    var shardCards = mutableListOf<ShardCard>()
    var minDestinations = 2
}

class RouteClaimedCommand : INormalClientCommand {
    override val command = ROUTE_CLAIMED
    var userId = 0
    var routeId = ""
}