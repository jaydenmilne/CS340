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

Valid colors are
- `blue`
- `red`
- `green`
- `yellow`
- `black`

### `user` object
This should only be sent as part of the loginResult command.
```json
{
    "username": "username",
    "userId": "user ID number",
    "color": "blue",
    "ready": true
}
```

## Commands
| Command                                | [Type](protocol.md#terminology)  | Related Commands |    
|----------------------------------------|----------------------------------|------------------|
| [register](#register-command)*         | Server                           | `loginResult`    |
| [login](#login-command)*               | Server                           | `loginResult`    |
| [loginResult](#loginresult-command)*   | Client                           | `register`, `login` |
| [listGames](#listgames-command)        | Server                           | `refreshGameList`|
| [refreshGameList](#refreshgamelist-command) | Client                      | `listGames`      |
| [joinGame](#joingame-command)          | Server                           | `leaveGame`      |
| [leaveGame](#leavegame-command)        | Server                           | `joinGame`       |
| [createGame](#creategame-command)      | Server                           | `refreshGameList`    |
| [changeColor](#changecolor-command)    | Server                           | `gameCreated`    |
| [gameCreated](#gamecreated-command)    | Client                           | `createGame`     |
| [playerReady](#playerready-command)    | Server                           |                  |
| [startGame](#startgame-command)        | Client                           |                  |
* denotes a command that must be sent to the server and to the client via a request to the `/register` endpoint.

### `register` Command
Server Command (must be sent to the server via a request to the `/register` endpoint.).

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
Server Command (must be sent to the server via a request to the `/register` endpoint.).

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
Client Command (must be sent to client via a request to the `/register` endpoint.).

Informs the client the result of a login. If there was no error, `error` should be an empty string. If there was an error, "user" should be empty.

If `gameId` is not `-1`, then the client should skip the lobby and go directly to the game, and send a `rejoinGame` command.

#### Syntax
```json
{
    "command": "loginResult",
    "error": "message",
    "user": {
        // user object
    },
	"gameid": 3
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

### `changeColor` Command
Client Command.

Informs the server that the player has selected a new color.

#### Syntax
```json
{
    "command": "changecolor",
    "gameId": "{game id}",
    "color":  "{new colorolor}"
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
