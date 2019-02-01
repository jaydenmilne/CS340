package command;

public interface IClientCommand extends ICommand {
    public command.CommandType command = null;
    public void execute();
    public command.CommandType getType();
}