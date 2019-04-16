import java.io.Serializable
import java.sql.Connection

class SQLCommandDAO(persistenceManager: IPersistenceManager) : ICommandDAO(persistenceManager) {

    private val sqlPlugin: SQLPlugin = persistenceManager as SQLPlugin
    private val serializer = Serializer;

    override fun clearCommandsForGame(gameID: Int) {

    }

    override fun persistCommand(command: serializedCmdDTO, gameID: Int) {
    }

    fun removeCommand(command: Serializable, gameID: Int){

    }

    override fun loadCommands(): List<serializedCmdDTO> {
        var commands = listOf<serializedCmdDTO>()
        return commands;
    }


}