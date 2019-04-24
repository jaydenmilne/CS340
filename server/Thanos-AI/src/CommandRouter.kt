import com.google.gson.Gson
import commands.*

/**
 * Created by Jordan Gassaway on 4/22/2019.
 * CommandRouter Execute functions for commands
 */
class CommandRouter() {
    private val commandQueue: MutableList<ICommand> = mutableListOf()

    // Callback functions
    private var handleLoginResult: (loginResult: LoginResultCommand) -> Unit = { }
    private var handleGameCreated: (gameCreated: GameCreatedCommand) -> Unit = { }
    private var handleRefreshGameList: (refreshGameList: RefreshGameListCommand) -> Unit = { }
    private var handleStartGame: (startGame: StartGameCommand) -> Unit = { }
    private var handleChangeTurn: (changeTurn: ChangeTurnCommand) -> Unit = { }
    private var handleUpdatePlayer: (updatePlayer: UpdatePlayerCommand) -> Unit = { }
    private var handleUpdateBank: (updateBank: UpdateBankCommand) -> Unit = { }
    private var handleUpdateChat: (updateChat: UpdateChatCommand) -> Unit = { }
    private var handleUpdateHand: (updateHand: UpdateHandCommand) -> Unit = { }
    private var handleDealCards: (dealCards: DealCardsCommand) -> Unit = { }
    private var handleRouteClaimed: (routeClaimed: RouteClaimedCommand) -> Unit = { }
    private var handleLastRound: (lastRound: LastRoundCommand) -> Unit = { }
    private var handleGameOver: (gameOver: GameOverCommand) -> Unit = { }

    public fun addNewCommands(commands: List<ICommand>){
        commandQueue.addAll(commands)
    }

    public fun processCommands(){
        val commandQueueCopy = commandQueue.toList()    // Avoid concurrent modification
        commandQueue.clear()
        commandQueueCopy.forEach{ handleIncomingCommand(it) }
    }

    private fun handleIncomingCommand(command: ICommand){

        when (command.command) {
            LOGIN_RESULT -> handleLoginResult(command as LoginResultCommand)
            GAME_CREATED -> handleGameCreated(command as GameCreatedCommand)
            REFRESH_GAME_LIST -> handleRefreshGameList(command as RefreshGameListCommand)
            START_GAME -> handleStartGame(command as StartGameCommand)
            CHANGE_TURN -> handleChangeTurn(command as ChangeTurnCommand)
            UPDATE_PLAYER -> handleUpdatePlayer(command as UpdatePlayerCommand)
            UPDATE_BANK -> handleUpdateBank(command as UpdateBankCommand)
            UPDATE_CHAT -> handleUpdateChat(command as UpdateChatCommand)
            UPDATE_HAND -> handleUpdateHand(command as UpdateHandCommand)
            DEAL_CARDS -> handleDealCards(command as DealCardsCommand)
            ROUTE_CLAIMED -> handleRouteClaimed(command as RouteClaimedCommand)
            LAST_ROUND -> handleLastRound(command as LastRoundCommand)
            GAME_OVER -> handleGameOver(command as GameOverCommand)
        }
    }

    public fun registerCallback(command: String, callback: (cmd: ICommand) -> Unit){

        when (command) {
            LOGIN_RESULT -> handleLoginResult = callback
            GAME_CREATED -> handleGameCreated = callback
            REFRESH_GAME_LIST -> handleRefreshGameList = callback
            START_GAME -> handleStartGame = callback
            CHANGE_TURN -> handleChangeTurn = callback
            UPDATE_PLAYER -> handleUpdatePlayer = callback
            UPDATE_BANK -> handleUpdateBank = callback
            UPDATE_CHAT -> handleUpdateChat = callback
            UPDATE_HAND -> handleUpdateHand = callback
            DEAL_CARDS -> handleDealCards = callback
            ROUTE_CLAIMED -> handleRouteClaimed = callback
            LAST_ROUND -> handleLastRound = callback
            GAME_OVER -> handleGameOver = callback
        }
    }
}