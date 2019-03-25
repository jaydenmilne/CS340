# Game Commands
These are the commands that are associated with game play.
See [Command Protocol](protocol.md) for a description of how these commands are to be
transmitted back and forth.


## JSON representation of models

Several commands transmit arrays of models, these are their descriptions.

| Name                                    | Description |
|-----------------------------------------|----------------------------------------|
| [route](#route-object)                    | Route that connects two cities |
| [shardCard](#shardCard-object)            | Shard card needed to claim a route |
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
    "type": "{type of shard card}"
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
    "numShardCards": 4,
    "numDestinationCards": 4,
    "numRemainingShards": 4,
    "hasLongestRoute": false,
    "turnOrder": 2
}
```

### `playerPoint` object
```json
{
    "userId": "{id of player}",
    "username": "{username}",
    "totalPoints": 100, // Could be calculated on client
    "claimedRoutePoints": 73,
    "completedDestinationPoints": 50,
    "incompleteDestinationPoints": -32,
    "longestRoutePoints": 10,
}
```

## Commands

| Command                                | [Type](protocol.md#terminology)  | Related Commands |    
|----------------------------------------|----------------------------------|------------------|
| [updateBank](#updateBank-command)          | Client                           | `drawShardCard`    |
| [updatePlayer](#updatePlayer-command)     | Client                            | `changeTurn`    |
| [requestDestinations](#requestDestinations-command)     | Server              | `discardDestinations`, `selectDestinations`    |
| [dealCards](#dealCards-command)     | Client                                  | `discardDestinations`, `requestDestinations`    |
| [discardDestinations](#discardDestinations-command)     | Server              | `requestDestinations`, `selectDestinations`    |
| [updateHand](#updateHand-command)         | Client                            | `claimRoute`  |
| [changeTurn](#changeTurn-command)         | Client                            |     |
| [claimRoute](#claimRoute-command)         | Server                            | `routeClaimed`, `updateHand` |
| [routeClaimed](#routeClaimed-command)     | Client                            | `claimRoute`    |
| [drawShardCard](#drawShardCard-command)         | Server                      | `dealCards`    |
| [lastRound](#lastRound-command)         | Client                              |     |
| [gameOver](#gameOver-command)         | Client                                |     |
| [debugHelp](#debugHelp-command)       | Server                                |     |

### `updateBank` Command
Client Command.

Conveys a change in the state of the bank

#### Syntax
```json
{
    "command": "updateBank",
    "faceUpCards": ["{array of shardCards}"],
    "shardDrawPileSize": 4,
    "shardDiscardPileSize": 4,
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

Sends DestinationCards and ShardCards to the player. These are new cards to be added to their hand. Step 2 of selecting destination cards.

#### Syntax
```json
{
    "command": "dealCards",
    "destinations": ["{array of 3 destinationCards}"],
    "shardCards": ["{array of shardCards}"],
    "minDestinations": 2
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

### `updateHand` Command
Client Command.

Sends DestinationCards and ShardCards to the server. This command contains the current state of their hand and replaces the current model. Used when claiming routes.

#### Syntax
```json
{
    "command": "updateHand",
    "destinations": ["{array of DestinationCards}"],
    "shardCards": ["{array of ShardCards}"],
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
### `claimRoute` Command
Server Command.

Informs the server that a player has claimed a route with the specified shard cards.

#### Syntax
```json
{
    "command": "claimRoute",
    "routeId": "{Id of the route being claimed}",
    "shardsUsed": "[{Array of ShardCards from the users hand}]"
}
```

### `routeClaimed` Command
Client Command.

Informs clients that a player has claimed a route.

#### Syntax
```json
{
    "command": "routeClaimed",
    "userId": "{Id of player}",
    "routeId": "{Id of the route claimed}",
}
```

### `drawShardCard` Command
Server Command.

Informs the server that a player has drawn a card from the face up pile or from the deck.

#### Syntax
```json
{
    "command": "drawShardCard",
    "card": "{MaterialType of card picked up or 'deck' if card was drawn from the pile. }",
}
```

### `lastRound` Command
Client Command.

Informs the client that this is the last round.

#### Syntax
```json
{
    "command": "lastRound",
}
```

### `gameOver` Command
Client Command.

Informs the client that the game is over.

#### Syntax
```json
{
    "command": "gameOver",
    "players": "[{Array of PlayerPoint objects}]"
}
```

### `debugHelp` Command
Server Command.

Allows the following list of actions to be run on the Server

| Command                                | Result |    
|---------------------------------|----------------------------------|
|rainbowroad| Makes all five faceup cards infinitygauntlets|
|allyourbasesaremine| Takes all routes owned by other players and gives them to user|
|claim [player] [routeId]| Claims said route for said user|
|givethemtrainsorgivemedeath| Makes everyones trains 100 except the user who is left with 10|
|myturn| Advances whose turn it is|
|heartinthecards| Gives user a full deck of shardcards in his hand an removes everyone elses cards|
|finaldestination| Gives all other players a deck of destination cards|
|newroad [city1] [city2] [points]| Makes a new destination card for the user with those cities & points|
|whereto| Starts a draw on destination cards for a user|
|makeitrain [type]| If the type is empty the user gets three of each type of card if it isn't the user gets three of that type of card|

#### Syntax
```json
{
    "command": "debugHelp",
    "action": "/rainbowroad"
}
```