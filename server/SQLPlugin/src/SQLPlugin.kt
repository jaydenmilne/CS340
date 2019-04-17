import java.io.File
import java.nio.file.Files
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLPlugin : IPersistenceManager {

    private val parentdir = File(".").canonicalPath

    private val filename = "endgame.db"

    private val sqlite = "jdbc:sqlite:"

    private val url = sqlite + parentdir + "\\SQLPlugin\\db\\" + filename

    private val filepath = parentdir + "\\SQLPlugin\\db\\" + filename

    private var connection: Connection? = null

    override fun openTransaction() {
        try {
            connection = DriverManager.getConnection(url)
            connection!!.autoCommit = false
        }
        catch (e: SQLException) {
            e.printStackTrace()
            throw DatabaseException("Error: SQL transaction failed to open")
        }
    }

    override fun closeTransaction(commit: Boolean) {
        try {
            if (connection != null) {
                if (!connection!!.isClosed) {
                    if (commit) {
                        connection!!.commit()
                    } else {
                        connection!!.rollback()
                    }
                    connection!!.close()
                }
            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
            throw DatabaseException("Error: SQL transaction failed to close")
        }
    }

    override fun getCommandDAO(): ICommandDAO { return SQLCommandDAO(this) }

    override fun getUserDAO(): IUserDAO { return SQLUserDAO(this) }

    override fun getGameDAO(): IGameDAO { return SQLGameDAO(this) }

    override fun initialize(): Boolean {
        val file = File(filepath)
        if (!file.exists()) {
            return clear()
        }
        return true
    }

    override fun clear(): Boolean {
        val file = File(filepath)
        Files.deleteIfExists(file.toPath())

        openTransaction()

        val createTable1 = "CREATE TABLE Commands (" +
                " CommandId integer NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
                " GameId integer NOT NULL," +
                " Data blob NOT NULL," +
                " FOREIGN KEY(GameId) REFERENCES Games(GameId)" +
                ");"

        val createTable2 = "CREATE TABLE Games (" +
                " GameId integer NOT NULL UNIQUE," +
                " Data blob NOT NULL," +
                " PRIMARY KEY(GameId)" +
                ");"

        val createTable3 = "CREATE TABLE Users (" +
                " UserId integer NOT NULL UNIQUE," +
                " Data blob NOT NULL," +
                " PRIMARY KEY(UserId)" +
                ");"

        try {
            val stmt = connection!!.createStatement()
            stmt.addBatch(createTable1)
            stmt.addBatch(createTable2)
            stmt.addBatch(createTable3)
            stmt.executeBatch()
        }
        catch (e: SQLException) {
            closeTransaction(false)
            e.printStackTrace()
            return false
        }

        closeTransaction(true)
        return true
    }

    fun getConnection(): Connection? { return this.connection}
}
