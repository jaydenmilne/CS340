import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileGameDAO Used to access games in a Flat File Tar database
 */
class FlatFileGameDAO(persistenceManager: IPersistenceManager) : IGameDAO(persistenceManager) {
    override fun persistGame(game: IGame) {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        persistenceManager.addFile(makeFileName(game), game)
    }

    override fun loadGames(): List<IGame> {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistenceManager.getFolder("games").map { it -> it as IGame }
    }

    private fun makeFileName(game: IGame): String {
        return "games/%d.game".format(game.gameId)
    }

}