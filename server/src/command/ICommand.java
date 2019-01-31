package command;

public interface ICommand {
    public command.CommandType type = null;
    public void execute();
    public command.CommandType getType();
}
