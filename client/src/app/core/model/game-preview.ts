import { Color } from './color.enum';
import { Player } from './player';

export class GamePreview {
    private name: string;
    private Id: string;
    private started: boolean;
    private players: Player [];

    constructor(gamePreview: any) {
        if (!('name' in gamePreview ||
        'Id' in gamePreview ||
        'started' in gamePreview ||
        'players' in gamePreview)) {
            throw new TypeError('Unable to deserialize GamePreview object, ' + JSON.stringify(gamePreview));
    }

        this.name = gamePreview.name;
        this.Id = gamePreview.Id;
        this.started = gamePreview.started;
        this.players = [];
        gamePreview.players.forEach(player => {
            this.players.push(new Player(player))
        });
    }

    public getName(): string {
        return this.name;
    }

    public setColor(name: string) {
        this.name = name;
    }

    public getID(): string {
        return this.Id;
    }

    public setUserID(ID: string) {
        this.Id = ID;
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
            takenColors.push(player.getColor());
        });

        const avaiableColors = [];

        colors.forEach(color => {
            if (!takenColors.includes(color)) {
                avaiableColors.push(color);
            }
        });

        return avaiableColors;
    }
}

