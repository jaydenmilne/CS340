package command;

public class ListGamesCommand implements IServerCommand{
    private command.CommandType command = CommandType.listGames;

    public ListGamesCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
