import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userId: number;
    private ready: boolean;
    private username: string;

    constructor(player: any) {
        if (!('userId' in player &&
            'ready' in player &&
            'username' in player &&
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
    private userId: number;
    private username: string;
    private color: Color;
    private ready: boolean;
    private points: number;
    private numTrainCards: number;
    private numDestinationCards: number;
    private numRemainingTrains: number;
    private hasLongestRoute: boolean;
    private turnOrder: number;

    constructor(gamePlayer: any) {
        if (!('userId' in gamePlayer &&
            'username' in gamePlayer &&
            'color' in gamePlayer &&
            'ready' in gamePlayer &&
            'points' in gamePlayer &&
            'numTrainCards' in gamePlayer &&
            'numDestinationCards' in gamePlayer &&
            'numRemainingTrains' in gamePlayer &&
            'hasLongestRoute' in gamePlayer &&
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

    public setUserId(userId: number) {
        this.userId = userId
    }

    public getUserId(): number {
        return this.userId;
    }

    public setUsername(username: string) {
        this.username = username;
    }

    public getUsername(): string {
        return this.username;
    }

    public setColor(color: Color) {
        return this.color;
    }

    public getColor(): Color {
        return this.color;
    }

    public setReady(ready: boolean) {
        return this.ready;
    }

    public getReady(): boolean {
        return this.ready;
    }

    public setPoints(points: number) {
        this.points = points;
    }

    public getPoints(): number {
        return this.points;
    }

    public setNumTrainCards(numTrainCards: number) {
        this.numTrainCards = numTrainCards;
    }

    public getNumTrainCards(): number {
        return this.numTrainCards;
    }

    public setNumDestinationCards(numDestinationCards: number) {
        this.numDestinationCards = numDestinationCards;
    }

    public getNumDestinationCards(): number {
        return this.numDestinationCards;
    }

    public setNumRemainingTrains(numRemainingTrains: number) {
        this.numRemainingTrains = numRemainingTrains;
    }

    public getNumRemainingTrains(): number {
        return this.numRemainingTrains;
    }

    public setHasLongestRoute(hasLongestRoute: boolean) {
        this.hasLongestRoute = hasLongestRoute;
    }

    public getHasLongestRoute(): boolean {
        return this.hasLongestRoute;
    }

    public setTurnOrder(turnOrder: number) {
        this.turnOrder = turnOrder;
    }

    public getTurnOrder(): number {
        return this.turnOrder;
    }
}
