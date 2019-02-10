package commands

import models.Games
import models.User

class PostChatCommand : INormalClientCommand, INormalServerCommand {
    override val command = "postChat"
    private val gameId = ""
    private val playerId = ""
    private val playerName = ""
    private val message = ""

    override fun execute(user: User) {
        Games.games[gameId.toInt()]!!.players.forEach { user:User -> user.queue.push(this) }
    }
}