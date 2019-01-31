package command;

public class CreateGameCommand implements IServerCommand{
    private command.CommandType type = CommandType.CREATE_GAME;
    private String name;

    public CreateGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
