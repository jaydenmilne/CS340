import commands.*

/**
 * Created by Jordan Gassaway on 4/22/2019.
 * Thanos - Self contained AI that can play the ticket to ride game
 */
class Thanos {
    private val server = ProxyServer()
    private val commandRouter = CommandRouter()
    private val commandQueue: MutableList<ICommand> = mutableListOf<ICommand>()
    private var user: LoginUserDTO? = null

    fun login(username: String, password: String){
        commandQueue.addAll(server.executeCommand(ILoginCommand(username, password)))
    }

    fun register(username: String, password: String){
        commandQueue.addAll(server.executeCommand(IRegisterCommand(username, password)))
    }

    fun processCommands(){
        this.commandQueue.forEach{ handleIncomingCommand(it) }
        this.commandQueue.clear()
    }

    private fun handleIncomingCommand(command: ICommand){

        when (command.command) {
            LOGIN_RESULT -> handleLoginResult(command as LoginResultCommand)
            GAME_CREATED -> println(command.command)
            REFRESH_GAME_LIST -> println(command.command)
            START_GAME -> println(command.command)
            UPDATE_PLAYER -> println(command.command)
            CHANGE_TURN -> println(command.command)
            UPDATE_BANK -> println(command.command)
            UPDATE_CHAT -> println(command.command)
            DEAL_CARDS -> println(command.command)
            UPDATE_HAND -> println(command.command)
            ROUTE_CLAIMED -> println(command.command)
            LAST_ROUND -> println(command.command)
            GAME_OVER -> println(command.command)
        }
    }

    private fun handleLoginResult(command: LoginResultCommand){
        server.handleLoginResult(command)
        if (command.error == ""){
            user = command.user
        }
    }
}
