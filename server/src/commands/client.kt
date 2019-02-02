package commands

class LoginResultCommand : IClientCommand {
    override val type = "loginResult"
    private val authToken = ""
    private val error = ""

    override fun execute() {}
}

class RefreshGameListCommand : IClientCommand {
    //unfinished needs GamesList Model Object
    override val type = "refreshGameList"

    override fun execute() {}
}

class RegisterResultCommand : IClientCommand {
    override val type = "registerResult"
    private val authToken = ""
    private val error = ""

    override fun execute() {}
}

class StartGameCommand : IClientCommand {
    override val type = "startGame"
    private val gameId = ""

    override fun execute() {}
}