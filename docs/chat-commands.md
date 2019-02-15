# Game Commands
These are the commands that are associated with chat windows.
See [Command Protocol](protocol.md) for a description of how these commands are to be transmitted back and forth.


## JSON representation of models

Several commands transmit arrays of models, these are their descriptions.

| Name                                    | Description |
|-----------------------------------------|----------------------------------------|
| [chatMessage](#chatMessage-object)      | Message sent via the chat window |

### `chatMessage` object
```json
{
    "message": "the message",
    "gameId": "{ID of the game the chat belongs to}",
    "userId": "{ID of the user who sent the chat}",
    "username": "{username of the user who sent the chat}",
    "sequenceNum": "{Sequence number assigned by the server for ordering}"
}
```

## Commands

| Command                                | [Type](protocol.md#terminology)  | Related Commands |    
|----------------------------------------|----------------------------------|------------------|
| [postChat](#postChat-command)          | Server                  |     |
| [updateChat](#updateChat-command)      | Client                  |     |


### `postChat` Command
Server Command. 

New message that should be broadcast to other players in the game.

#### Syntax
```json
{
    "command": "postChat",
    "gameId": "{id of relevant game}",
    "message": "I like pie!"
}
```

### `updateChat` Command
Client Command. 

New message sent by another player that should be displayed in the chat window.

#### Syntax
```json
{
    "command": "updateChat",
    "message": "{chatMessage object}",
}
```
