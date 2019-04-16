import java.io.Serializable

class SQLCommandDAO(persistenceManager: IPersistenceManager) : ICommandDAO(persistenceManager) {

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