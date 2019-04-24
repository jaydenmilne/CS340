package services

import CommandRouter
import LobbyGameDTO
import ProxyServer
import commands.*

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.LobbyService Handles Lobby functions (joining games, etc)
 */
class LobbyService(private val server: ProxyServer, private val cmdRouter: CommandRouter) {
    private var currentGameId: Int = -1
    @Volatile private var currentGame: LobbyGameDTO? = null

    init {
        this.cmdRouter.registerCallback(REFRESH_GAME_LIST) { handleRefreshGamesList(it as RefreshGameListCommand) }
    }

    fun joinGame(gameId: Int){
        server.executeCommand(IJoinGameCommand(gameId))
        currentGameId = gameId

        while(currentGame == null){
            // poll
        }
        server.executeCommand(IPlayerReadyCommand(currentGameId, true))
        println("Successfully joined game $gameId")
    }

    fun rejoinGame(){
        server.executeCommand(IRejoinGameCommand())
        println("Successfully rejoined game")
    }

    fun leaveGame(){
        if (currentGame == null) return     // currentGameId is invalid
        server.executeCommand(ILeaveGameCommand(currentGameId))
    }

    fun handleRefreshGamesList(refreshGameList: RefreshGameListCommand){
        currentGame = refreshGameList.games.firstOrNull { it.gameId == currentGameId }
    }


    fun getCurrentGame(): LobbyGameDTO? {
        return currentGame
    }
}