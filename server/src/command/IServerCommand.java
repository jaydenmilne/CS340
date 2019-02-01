package command;

public interface IServerCommand extends ICommand {
    public command.CommandType command = null;
    public void execute();
    public command.CommandType getType();
}
