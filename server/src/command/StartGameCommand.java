package command;

public class StartGameCommand implements IClientCommand{
    private command.CommandType type = CommandType.START_GAME;
    private String gameID;

    public StartGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
