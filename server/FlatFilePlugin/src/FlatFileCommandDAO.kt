import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileCommandDAO Used to access commands in a Flat File Tar database
 */
class FlatFileCommandDAO(private val statement: FlatFileStatement) : ICommandDAO{
    override fun persistCommand(command: ICommand, gameID: Int) {
        statement.addFile(makeFileName(command, gameID), command)
    }

    fun removeCommand(command: Serializable, gameID: Int){
        statement.removeFile(makeFileName(command, gameID))
    }

    override fun loadCommands(persistenceManager: IPersistenceManager): List<ICommand> {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistenceManager.getFolder("command").map { it -> it as ICommand }
    }

    private fun makeFileName(command: Serializable, gameID: Int): String {
        return "commands/game_%d/%d.cmd".format(gameID, command.hashCode())
    }

}