package command;

public class GameCreatedCommand implements IServerCommand {
    private command.CommandType command = CommandType.createGame;
    private String gameId;

    public GameCreatedCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
