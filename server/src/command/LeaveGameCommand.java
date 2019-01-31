package command;

public class LeaveGameCommand implements IServerCommand{
    private command.CommandType type = CommandType.LEAVE_GAME;
    private String userID;
    private String gameID;
    private String authToken;

    public LeaveGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
