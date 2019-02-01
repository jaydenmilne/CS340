package command;

public class PostChatCommand implements IClientCommand, IServerCommand{
    private command.CommandType command = CommandType.postChat;
    private String gameId;
    private String playerId;
    private String playerName;
    private String message;

    public PostChatCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return command;
    }
}
