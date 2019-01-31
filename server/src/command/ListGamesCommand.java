package command;

public class ListGamesCommand implements IServerCommand{ //unfinished waiting on GameList Model Object
    private command.CommandType type = CommandType.LIST_GAMES;
    //private GameList games;

    public ListGamesCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
