package command;

public class LeaveGameCommand implements IServerCommand{
    private command.CommandType command = CommandType.leaveGame;
    private String gameId;

    public LeaveGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
