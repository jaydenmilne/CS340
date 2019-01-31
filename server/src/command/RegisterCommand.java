package command;

public class RegisterCommand implements IServerCommand{
    private command.CommandType type = CommandType.REGISTER;
    private String username;
    private String password;

    public RegisterCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
