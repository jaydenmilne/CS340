import { Command } from './command';
import { GamePlayer } from './model/game-player';
import { DestinationCard, ShardCard } from './model/cards';

// Server commands
export class DrawShardCard implements Command {
    public command = 'drawShardCard';

    constructor(public card: String) {}
}

// Client Commands
export class UpdatePlayerCommand implements Command {
    public command = 'updatePlayer';
    public gamePlayer: GamePlayer;

    constructor(updatePlayerCommand: any) {
        if (!('gamePlayer' in updatePlayerCommand)) {
            throw new TypeError('Unable to deserialize updatePlayerCommand object, ' + JSON.stringify(updatePlayerCommand));
        }

        this.gamePlayer = new GamePlayer(updatePlayerCommand.gamePlayer);
    }
}

export class ChangeTurnCommand implements Command {
    public command = 'changeTurn';
    public userId: Number;

    constructor(changeTurnCommand: any) {
        if (!('userId' in changeTurnCommand)) {
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
        this.destinationPileSize = updateBankCommand.destinationPileSize;
    }
}

export class RequestDestinationsCommand implements Command {
    public command = 'requestDestinations';
}

export class DealCardsCommand implements Command {
    public command = 'dealCards';
    public destinations: DestinationCard[];
    public shardCards: ShardCard[];
    public minDestinations: number;

    constructor(dealCardsCommand: any) {
        if (!('destinations' in dealCardsCommand &&
            'shardCards' in dealCardsCommand && 'minDestinations' in dealCardsCommand)) {
            throw new TypeError('Unable to deserialize DealCardsCommand object, ' + JSON.stringify(dealCardsCommand));
        }
        this.minDestinations = dealCardsCommand.minDestinations;

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

