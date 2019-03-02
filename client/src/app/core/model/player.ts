import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userId: number;
    private ready: boolean;
    private username: string;

    constructor(player: any) {
        if (!('userId' in player ||
            'ready' in player ||
            'username' in player ||
            'color' in player)) {
            throw new TypeError('Unable to deserialize Player object, ' + JSON.stringify(player));
        }

        this.color = <Color> Color[player.color];
        this.userId = player.userId;
        this.ready = player.ready;
        this.username = player.username;
    }

    public setColor(color: string) {
        this.color = Color[color.toUpperCase()];
    }

    public getColor(): Color {
        return this.color;
    }

    public getuserId(): number {
        return this.userId;
    }

    public setuserId(userId: number) {
        this.userId = userId;
    }

    public isReady(): boolean {
        return this.ready;
    }

    public setReady(ready: boolean) {
        this.ready = ready;
    }

    public getName(): string {
        return this.username;
    }

    public setHandle(handle: string) {
        this.username = handle;
    }
}

export class GamePlayer {
    private userId: Number;
    private username: String;
    private color: Color;
    private ready: Boolean;
    private points: Number;
    private numTrainCards: Number;
    private numDestinationCards: Number;
    private numRemainingTrains: Number;
    private hasLongestRoute: Boolean;
    private turnOrder: Number;

    constructor(gamePlayer: any) {
        if (!('userId' in gamePlayer ||
            'username' in gamePlayer ||
            'color' in gamePlayer ||
            'ready' in gamePlayer ||
            'points' in gamePlayer ||
            'numTrainCards' in gamePlayer ||
            'numDestinationCards' in gamePlayer ||
            'numRemainingTrains' in gamePlayer ||
            'hasLongestRoute' in gamePlayer ||
            'turnOrder' in gamePlayer)) {
            throw new TypeError('Unable to deserialize gamePlayer object, ' + JSON.stringify(gamePlayer));
        }

        this.userId = gamePlayer.userId;
        this.username = gamePlayer.username;
        this.color = <Color> Color[gamePlayer.color];
        this.ready = gamePlayer.ready;
        this.points = gamePlayer.points;
        this.numTrainCards = gamePlayer.numTrainCards;
        this.numDestinationCards = gamePlayer.numDestinationCards;
        this.numRemainingTrains = gamePlayer.numRemainingTrains;
        this.hasLongestRoute = gamePlayer.hasLongestRoute;
        this.turnOrder = gamePlayer.turnOrder;
    }

    public setUserId(userId: Number) {
        this.userId = userId
    }

    public getUserId(): Number {
        return this.userId;
    }

    public setUsername(username: String) {
        this.username = username;
    }

    public getUsername(): String {
        return this.username;
    }

    public setColor(color: Color) {
        return this.color;
    }

    public getColor(): Color {
        return this.color;
    }

    public setReady(ready: Boolean) {
        return this.ready;
    }

    public getReady(): Boolean {
        return this.ready;
    }

    public setPoints(points: Number) {
        this.points = points;
    }

    public getPoints(): Number {
        return this.points;
    }

    public setNumTrainCards(numTrainCards: Number) {
        this.numTrainCards = numTrainCards;
    }

    public getNumTrainCards(): Number {
        return this.numTrainCards;
    }

    public setNumDestinationCards(numDestinationCards: Number) {
        this.numDestinationCards = numDestinationCards;
    }

    public getNumDestinationCards(): Number {
        return this.numDestinationCards;
    }

    public setNumRemainingTrains(numRemainingTrains: Number) {
        this.numRemainingTrains = numRemainingTrains;
    }

    public getNumRemainingTrains(): Number {
        return this.numRemainingTrains;
    }

    public setHasLongestRoute(hasLongestRoute: Boolean) {
        this.hasLongestRoute = hasLongestRoute;
    }

    public getHasLongestRoute(): Boolean {
        return this.hasLongestRoute;
    }

    public setTurnOrder(turnOrder: Number) {
        this.turnOrder = turnOrder;
    }

    public getTurnOrder(): Number {
        return this.turnOrder;
    }
}
