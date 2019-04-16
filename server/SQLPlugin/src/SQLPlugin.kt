import java.io.File
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class SQLPlugin : IPersistenceManager {

    private val parentdir = File(".").canonicalPath

    private val filename = "endgame.db"

    private val url = "jdbc:sqlite:" + parentdir + "\\SQLPlugin\\db\\" + filename

    private var connection : Connection? = null

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
            if (commit) {
                connection!!.commit()
            }
            else {
                connection!!.rollback()
            }
        }
        catch (e: SQLException) {
            e.printStackTrace()
            throw DatabaseException("Error: SQL transaction failed to close")
        }
    }

    override fun getCommandDAO(): ICommandDAO {
        TODO("not implemented")
    }

    override fun getUserDAO(): IUserDAO {
        TODO("not implemented")
    }

    override fun getGameDAO(): IGameDAO {
        TODO("not implemented")
    }

    override fun initialize(): Boolean {
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

    override fun clear(): Boolean {
        openTransaction()

        val deleteTable1 = "DROP TABLE Commands"
        val deleteTable2 = "DROP TABLE Games"
        val deleteTable3 = "DROP TABLE Users"

        try {
            val stmt = connection!!.createStatement()
            stmt.addBatch(deleteTable1)
            stmt.addBatch(deleteTable2)
            stmt.addBatch(deleteTable3)
            stmt.executeBatch()
        }
        catch (e: SQLException) {
            closeTransaction(false)
            e.printStackTrace()
            return false
        }

        closeTransaction(true)
        return initialize()
    }
}

fun main(args: Array<String>) {
    println("TEST")
    println("Initializing Database")
    SQLPlugin().initialize()
    println("Clearing Database")
    SQLPlugin().clear()
}