package command;

public class RefreshGameListCommand implements IClientCommand{//unfinished needs GamesList Model Object
    private command.CommandType type = CommandType.REFRESH_GAME_LIST;
    //private GamesList games;

    public RefreshGameListCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
