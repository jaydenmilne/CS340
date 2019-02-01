package command;

public class JoinGameCommand implements IServerCommand{
    private command.CommandType command = CommandType.joinGame;
    private String gameId;

    public JoinGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
