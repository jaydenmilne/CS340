import commands.*

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * LoginService Handles Lobby functions (joining games, etc)
 */
class LobbyService(private val server: ProxyServer, private val cmdRouter: CommandRouter) {

    private var currentGameId: Int = -1
    private var currentGame: LobbyGameDTO? = null

    init {
        this.cmdRouter.registerCallback(REFRESH_GAME_LIST) { handleRefreshGamesList(it as RefreshGameListCommand) }
    }

    fun joinGame(gameId: Int){
        server.executeCommand(IJoinGameCommand(gameId))
        currentGameId = -1

        while(currentGame == null){
            // poll
        }
        server.executeCommand(IPlayerReadyCommand(currentGameId, true))
    }

    fun leaveGame(){
        if (currentGame == null) return     // currentGameId is invalid
        server.executeCommand(ILeaveGameCommand(currentGameId))
    }

    fun handleRefreshGamesList(refreshGameList: RefreshGameListCommand){
        currentGame = refreshGameList.games.firstOrNull { it.gameId == currentGameId }
    }

}