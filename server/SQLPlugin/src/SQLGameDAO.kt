import java.io.Serializable

class SQLGameDAO(persistenceManager: IPersistenceManager) : IGameDAO(persistenceManager) {
    override fun persistGame(game: IGame) {
    }

    override fun loadGames(): List<IGame> {
        var games = listOf<IGame>()
        return games
    }


}