package command;

public class RegisterCommand implements IServerCommand{
    private command.CommandType command = CommandType.register;
    private String username;
    private String password;

    public RegisterCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
