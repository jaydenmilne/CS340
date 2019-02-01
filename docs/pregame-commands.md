# Pregame Commands
These are the commands that are associated with the pregame flow (login and the lobby).
See [Command Protocol](protocol.md) for a description of how these commands are to be
transmitted back and forth.


## JSON representation of models

Several commands transmit arrays of models, these are their descriptions.

| Name                                    | Description |
|-----------------------------------------|----------------------------------------|
| [gamePreview](#gamepreview-object)      | List of game previews that are used in the lobby.         |
| [player](#player-object)                | Player object included in the gamePreviews for the lobby. |

### `gamePreview` object
```json
{
    "gameId": "{id of game}",
    "name": "Bob's Epic Game",
    "started": true,
    "players": [
        // array of player objects
    ]
}
```

### `player` object
```json
{
    "playerId": "{id of player}",
    "name": "Bob",
    "color": "blue",
    "ready": true
}
```

## Commands

| Command                                | [Type](protocol.md#terminology)  | Related Commands |    
|----------------------------------------|----------------------------------|------------------|
| [register](#register-command)          | Server                           | `loginResult`    |
| [login](#login-command)                | Server                           | `loginResult`    |
| [loginResult](#loginresult-command)    | Client                           | `register`, `login` |
| [listGames](#listgames-command)        | Server                           | `refreshGameList`|
| [refreshGameList](#refreshgamelist-command) | Client                      | `listGames`      |
| [joinGame](#joingame-command)          | Server                           | `leaveGame`      |
| [leaveGame](#leavegame-command)        | Server                           | `joinGame`       |
| [createGame](#creategame-command)      | Server                           | `gameCreated`    |
| [gameCreated](#gamecreated-command)    | Client                           | `createGame`     |
| [playerReady](#playerready-command)    | Server                           |                  |
| [postChat](#postchat-command)          | Server / Client                  |                  |
| [startGame](#startgame-command)        | Client                           |                  |

### `register` Command
Server Command.

Registers a new user.
`username` should be a maximum of 16 characters.

#### Syntax
```json
{
    "command": "register",
    "username": "uname",
    "password": "password"
}
```

### `login` Command
Server Command.

Logs in an existing user.

#### Syntax
```json
{
    "command": "login",
    "username": "uname",
    "password": "password"
}
```

### `loginResult` Command
Client Command.

Informs the client the result of a login.

If the login/registration is successful, `authToken` will be present and populated.
If the login/registration was not successful, `error` will be present and populated.

#### Syntax
```json
{
    "command": "loginResult",
    "authToken": "token",
    "error": "message",
}
```
### `listGames` Command
Client Command.

Requests an updated list of games from the server.

#### Syntax
```json
{
    "command": "listGames",
}
```

### `refreshGameList` Command
Client Command.

Gives the client an updated list of games.

#### Syntax
`"games"` is an array of [gamePreviewObjects](#gamepreview-object)
```json
{
    "command": "refreshGameList",
    "games": [
        // array of game objects
    ]
}
```

### `joinGame` Command
Client Command.

Informs the server the user has joined a game.

#### Syntax
```json
{
    "command": "joinGame",
    "gameId": "{game id}"
}
```

### `leaveGame` Command
Client Command.

Informs the server the user has left a game.

#### Syntax
```json
{
    "command": "leaveGame",
    "gameId": "{game id}"
}
```

### `createGame` Command
Client Command.

Informs the server the user has created a game.

#### Syntax
```json
{
    "command": "createGame",
    "name": "Dan's Epic Game"
}
```

### `gameCreated` Command
Client Command.

Informs the client that the user's new game has been created so the UI
knows which game needs to be selected

#### Syntax
```json
{
    "command": "gameCreated",
    "gameId": "{id of new game}"
}
```

### `playerReady` Command
Client Command.

Updates the player's ready status for a game.

#### Syntax
```json
{
    "command": "playerReady",
    "gameId": "{id of relevant game}",
    "playerIsReady": true
}
```

### `postChat` Command
Client and Client Command. 

If sent to the server, the client is sending a message that should be broadcast
to other players in the game. When sending to the server the client may omit
`playerId` and `playerName` since it is implied it is the user associated with
the auth token.

If sent to the client, the client is receiving a message it should display in 
the chat window.

#### Syntax
```json
{
    "command": "postChat",
    "gameId": "{id of relevant game}",
    "playerId": "{id of relevant player}",
    "playerName": "{name of relevant player}",
    "message": "I like pie!"
}
```

### `startGame` Command
Client Command.

The Server is informing the Client that all players have readied up and that the
game has started.

#### Syntax
```json
{
    "command": "startGame",
    "gameId": "{relevant game id}"
}
