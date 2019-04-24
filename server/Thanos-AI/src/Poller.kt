import commands.IListGamesCommand

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * Poller Periodically polls server for new commands
 */
class Poller(private val cmdRouter: ICommandRouter, private val server: ProxyServer) {
    private val pollThread = PollThread { poll() }
    private val debugMode = false
    var lobbyMode = false

    init {
        pollThread.start()
    }

    fun startPolling(lobbyMode:Boolean = false){
        this.lobbyMode = lobbyMode
        pollThread.enabled = true
    }

    fun stopPolling(){
        pollThread.enabled = false
    }

    fun setPeriod(period: Long){
        pollThread.period = period
    }

    private fun poll(){
        if(lobbyMode){
            server.executeCommand(IListGamesCommand())
        } else {
            server.poll()
        }
        cmdRouter.processCommands()
    }

    fun close() {
        pollThread.exit = true
    }
}

private class PollThread(private val pollFunc: () -> Unit): Thread("Polling Thread") {
    var enabled = false
    var period:Long = 300
    var exit = false
    private val debugMode = false

    override fun run() {
        super.run()
        while(!exit){
            if (enabled){
                if(debugMode) println("polling...")
                pollFunc()
            }
            sleep(period)
        }
    }
}