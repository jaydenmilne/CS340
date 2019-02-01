package command;

public class RegisterResultCommand implements IClientCommand{
    private command.CommandType command = CommandType.registerResult;
    private String authToken;
    private String error;

    public RegisterResultCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
