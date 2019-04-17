import java.io.Serializable
import java.sql.SQLException
import java.sql.Connection
import java.sql.DriverManager


class SQLGameDAO(persistenceManager: IPersistenceManager) : IGameDAO(persistenceManager) {
    override fun removeGame(game: IGame) {
        TODO("not implemented")
    }

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer;

    override fun persistGame(game: IGame) {

        val addGame = "INSERT OR REPLACE INTO Games(GameId, Data)" +
                      " values(?, ?)"
        try {
            val stmt = sqlPlugin.getConnection()!!.prepareStatement(addGame)
            stmt.setInt(1, game.gameId)
            stmt.setBytes(2, serializer.serialize(game))
            stmt.execute()
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to persist Game")
        }
    }

    override fun loadGames(): List<IGame> {
        var games = mutableListOf<IGame>()
        val getGames = "SELECT Data" +
                " FROM Games"
        var results = sqlPlugin.getConnection()!!.createStatement().executeQuery(getGames)
        while(results.next()) {
            var blob = results.getBytes("Data")
            var gameData = serializer.deserialize(blob)
            if(gameData is IGame) {
                games.add(gameData)
            } else{
                throw DatabaseException("Error: SQL failed to deserialize Game")
            }
        }
        return games
    }


}