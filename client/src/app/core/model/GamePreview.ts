import { Player } from './Player';

export class GamePreview {
    private name: string;
    private ID: string;
    private started: boolean;
    private players: Player [];

    constructor(name: string, id: string, started: boolean, players: Player []) {
        this.name = name;
        this.ID = id;
        this.started = started;
        this.players = players;
    }

    public getName(): string {
        return this.name;
    }

    public setColor(name: string) {
        this.name = name;
    }

    public getID(): string {
        return this.ID;
    }

    public setUserID(ID: string) {
        this.ID = ID;
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
}
