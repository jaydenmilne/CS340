import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import commands.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

/**
 * Server.ProxyServer: Runs on Client to provide interface with server.
 * Communicates with Server over HTTP, Decodes and Encodes JSON
 * data to/from data objects. Interprets results of remote
 * operations.
 * @author Jordan Gassaway
 * @since 2/15/17
 */
class ProxyServer(private val cmdRouter: CommandRouter, private val url: String = "http://localhost",  private val port: Int = 4300) {
    /** AuthToken for current session */
    private var session: String? = null
    private val gson = Gson()
    private val serverURL = "$url:$port"


    public fun executeCommand(command: ICommand) {
        try {
            val type = when (command.command) {
                LOGIN -> RequestType.REGISTER
                REGISTER -> RequestType.REGISTER
                LIST_GAMES -> RequestType.POLL
                else -> RequestType.COMMAND
            }

            val sendURL = URL(serverURL + type.endpoint)

            val con = sendURL.openConnection() as HttpURLConnection


            con.doOutput = true
            con.requestMethod = type.method
            con.addRequestProperty("Content-Type", "application/json")
            if(session != null){
                con.addRequestProperty("Authorization", session)
            }

            con.connect()

            val output = con.outputStream
            val jsonSend = gson.toJson(command)
            output.write(jsonSend.toByteArray(charset("UTF-8")))
            output.close()

            println("Sending Request to $sendURL")

            when (con.responseCode){
                HttpURLConnection.HTTP_UNAUTHORIZED -> throw UnauthorizedException(con.responseMessage)
                HttpURLConnection.HTTP_INTERNAL_ERROR -> throw InternalServerException(con.responseMessage)
            }


            // HTTP OK
            val responseBody = getResponse(con)
            val commands = deserializeCommands(responseBody)
            cmdRouter.addNewCommands(commands)

        } catch (e: IOException) {
            e.printStackTrace()
        }

    }


    @Throws(IOException::class)
    fun getResponse(con: HttpURLConnection): String {
        val inputStream = BufferedReader(InputStreamReader(con.inputStream))
        var inputLine: String?
        val response = StringBuffer()

        inputLine = inputStream.readLine()
        while (inputLine != null) {
            response.append(inputLine)
            inputLine = inputStream.readLine()
        }
        inputStream.close()
        return response.toString()
    }

    private fun deserializeCommands(responseBody: String): List<ICommand>{
        val commandsList = mutableListOf<ICommand>()
        val jsonCommands = gson.fromJson(responseBody, CommandQueue::class.java)

        jsonCommands.commands.forEach{
            val cmdString = gson.toJson(it)
            val initialCommand = gson.fromJson<ClientCommandData>(cmdString, ClientCommandData::class.java)
            val command = when (initialCommand.command) {
                LOGIN_RESULT -> gson.fromJson(cmdString, LoginResultCommand::class.java)
                GAME_CREATED -> gson.fromJson(cmdString, GameCreatedCommand::class.java)
                REFRESH_GAME_LIST -> gson.fromJson(cmdString, RefreshGameListCommand::class.java)
                START_GAME -> gson.fromJson(cmdString, StartGameCommand::class.java)
                UPDATE_PLAYER -> gson.fromJson(cmdString, UpdatePlayerCommand::class.java)
                CHANGE_TURN -> gson.fromJson(cmdString, ChangeTurnCommand::class.java)
                UPDATE_BANK -> gson.fromJson(cmdString, UpdateBankCommand::class.java)
                UPDATE_CHAT -> gson.fromJson(cmdString, UpdateChatCommand::class.java)
                DEAL_CARDS -> gson.fromJson(cmdString, DealCardsCommand::class.java)
                UPDATE_HAND -> gson.fromJson(cmdString, UpdateHandCommand::class.java)
                ROUTE_CLAIMED -> gson.fromJson(cmdString, RouteClaimedCommand::class.java)
                LAST_ROUND -> gson.fromJson(cmdString, LastRoundCommand::class.java)
                GAME_OVER -> gson.fromJson(cmdString, GameOverCommand::class.java)
                else -> null
            }

            commandsList.add(command!!)
        }


        return commandsList
    }

    fun setAuthToken(authToken: String?){
        session = authToken
    }

    private enum class RequestType private constructor(val endpoint: String, val method: String) {
        REGISTER("/register", "POST"),
        COMMAND("/command", "POST"),
        POLL("/command", "GET")
    }

}

class InternalServerException(msg: String) : Exception(msg)
class UnauthorizedException(msg: String) : Exception(msg)

class CommandQueue(){
    val commands: List<Any> = listOf()
}
