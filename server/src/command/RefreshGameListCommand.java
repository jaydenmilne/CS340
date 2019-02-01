package command;

public class RefreshGameListCommand implements IClientCommand{//unfinished needs GamesList Model Object
    private command.CommandType command = CommandType.refreshGameList;
    //private GamesList games;

    public RefreshGameListCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
