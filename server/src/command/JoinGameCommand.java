package command;

public class JoinGameCommand implements IServerCommand{
    private command.CommandType type = CommandType.JOIN_GAME;
    private String username;
    private String password;

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
