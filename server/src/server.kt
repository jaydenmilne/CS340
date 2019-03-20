import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import commands.*
import models.AuthTokens
import models.RegisterCommandQueue
import org.apache.commons.io.IOUtils
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection.*
import java.net.InetSocketAddress

const val MAX_CONNECTIONS = 10
const val REGISTRATION_ENDPOINT = "/register"
const val COMMAND_ENDPOINT = "/command"
const val PORT = 4300

fun main(args: Array<String>) {
    val server = HttpServer.create(InetSocketAddress(PORT), MAX_CONNECTIONS)

    server.createContext(COMMAND_ENDPOINT) { httpExchange ->
        when (httpExchange.requestMethod) {
            "GET" -> handleGet(httpExchange)
            "POST" -> handlePost(httpExchange)
            "OPTIONS" -> handleOptions(httpExchange)
        }
    }

    server.createContext(REGISTRATION_ENDPOINT) { httpExchange ->
        when (httpExchange.requestMethod) {
            "POST" -> handleRegistrationPost(httpExchange)
            "OPTIONS" -> handleOptions(httpExchange)
        }
    }
    println("On your marks... get set...")
    server.start()
    println("Server started on port $PORT")
    println("Go!")
}

fun handleOptions(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*")
    httpExchange.responseHeaders.add("Access-Control-Allow-Headers", "*")
    httpExchange.sendResponseHeaders(HTTP_OK, 0)
    httpExchange.close()
}

fun handleRegistrationPost(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*")

    val requestBody = IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    println("POST /register")
    println(requestBody)

    try {
        val initialCommand = Gson().fromJson(requestBody, ServerCommandData::class.java)

        val command = when(initialCommand.command) {
            LOGIN -> Gson().fromJson(requestBody, LoginCommand::class.java)
            REGISTER -> Gson().fromJson(requestBody, RegisterCommand::class.java)
            else -> null
        }

        if (command == null) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            val writer = OutputStreamWriter(httpExchange.responseBody)

            val resultCommands = RegisterCommandQueue()
            resultCommands.push(command.execute())

            writer.write(Gson().toJson(resultCommands))
            writer.close()
        }

    } catch (e : Exception) {
        println(e)
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }

    println(httpExchange.responseCode.toString())
    httpExchange.close()
}

fun handleGet(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*")

    try {
        if (!httpExchange.requestHeaders.containsKey("Authorization")) {
            httpExchange.sendResponseHeaders(HTTP_UNAUTHORIZED, 0)
            httpExchange.close()
            return
        }

        val authToken = httpExchange.requestHeaders.getFirst("Authorization")
        val user = AuthTokens.getUser(authToken)

        println("GET /command - $authToken")

        if (user == null) {
            httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
            return
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            val writer = OutputStreamWriter(httpExchange.responseBody)
            writer.write(user.queue.render())
            writer.close()
        }
    } catch (e : Exception) {
        println(e)
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }

    println(httpExchange.responseCode.toString())
    httpExchange.close()
}

fun handlePost(httpExchange: HttpExchange) {
    httpExchange.responseHeaders.add("Access-Control-Allow-Origin", "*")

    val requestBody = IOUtils.toString(InputStreamReader(httpExchange.requestBody))

    try {
        val initialCommand = Gson().fromJson(requestBody, ServerCommandData::class.java)

        if (!httpExchange.requestHeaders.containsKey("Authorization")) {
            httpExchange.sendResponseHeaders(HTTP_UNAUTHORIZED, 0)
            httpExchange.close()
            return
        }

        val authToken = httpExchange.requestHeaders.getFirst("Authorization")
        val user = AuthTokens.getUser(authToken)

        if (user == null) {
            httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
            httpExchange.close()
            return
        }

        println("POST /command - " + user.username)
        println(requestBody)

        val command = when (initialCommand.command) {
            CREATE_GAME -> Gson().fromJson(requestBody, CreateGameCommand::class.java)
            JOIN_GAME -> Gson().fromJson(requestBody, JoinGameCommand::class.java)
            LEAVE_GAME -> Gson().fromJson(requestBody, LeaveGameCommand::class.java)
            LIST_GAMES -> Gson().fromJson(requestBody, ListGamesCommand::class.java)
            PLAYER_READY -> Gson().fromJson(requestBody, PlayerReadyCommand::class.java)
            CHANGE_COLOR -> Gson().fromJson(requestBody, ChangeColorCommand::class.java)
            POST_CHAT -> Gson().fromJson(requestBody, PostChatCommand::class.java)
            REQUEST_DESTINATIONS -> Gson().fromJson(requestBody, RequestDestinationsCommand::class.java)
            DISCARD_DESTINATIONS -> Gson().fromJson(requestBody, DiscardDestinationsCommand::class.java)
            DRAW_SHARD_CARD -> Gson().fromJson(requestBody, DiscardDestinationsCommand::class.java)
            else -> null
        }

        if (command == null) {
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
        } else {
            val writer = OutputStreamWriter(httpExchange.responseBody)

            try {
                command.execute(user)
                httpExchange.sendResponseHeaders(HTTP_OK, 0)
                writer.write(user.queue.render())
            } catch (e: CommandException) {
                println(e.message)
                writer.write(e.message)

                httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
            }

            writer.close()
        }

    } catch (e: Exception) {
        println(e)
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
    }

    println(httpExchange.responseCode.toString())
    httpExchange.close()
}