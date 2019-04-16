import java.io.Serializable
import java.sql.SQLException
import java.sql.Connection
import java.sql.DriverManager


class SQLGameDAO(persistenceManager: IPersistenceManager) : IGameDAO(persistenceManager) {

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer;

    override fun persistGame(game: IGame) {

        val addGame = "INSERT INTO Games(GameId, Data)" +
                      " values(?, ?)"
        try {
            val stmt = sqlPlugin.getConnection()!!.prepareStatement(addGame)
            stmt.setInt(1, game.gameId)
            stmt.setBlob(2, serializer.serialize(game).inputStream())
            stmt.execute()
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to persist Game")
        }
    }

    override fun loadGames(): List<IGame> {
        var games = listOf<IGame>()
        return games
    }


}