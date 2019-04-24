import commands.IListGamesCommand

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * Poller Periodically polls server for new commands
 */
class Poller(private val cmdRouter: CommandRouter, private val server: ProxyServer) {
    private val pollThread = PollThread { poll() }
    private val debugMode = false

    init {
        pollThread.start()
    }

    fun startPolling(){
        pollThread.enabled = true
    }

    fun stopPolling(){
        pollThread.enabled = false
    }

    fun setPeriod(period: Long){
        pollThread.period = period
    }

    private fun poll(){
        server.executeCommand(IListGamesCommand())
        cmdRouter.processCommands()
    }

    fun close() {
        pollThread.exit = true
    }
}

private class PollThread(private val pollFunc: () -> Unit): Thread("Polling Thread") {
    var enabled = false
    var period:Long = 500
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