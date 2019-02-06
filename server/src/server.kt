import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import commands.*
import models.AuthTokens
import org.apache.commons.io.IOUtils
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection.*
import java.net.InetSocketAddress

const val MAX_CONNECTIONS = 10
const val REGISTRATION_ENDPOINT = "/register"
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

fun handleGet(httpExchange: HttpExchange) {
    var authToken = httpExchange.requestHeaders.getFirst("Authorization")
    var user = AuthTokens.getUser(authToken)

    httpExchange.sendResponseHeaders(HTTP_OK, 0)

    if (user == null) {
        httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
        httpExchange.close()
        return
    }

    var writer = OutputStreamWriter(httpExchange.responseBody)

    writer.write(user.queue.pollCommands())
    httpExchange.close()
}

fun handlePost(httpExchange: HttpExchange) {
    var requestBody = IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    var initialCommand = Gson().fromJson(requestBody, IServerCommand::class.java)

    var authToken = httpExchange.requestHeaders.getFirst("Authorization")
    var user = AuthTokens.getUser(authToken)

    if (user == null) {
        httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
        httpExchange.close()
        return
    }

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
        var writer = OutputStreamWriter(httpExchange.responseBody)

        command.execute(user)

        writer.write(user.queue.pollCommands())
        httpExchange.close()
    }
}