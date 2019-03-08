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
        if (!('faceUpCards' in updateBankCommand &&
            'shardDrawPileSize' in updateBankCommand &&
            'shardDiscardPileSize' in updateBankCommand &&
            'destinationPileSize' in updateBankCommand)) {
            throw new TypeError('Unable to deserialize UpdateBankCommand object, ' + JSON.stringify(updateBankCommand));
        }

        this.faceUpCards = [];
        updateBankCommand.faceUpCards.forEach(card => {
            this.faceUpCards.push(new ShardCard(card));
        });

        this.shardDrawPileSize = updateBankCommand.shardDrawPileSize;
        this.shardDiscardPileSize = updateBankCommand.shardDiscardPileSize;
        this.destinationPileSize = updateBankCommand.updateBankCommand;
    }
}

export class RequestDestinationsCommand implements Command {
    public command = 'requestDestinations';
}

export class DealCardsCommand implements Command {
    public command = 'selectDestinations';
    public destinations: DestinationCard[];
    public shardCards: ShardCard[];

    constructor(dealCardsCommand: any) {
        if (!('destinations' in dealCardsCommand &&
            'shardCards' in dealCardsCommand)) {
            throw new TypeError('Unable to deserialize DealCardsCommand object, ' + JSON.stringify(dealCardsCommand));
        }

        this.destinations = [];
        dealCardsCommand.destinations.forEach(card => {
            this.destinations.push(new DestinationCard(card));
        });

        this.shardCards = [];
        dealCardsCommand.shardCards.forEach(card => {
            this.shardCards.push(new ShardCard(card));
        });
    }
}

export class DiscardDestinationsCommand implements Command {
    public command = 'discardDestinations';
    public discardedDestinations: DestinationCard[];

    constructor(discardedDestinations: DestinationCard[]) {
        this.discardedDestinations = discardedDestinations;
    }
}