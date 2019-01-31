package command;

public class PlayerReadyCommand implements IServerCommand{
    private command.CommandType type = CommandType.PLAYER_READY;
    private String userID;
    private boolean ready;

    public PlayerReadyCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
