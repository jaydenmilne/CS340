import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import commands.*
import models.AuthTokens
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection.*
import java.net.InetSocketAddress

const val MAX_CONNECTIONS = 10
const val REGISTRATION_ENDPOINT = "/register"
const val COMMAND_ENDPOINT = "/command"

fun main(args: Array<String>) {
    val port = 4300
    var server = HttpServer.create(InetSocketAddress(port), MAX_CONNECTIONS)

    println("Registering endpoints...")
    server.createContext(COMMAND_ENDPOINT) { httpExchange ->
        when (httpExchange.requestMethod) {
            "GET" -> handleGet(httpExchange)
            "POST" -> handlePost(httpExchange)
        }
    }

    server.createContext(REGISTRATION_ENDPOINT) { httpExchange ->
        when (httpExchange.requestMethod) {
            "POST" -> handleRegistrationPost(httpExchange)
        }
    }
    println("Hold on to your butts...")
    server.start()
    println("Server started on port " + port)
}

fun handleRegistrationPost(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*");
    var requestBody = org.apache.commons.io.IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    println("POST /register")
    println(requestBody)

    try {
        var initialCommand = Gson().fromJson(requestBody, IRegisterServerCommand::class.java)

        var command = when(initialCommand.type) {
            LOGIN -> Gson().fromJson(requestBody, LoginCommand::class.java)
            REGISTER -> Gson().fromJson(requestBody, RegisterCommand::class.java)
            else -> null
        }

        if (command == null) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            var writer = OutputStreamWriter(httpExchange.responseBody)

            var resultCommand = command.execute()

            writer.write(Gson().toJson(resultCommand, IRegisterClientCommand::class.java))
        }
    } catch (e : Exception) {
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }

    println(httpExchange.responseCode.toString())
    httpExchange.close()

}

fun handleGet(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*");
    try {
        var authToken = httpExchange.requestHeaders.getFirst("Authorization")
        var user = AuthTokens.getUser(authToken)

        println("GET /command - " + authToken)


        if (user == null) {
            httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
            return
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            var writer = OutputStreamWriter(httpExchange.responseBody)
            writer.write(user.queue.pollCommands())
        }
    } catch (e : Exception) {
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }


    println(httpExchange.responseCode.toString())
    httpExchange.close()
}

fun handlePost(httpExchange: HttpExchange) {
    // Handle CORS
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*");
    var requestBody = org.apache.commons.io.IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    try {
        var initialCommand = Gson().fromJson(requestBody, INormalServerCommand::class.java)

        var authToken = httpExchange.requestHeaders.getFirst("Authorization")
        var user = AuthTokens.getUser(authToken)


        if (user == null) {
            httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
            httpExchange.close()
            return
        }
        println("POST /command - " + user.username)
        println(requestBody)

        var command = when (initialCommand.type) {
            CREATE_GAME -> Gson().fromJson(requestBody, CreateGameCommand::class.java)
            JOIN_GAME -> Gson().fromJson(requestBody, JoinGameCommand::class.java)
            LEAVE_GAME -> Gson().fromJson(requestBody, LeaveGameCommand::class.java)
            LIST_GAMES -> Gson().fromJson(requestBody, ListGamesCommand::class.java)
            PLAYER_READY -> Gson().fromJson(requestBody, PlayerReadyCommand::class.java)
            else -> null
        }

        if (command == null) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            var writer = OutputStreamWriter(httpExchange.responseBody)

            command.execute(user)

            writer.write(user.queue.pollCommands())
        }

    } catch (e : Exception) {
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }

    println(httpExchange.responseCode.toString())
    httpExchange.close()

}