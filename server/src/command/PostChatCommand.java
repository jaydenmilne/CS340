package command;

public class PostChatCommand implements IClientCommand, IServerCommand{//unfinished waiting on Message Model Object
    private command.CommandType type = CommandType.POST_CHAT;
    //private Message message;

    public PostChatCommand(){
    }

    @Override
    public void execute() {

    }

    @Override
    public CommandType getType() {
        return type;
    }
}
