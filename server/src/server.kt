import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import commands.*
import org.apache.commons.io.IOUtils
import java.io.InputStreamReader
import java.net.HttpURLConnection.HTTP_BAD_REQUEST
import java.net.HttpURLConnection.HTTP_OK
import java.net.InetSocketAddress

const val MAX_CONNECTIONS = 10
const val COMMAND_ENDPOINT = "/command"

fun main(args: Array<String>) {
    var server = HttpServer.create(InetSocketAddress(8080), MAX_CONNECTIONS)

    server.createContext(COMMAND_ENDPOINT) { httpExchange ->
        when (httpExchange.requestMethod) {
            "GET" -> handleGet(httpExchange)
            "POST" -> handlePost(httpExchange)
        }
    }
}

fun handleGet(httpExchange: HttpExchange) {}

fun handlePost(httpExchange: HttpExchange) {
    var requestBody = IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    var initialCommand = Gson().fromJson(requestBody, IServerCommand::class.java)

    var authToken = httpExchange.requestHeaders.getFirst("Authorization")

    var command = when(initialCommand.type) {
        CREATE_GAME -> Gson().fromJson(requestBody, CreateGameCommand::class.java)
        JOIN_GAME -> Gson().fromJson(requestBody, JoinGameCommand::class.java)
        LEAVE_GAME -> Gson().fromJson(requestBody, LeaveGameCommand::class.java)
        LIST_GAMES -> Gson().fromJson(requestBody, ListGamesCommand::class.java)
        LOGIN -> Gson().fromJson(requestBody, LoginCommand::class.java)
        PLAYER_READY -> Gson().fromJson(requestBody, PlayerReadyCommand::class.java)
        REGISTER -> Gson().fromJson(requestBody, RegisterCommand::class.java)
        START_GAME -> Gson().fromJson(requestBody, RegisterCommand::class.java)
        else -> null
    }

    if (command == null) {
        httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
        httpExchange.close()
    } else {
        httpExchange.sendResponseHeaders(HTTP_OK, 0)
        command.execute()

        // render the command queue

        httpExchange.close()
    }
}