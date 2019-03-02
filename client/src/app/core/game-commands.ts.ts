import { Command } from './command';
import { GamePlayer } from './model/game-player';
import { DestinationCard, ShardCard } from './model/cards';

// Server commands

// Client Commands
export class UpdatePlayerCommand implements Command {
    public command = 'updatePlayer';
    public player : GamePlayer;

    constructor(updatePlayerCommand: any) {
        if (!("player" in updatePlayerCommand)) {
            throw new TypeError('Unable to deserialize updatePlayerCommand object, ' + JSON.stringify(updatePlayerCommand));
        }

        this.player = new GamePlayer(updatePlayerCommand.player);
    }
}

export class ChangeTurnCommand implements Command {
    public command = "changeTurn";
    public userId : Number;

    constructor(changeTurnCommand: any) {
        if (!("userId" in changeTurnCommand)) {
            throw new TypeError('Unable to deserialize changeTurnCommand object, ' + JSON.stringify(changeTurnCommand));
        }

        this.userId = changeTurnCommand.userId;
    }
}

export class UpdateBankCommand implements Command {
    public command = 'updateBank';
    public faceUpCards: ShardCard[];
    public shardDrawPileSize: number;
    public shardDiscardPileSize: number;
    public destinationPileSize: number;

    constructor(updateBankCommand: any) {
        if (!('command' in updateBankCommand &&
            'faceUpCards' in updateBankCommand &&
            'shardDrawPileSize' in updateBankCommand &&
            'shardDiscardPileSize' in updateBankCommand &&
            'destinationPileSize' in updateBankCommand)) {
            throw new TypeError('Unable to deserialize UpdateBankCommand object, ' + JSON.stringify(updateBankCommand));
        }

        this.faceUpCards = updateBankCommand.faceUpCards;
        this.shardDrawPileSize = updateBankCommand.shardDrawPileSize;
        this.shardDiscardPileSize = updateBankCommand.shardDiscardPileSize;
        this.destinationPileSize = updateBankCommand.updateBankCommand;
    }
}

export class RequestDestinationsCommand implements Command {
    public command = 'requestDestinations';

    constructor(requestDestinationsCommand: any) {
        if (!('command' in requestDestinationsCommand)) {
            throw new TypeError('Unable to deserialize RequestDestinationsCommand object, ' + JSON.stringify(requestDestinationsCommand));
        }
    }
}

export class DealCardsCommand implements Command {
    public command = 'selectDestinations';
    public destinations: DestinationCard[];
    public shardCards: ShardCard[];

    constructor(dealCardsCommand: any) {
        if (!('command' in dealCardsCommand &&
            'destinations' in dealCardsCommand &&
            'shardCards' in dealCardsCommand)) {
            throw new TypeError('Unable to deserialize DealCardsCommand object, ' + JSON.stringify(dealCardsCommand));
        }
    }
}

export class DiscardDestionationsCommand implements Command {
    public command = 'discardDestinations';
    public discardedDestinations: DestinationCard[];

    constructor(discardDestinoationsCommand: any) {
        if (!('command' in discardDestinoationsCommand &&
            'discardedDestinations' in discardDestinoationsCommand)) {
            throw new TypeError('Unable to deserialize DiscardDestionationsCommand object, ' + JSON.stringify(discardDestinoationsCommand));
        }
    }
}