package command;

public class JoinGameCommand implements IServerCommand{
    private command.CommandType type = CommandType.JOIN_GAME;
    private String userID;
    private String authToken;

    public JoinGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
