package commands

import models.Games
import models.User

/**
 * A command used to represent chat messages.  The server just acts like a dumb network hub and broadcasts to all users
 * in that game via their CommandQueues.
 */
class PostChatCommand : INormalClientCommand, INormalServerCommand {
    override val command = "postChat"
    private val gameId = ""
    private val playerId = ""
    private val playerName = ""
    private val message = ""

    override fun execute(user: User) {
        val game = Games.games[gameId.toInt()]

        if (game == null) {
            throw CommandException("The game you're posting a chat message to doesn't exist.")
        }

        game.players.forEach { user:User -> user.queue.push(this) }
    }
}