package commands

import models.User

class CreateGameCommand : INormalServerCommand {
    override val type = CREATE_GAME
    private var name = ""

    override fun execute(user: User) {}
}

class JoinGameCommand : INormalServerCommand {
    override val type = JOIN_GAME
    private val gameId = ""

    override fun execute(user: User) {}
}

class LeaveGameCommand : INormalServerCommand {
    override val type = LEAVE_GAME
    private val gameId = ""

    override fun execute(user: User) {}
}

class ListGamesCommand : INormalServerCommand {
    override val type = LIST_GAMES

    override fun execute(user: User) {}
}


class LoginCommand : IRegisterServerCommand {
    override val type = LOGIN
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {}
}

class PlayerReadyCommand : INormalServerCommand {
    override val type = PLAYER_READY
    private val gameId = ""
    private val playerIsReady = false

    override fun execute(user: User) {}
}

class RegisterCommand : IRegisterServerCommand {
    override val type = REGISTER
    private val username = ""
    private val password = ""

    override fun execute(): IRegisterClientCommand {}
}