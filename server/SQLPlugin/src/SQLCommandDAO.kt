import java.io.Serializable
import java.sql.Connection
import java.sql.SQLException

class SQLCommandDAO(persistenceManager: IPersistenceManager) : ICommandDAO(persistenceManager) {

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer;

    override fun clearCommandsForGame(gameId: Int) {
        val removeCommands = "DELETE FROM Commands" +
                             " WHERE gameId = " + gameId
        try {
            val stmt = sqlPlugin.getConnection()!!.createStatement()
            stmt.executeUpdate(removeCommands)
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to delete Commands")
        }
    }

    override fun persistCommand(command: serializedCmdDTO, gameID: Int) {
        val addCommand = "INSERT INTO Commands(GameId, Data)" +
                " values(?, ?)"
        try {
            val stmt = sqlPlugin.getConnection()!!.prepareStatement(addCommand)
            stmt.setInt(1, gameID)
            stmt.setBlob(2, serializer.serialize(command).inputStream())
            stmt.execute()
        }
        catch (e: SQLException) {
            persistenceManager.closeTransaction(false)
            e.printStackTrace()
            throw DatabaseException("Error: SQL failed to persist Command")
        }
    }

    override fun loadCommands(): List<serializedCmdDTO> {
        var commands = mutableListOf<serializedCmdDTO>()
        val getCommands = "SELECT Data" +
                " FROM Commands"
        var results = sqlPlugin.getConnection()!!.createStatement().executeQuery(getCommands)
        while(results.next()) {
            var blob = results.getBlob("Data")
            var commandData = serializer.deserialize(blob.getBytes(1, blob.length().toInt()))
            if(commandData is serializedCmdDTO) {
                commands.add(commandData)
            } else{
                throw DatabaseException("Error: SQL failed to deserialize Command")
            }
            blob.free()
        }
        return commands
    }


}