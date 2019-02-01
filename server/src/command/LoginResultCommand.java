package command;

public class LoginResultCommand implements IClientCommand{
    private command.CommandType command = CommandType.loginResult;
    private String authToken;
    private String error;

    public LoginResultCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
