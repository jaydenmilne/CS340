package commands

import models.Games
import models.Message
import models.User

/**
 * A command used to represent chat messages.  The server just acts like a dumb network hub and broadcasts to all users
 * in that game via their CommandQueues.
 */
class PostChatCommand : INormalServerCommand {
    override val command = "postChat"
    private val message = ""

    override fun execute(user: User) {
        val gameId = Games.getGameIdForPlayer(user)

        val game = Games.games[gameId]

        if (game == null) {
            throw CommandException("The game you're posting a chat message to doesn't exist.")
        }

        val messageObject = Message(user.userId, user.username, message, game.getNextMessageId())
        val updateChatCommand = UpdateChatCommand(messageObject)

        game.broadcast(updateChatCommand)
    }
}