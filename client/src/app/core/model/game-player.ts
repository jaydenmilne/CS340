import { Color } from './color.enum';

export class GamePlayer {
    private _color: Color;
    private _userId: number;
    private _ready: boolean;
    private _username: string;
    private _points: number;
    private _numTrainCards: number;
    private _numDestinationCards: number;
    private _numRemainingTrains: number;
    private _hasLongestRoute: boolean;
    private _turnOrder: number;
    
    constructor(gamePlayer: any) {
        if (!('userId' in gamePlayer) ||
            !('ready' in gamePlayer) ||
            !('username' in gamePlayer) ||
            !('color' in gamePlayer) ||
            !('points' in gamePlayer) ||
            !('numTrainCards' in gamePlayer) ||
            !('numDestinationCards' in gamePlayer) ||
            !('numRemainingTrains' in gamePlayer) ||
            !('hasLongestRoute' in gamePlayer) ||
            !('turnOrder' in gamePlayer)) {
            throw new TypeError('Unable to deserialize GamegamePlayer object, ' + JSON.stringify(gamePlayer));
        }

        this._color = <Color> Color[gamePlayer.color];
        this._userId = gamePlayer.userId;
        this._ready = gamePlayer.ready;
        this._username = gamePlayer.username;
        this._points = gamePlayer.points;
        this._numTrainCards = gamePlayer.numTrainCards;
        this._numDestinationCards = gamePlayer.numDestinationCards;
        this._numRemainingTrains = gamePlayer.numRemainingTrains;
        this._hasLongestRoute = gamePlayer.hasLongestRoute;
        this._turnOrder = gamePlayer.turnOrder;
    }

    public setColor(color: string) {
        this._color = Color[color.toUpperCase()];
    }

    public getColor(): Color {
        return this._color;
    }

    get userId(): number {
        return this._userId;
    }

    get ready(): boolean {
        return this._ready;
    }

    get username(): string {
        return this._username;
    }

    get points(): number {
        return this._points;
    }

    get numTrainCards(): number {
        return this._numTrainCards;
    }

    get numDestinationCards(): number {
        return this._numDestinationCards;
    }

    get numRemainingTrains(): number {
        return this._numRemainingTrains;
    }

    get gasLongestRoute(): boolean {
        return this._hasLongestRoute;
    }

    get turnOrder(): number {
        return this._turnOrder;
    }

}
