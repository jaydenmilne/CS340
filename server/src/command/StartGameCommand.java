package command;

public class StartGameCommand implements IClientCommand{
    private command.CommandType command = CommandType.startGame;
    private String gameId;

    public StartGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
