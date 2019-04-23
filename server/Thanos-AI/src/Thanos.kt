import commands.*

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

    fun login(){
        loginService.login(username, password)
    }

    fun register(){
        loginService.register(username, password)
    }

    fun joinGame(gameId: Int){
        lobbyService.joinGame(gameId)
    }
}
