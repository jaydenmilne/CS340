# Game Commands
These are the commands that are associated with game play.
See [Command Protocol](protocol.md) for a description of how these commands are to be
transmitted back and forth.


## JSON representation of models

Several commands transmit arrays of models, these are their descriptions.

| Name                                    | Description |
|-----------------------------------------|----------------------------------------|
| [route](#route-object)                    | Route that connects two cities |
| [shardCard](#shardCard-object)            | Train card needed to claim a route |
| [destinationCard](#destinationCard-object)      | Destination card connecting two cities. Connecting these will earn the player points. |
| [gamePlayer](#gamePlayer-object)      | Updated player object with additional fields |

### `route` object
```json
{
    "routeId": "miami_ny_1",
    "cities": ["miami", "newyork"],
    "numCars": 4,
    "type": "{type of car needed to claim}",
    "ownerId": "{userId of owning player}"
}
```

### `shardCard` object
```json
{
    "type": "{type of train card}"
}
```

### `destinationCard` object
```json
{
    "cities": ["miami", "newyork"],
    "points": 4
}
```

### `gamePlayer` object
```json
{
    "userId": "{id of player}",
    "username": "{username}",
    "color": "BLUE",
    "ready": true,
    "points": 4,
    "numTrainCards": 4,
    "numDestinationCards": 4,
    "numRemainingTrains": 4,
    "hasLongestRoute": false,
    "turnOrder": 2
}
```

## Commands

| Command                                | [Type](protocol.md#terminology)  | Related Commands |    
|----------------------------------------|----------------------------------|------------------|
| [updateBank](#updateBank-command)          | Client                           | `drawCards`    |
| [updatePlayer](#updatePlayer-command)     | Client                           | `changeTurn`    |
| [requestDestinations](#requestDestinations-command)     | Server             | `discardDestinations`, `selectDestinations`    |
| [selectDestinations](#selectDestinations-command)     | Client               | `discardDestinations`, `requestDestinations`    |
| [discardDestinations](#discardDestinations-command)     | Server             | `requestDestinations`, `selectDestinations`    |
| [changeTurn](#changeTurn-command)         | Client                            |     |

### `updateBank` Command
Client Command.

Conveys a change in the state of the bank

#### Syntax
```json
{
    "command": "updateBank",
    "faceUpCards": ["{array of shardCards}"],
    "trainDrawPileSize": 4,
    "trainDiscardPileSize": 4,
    "destinationPileSize": 4,
}
```

### `updatePlayer` Command
Client Command.

Conveys a change in the state of a player. Sends the updated player object over.
Also the mechanism used to add players at the beginning of the game.

#### Syntax
```json
{
    "command": "updatePlayer",
    "gamePlayer": {
        // gamePlayer object
    }
}
```

### `requestDestinations` Command
Server Command.

Requests destinationCards from the server. Step 1 of selecting destination cards.

#### Syntax
```json
{
    "command": "requestDestinations"
}
```

### `dealCards` Command
Client Command.

Sends destinationCards to the client, as well as the shardCards for that player. Step 2 of selecting destination cards.

#### Syntax
```json
{
    "command": "dealCards",
    "destinations": ["{array of 3 destinationCards}"],
    "shardCards": ["{array of shardCards}"]
}
```

### `discardDestinations` Command
Server Command.

Sends discarded destinationCards back to the server. Step 3 of selecting destination cards.

#### Syntax
```json
{
    "command": "discardDestinations",
    "discardedDestinations": ["{array of 0-2 destinationCards}"]
}
```

### `changeTurn` Command
Client Command.

Informs the client tha the turn has passed to the next player.

#### Syntax
```json
{
    "command": "changeTurn",
    "userId": "{userId of player who's turn it is}"
}
```
