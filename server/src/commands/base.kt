package commands

import models.User

const val CREATE_GAME = "createGame"
const val GAME_CREATED = "gameCreated"
const val JOIN_GAME = "joinGame"
const val LEAVE_GAME = "leaveGame"
const val LIST_GAMES = "listGames"
const val LOGIN = "login"
const val LOGIN_RESULT = "loginResult"
const val PLAYER_READY = "playerReady"
const val REFRESH_GAME_LIST = "refreshGameList"
const val REGISTER = "register"
const val START_GAME = "startGame"
const val CHANGE_COLOR = "changeColor"
const val UPDATE_PLAYER = "updatePlayer"
const val UPDATE_BANK = "updateBank"
const val REQUEST_DESTINATIONS = "requestDestinations"
const val CHANGE_TURN = "changeTurn"
const val DISCARD_DESTINATIONS = "discardDestinations"
const val SELECT_DESTINATIONS = "selectDestinations"
const val POST_CHAT = "postChat"
const val UPDATE_CHAT = "updateChat"
const val DEAL_CARDS = "dealCards"
const val CLAIM_ROUTE = "claimRoute"
const val ROUTE_CLAIMED = "routeClaimed"
const val DEBUG_HELP = "debugHelp"
const val DRAW_SHARD_CARD = "drawShardCard"
const val UPDATE_HAND = "updateHand"
const val LAST_ROUND = "lastRound"
const val GAME_OVER = "gameOver"

interface ICommand {
    val command: String
}

interface INormalCommand : ICommand {
    override val command: String
}

interface IRegisterCommand : ICommand {
    override val command: String
}

interface IRegisterServerCommand : IRegisterCommand {
    override val command: String
    fun execute(): IRegisterClientCommand
}

interface IRegisterClientCommand : IRegisterCommand {
    override val command: String
}

interface INormalServerCommand : INormalCommand {
    override val command: String
    fun execute(user: User)
}

interface INormalClientCommand : INormalCommand {
    override val command: String
}

class CommandException(message:String): Exception(message)