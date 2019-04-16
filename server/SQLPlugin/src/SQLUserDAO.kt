import java.io.Serializable
import java.sql.Connection
import java.sql.SQLException

class SQLUserDAO(persistenceManager: IPersistenceManager) : IUserDAO(persistenceManager) {

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer;

    override fun persistUser(user: IUser) {
        val addGame = "INSERT INTO Users(GameId, Data)" +
                " values(?, ?)"
        try {
            val stmt = sqlPlugin.getConnection()!!.prepareStatement(addGame)
            stmt.setInt(1, user.userId)
            stmt.setBlob(2, serializer.serialize(user).inputStream())
            stmt.execute()
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to persist Game")
        }
    }

    override fun loadUsers(): List<IUser> {
        var users = listOf<IUser>()
        return users
    }
}