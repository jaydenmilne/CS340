import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileCommandDAO Used to access commands in a Flat File Tar database
 */
class FlatFileCommandDAO(persistenceManager: IPersistenceManager) : ICommandDAO(persistenceManager) {
    private val flafFilePlugin: FlatFilePlugin = persistenceManager as FlatFilePlugin

    override fun clearCommandsForGame(gameID: Int) {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        persistenceManager.removeFolder(makeFolderName(gameID))
    }

    override fun persistCommand(command: serializedCmdDTO, gameID: Int) {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        persistenceManager.addFolder(makeFolderName(gameID))
        persistenceManager.addFile(makeFileName(command, gameID), command)
    }

    fun removeCommand(command: Serializable, gameID: Int){
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        persistenceManager.removeFile(makeFileName(command, gameID))
    }

    override fun loadCommands(): List<serializedCmdDTO> {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistenceManager.getFolder("commands").map { it as serializedCmdDTO }
    }

    private fun makeFileName(command: Serializable, gameID: Int): String {
        return "commands/game_%d/%d.cmd".format(gameID, command.hashCode())
    }

    private fun makeFolderName(gameID: Int): String {
        return "commands/game_%d".format(gameID)
    }
}