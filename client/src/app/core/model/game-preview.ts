import { Color } from './color.enum';
import { Player } from './player';

export class GamePreview {
    private name: string;
    private gameId: number;
    private started: boolean;
    private players: Player [];

    constructor(gamePreview: any) {
        if (!('name' in gamePreview ||
        'gameId' in gamePreview ||
        'started' in gamePreview ||
        'players' in gamePreview)) {
            throw new TypeError('Unable to deserialize GamePreview object, ' + JSON.stringify(gamePreview));
    }

        this.name = gamePreview.name;
        this.gameId = gamePreview.gameId;
        this.started = gamePreview.started;
        this.players = [];
        gamePreview.players.forEach(player => {
            this.players.push(new Player(player));
        });
    }

    public getName(): string {
        return this.name;
    }

    public setColor(name: string) {
        this.name = name;
    }

    public getID(): number {
        return this.gameId;
    }

    public setUserID(ID: number) {
        this.gameId = ID;
    }

    public isStarted(): boolean {
        return this.started;
    }

    public setStarted(started: boolean) {
        this.started = started;
    }

    public getPlayers(): Player [] {
        return this.players;
    }

    public setPlayers(players: Player []) {
        this.players = players;
    }

    public getNumPlayers(): number {
        return this.players.length;
    }

    public getAvailableColors(): Color [] {
        const colors: Color[] = [Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.PURPLE];
        const takenColors: Color[] = [];

        this.players.forEach(player => {
            takenColors.push(player.color);
        });

        const avaiableColors = [];

        colors.forEach(color => {
            if (!takenColors.includes(color)) {
                avaiableColors.push(color);
            }
        });

        return avaiableColors;
    }

    public isPlayerReady(userId: number): boolean {
        const player: Player = this.players.find(p => p.userId === userId);
        return (player !== undefined && player.ready);
    }
}

