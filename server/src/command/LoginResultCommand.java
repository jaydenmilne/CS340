package command;

public class LoginResultCommand implements IClientCommand{
    private command.CommandType type = CommandType.LOGIN_RESULT;
    private String authToken;
    private String error;

    public LoginResultCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
