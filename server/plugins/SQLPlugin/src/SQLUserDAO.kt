import java.io.Serializable
import java.sql.Connection
import java.sql.SQLException

class SQLUserDAO(persistenceManager: IPersistenceManager) : IUserDAO(persistenceManager) {

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer

    override fun persistUser(user: IUser) {
        val addUser = "INSERT OR REPLACE INTO Users(UserId, Data)" +
                " values(?, ?)"
        try {
            val stmt = sqlPlugin.getConnection()!!.prepareStatement(addUser)
            stmt.setInt(1, user.userId)
            stmt.setBytes(2, serializer.serialize(user))
            stmt.execute()
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to persist User")
        }
    }

    override fun loadUsers(): List<IUser> {
        var users = mutableListOf<IUser>()
        val getUsers = "SELECT Data" +
                       " FROM Users"
        var results = sqlPlugin.getConnection()!!.createStatement().executeQuery(getUsers)
        while (results.next()) {
            var blob = results.getBytes("Data")
            var userData = serializer.deserialize(blob)
            if (userData is IUser) {
                users.add(userData)
            } else {
                throw DatabaseException("Error: SQL failed to deserialize User")
            }
        }
        return users
    }
}
