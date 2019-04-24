import services.GameService
import services.LobbyService
import services.LoginService

/**
 * Created by Jordan Gassaway on 4/22/2019.
 * Thanos - Self contained AI that can play the ticket to ride game
 */
class Thanos (private val username: String, private val password: String){
    private val commandRouter = CommandRouter()
    private val server = ProxyServer(commandRouter)

    fun processCommands(){
        commandRouter.processCommands()
    }

    // Services
    private val loginService = LoginService(server, commandRouter)
    private val lobbyService = LobbyService(server, commandRouter)
    private val gameService = GameService(server, commandRouter)
    private val poller = Poller(commandRouter, server)

    fun login(){
        loginService.login(username, password)
        commandRouter.processCommands()
    }

    fun register(){
        loginService.register(username, password)
        commandRouter.processCommands()
    }

    fun joinGame(gameId: Int){
        if(!loginService.isLoggedIn()) return
        poller.startPolling()
        lobbyService.joinGame(gameId)
    }

    fun close(){
        poller.close()
    }
}
