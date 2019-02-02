package commands

const val CREATE_GAME = "createGame"
const val JOIN_GAME = "joinGame"
const val LEAVE_GAME = "leaveGame"
const val LIST_GAMES = "listGames"
const val LOGIN = "login"
const val LOGIN_RESULT = "loginResult"
const val PLAYER_READY = "playerReady"
const val REFRESH_GAME_LIST = "refreshGameList"
const val REGISTER = "register"
const val REGISTER_RESULT = "registerResult"
const val START_GAME = "startGame"

interface ICommand {
    val type: String
}

interface IServerCommand : ICommand {
    override val type: String
    fun execute()
}

interface IClientCommand : ICommand {
    override val type: String
}