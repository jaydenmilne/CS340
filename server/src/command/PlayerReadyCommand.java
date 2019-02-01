package command;

public class PlayerReadyCommand implements IServerCommand{
    private command.CommandType command = CommandType.playerReady;
    private String gameId;
    private boolean playerIsReady;

    public PlayerReadyCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
