package commands

class PostChatCommand : IClientCommand, IServerCommand {
    override val type = "postChat"
    private val gameId = ""
    private val playerId = ""
    private val playerName = ""
    private val message = ""

    override fun execute() {}
}