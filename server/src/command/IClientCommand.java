package command;

public interface IClientCommand extends ICommand {
    public command.CommandType type = null;
    public void execute();
    public command.CommandType getType();
}