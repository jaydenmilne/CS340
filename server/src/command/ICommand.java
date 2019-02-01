package command;

public interface ICommand {
    public command.CommandType command = null;
    public void execute();
    public command.CommandType getType();
}
