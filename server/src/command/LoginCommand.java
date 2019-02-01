package command;

public class LoginCommand implements IServerCommand{
    private command.CommandType command = CommandType.login;
    private String username;
    private String password;

    public LoginCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
