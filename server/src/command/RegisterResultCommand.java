package command;

public class RegisterResultCommand implements IClientCommand{
    private command.CommandType type = CommandType.REGISTER_RESULT;
    private String authToken;
    private String error;

    public RegisterResultCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
