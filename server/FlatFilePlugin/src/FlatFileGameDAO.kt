import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileGameDAO Used to access games in a Flat File Tar database
 */
class FlatFileGameDAO(private val statement: FlatFileStatement) : IGameDAO {
    override fun persistGame(game: IGame) {
        statement.addFile(makeFileName(game), game)
    }

    override fun loadGames(persistenceManager: IPersistenceManager): List<IGame> {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistenceManager.getFolder("games").map { it -> it as IGame }
    }

    private fun makeFileName(game: Serializable): String {
        return "games/%d.game".format(game.hashCode())
    }

}