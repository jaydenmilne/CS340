package command;

public class CreateGameCommand implements IServerCommand{
    private command.CommandType command = CommandType.createGame;
    private String name;

    public CreateGameCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
