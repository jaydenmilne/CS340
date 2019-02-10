package commands

import models.User

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
    fun execute() : IRegisterClientCommand
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

class CommandException: Exception()