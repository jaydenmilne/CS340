package commands

import models.User

class PostChatCommand : INormalClientCommand, INormalServerCommand {
    override val type = "postChat"
    private val gameId = ""
    private val playerId = ""
    private val playerName = ""
    private val message = ""

    override fun execute(user: User) {}
}