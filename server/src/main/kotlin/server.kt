import com.google.gson.Gson
import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer
import commands.*
import models.AuthTokens
import models.Games
import models.RegisterCommandQueue
import models.Users
import org.apache.commons.io.IOUtils
import persistence.PersistenceManager
import persistence.PluginManager
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection.*
import java.net.InetSocketAddress

const val MAX_CONNECTIONS = 1
const val REGISTRATION_ENDPOINT = "/register"
const val COMMAND_ENDPOINT = "/command"
const val PORT = 4300
var commandsBetweenCheckpoints = 10

fun main(args: Array<String>) {
    val pluginManager = PluginManager()

    if (args.size > 2) {
        println("Loading Plugin %s".format(args[2]))
        pluginManager.loadPlugin(args[2])
        if (args.size > 3) {
            commandsBetweenCheckpoints = Integer.parseInt(args[3])
        }
    }

    PersistenceManager.restoreDB()
    PersistenceManager.setCommandsBetweenCheckpoints(commandsBetweenCheckpoints)

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
    httpExchange.responseHeaders.add("Access-Control-Allow-Headers", "Authorization,authorization,content-type")
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

        val command = when (initialCommand.command) {
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
            val resultCommand = command.execute() as LoginResultCommand
            resultCommands.push(resultCommand)

            // We only want to serialize the new user
            if (resultCommand.error.isEmpty()) {
                PersistenceManager.saveUser(resultCommand.user.userId)
            }

            writer.write(Gson().toJson(resultCommands))
            writer.close()

        }

    } catch (e: Exception) {
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

        if (user == null) {
            httpExchange.sendResponseHeaders(HTTP_FORBIDDEN, 0)
            return
        } else {
            httpExchange.sendResponseHeaders(HTTP_OK, 0)
            val writer = OutputStreamWriter(httpExchange.responseBody)

            // Don't log if nothing sent back
            if (user.queue.commands.isNotEmpty()) {
                var commands = ""
                for (command in user.queue.commands) {
                    commands += command.command + ", "
                }
                println("GET /command - ${user.username} $commands")
                println(httpExchange.responseCode.toString())
            }

            writer.write(user.queue.render())
            writer.close()
        }
    } catch (e: Exception) {
        println(e)
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
        println(httpExchange.responseCode.toString())
    }

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
            CLAIM_ROUTE -> Gson().fromJson(requestBody, ClaimRouteCommand::class.java)
            DRAW_SHARD_CARD -> Gson().fromJson(requestBody, DrawShardCardCommand::class.java)
            DEBUG_HELP -> Gson().fromJson(requestBody, DebugHelpCommand::class.java)
            REJOIN_GAME -> Gson().fromJson(requestBody, RejoinGameCommand::class.java)
            SKIP_TURN -> Gson().fromJson(requestBody, SkipTurnCommand::class.java)
            else -> null
        }


        if (command == null) {
            println("POST /command - " + user.username)
            println(requestBody)
            httpExchange.sendResponseHeaders(HTTP_BAD_REQUEST, 0)
            println(httpExchange.responseCode.toString())
        } else {
            val writer = OutputStreamWriter(httpExchange.responseBody)

            if(command !is ListGamesCommand){
                println("POST /command - " + user.username)
                println(requestBody)
            }

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

            // Don't serialize commands that failed
            if (httpExchange.responseCode == HTTP_OK) {
                // Serialize all commands except for LIST_GAMES
                if (initialCommand.command != LIST_GAMES) {
                    PersistenceManager.saveCommand(serializedCmdDTO(command, user.userId),
                            Games.getGameIdForPlayer(user) ?: -1)
                }
            }

            if (command.command != LIST_GAMES) {
                println(httpExchange.responseCode.toString())
            }
        }

    } catch (e: Exception) {
        e.printStackTrace()
        httpExchange.sendResponseHeaders(HTTP_INTERNAL_ERROR, 0)
        println(httpExchange.responseCode.toString())
    }

    httpExchange.close()
}