import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileCommandDAO Used to access commands in a Flat File Tar database
 */
class FlatFileCommandDAO(private val statement: FlatFileStatement) : ICommandDAO{
    override fun persistCommand(command: Serializable, gameID: Int) {
        statement.addFile(makeFileName(command, gameID), command)
    }

    fun removeCommand(command: Serializable, gameID: Int){
        statement.removeFile(makeFileName(command, gameID))
    }

    override fun loadCommands(persistanceManager: IPersistanceManager): List<Serializable> {
        if(persistanceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistanceManager.getFolder("command")
    }

    private fun makeFileName(command: Serializable, gameID: Int): String {
        return "commands/game_%d/%d.cmd".format(gameID, command.hashCode())
    }

}