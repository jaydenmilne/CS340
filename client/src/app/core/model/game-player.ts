import { Color, StyleColor, ColorAvatar } from './color.enum';

export class GamePlayer {
    public color: Color;
    public userId: number;
    public ready: boolean;
    public username: string;
    public points: number;
    public numTrainCards: number;
    public numDestinationCards: number;
    public numRemainingTrains: number;
    public hasLongestRoute: boolean;
    public longestRouteLength: number;
    public turnOrder: number;

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
            !('longestRouteLength' in gamePlayer) ||
            !('turnOrder' in gamePlayer)) {
            throw new TypeError('Unable to deserialize GamegamePlayer object, ' + JSON.stringify(gamePlayer));
        }

        this.color = <Color> Color[gamePlayer.color];
        this.userId = gamePlayer.userId;
        this.ready = gamePlayer.ready;
        this.username = gamePlayer.username;
        this.points = gamePlayer.points;
        this.numTrainCards = gamePlayer.numTrainCards;
        this.numDestinationCards = gamePlayer.numDestinationCards;
        this.numRemainingTrains = gamePlayer.numRemainingTrains;
        this.hasLongestRoute = gamePlayer.hasLongestRoute;
        this.longestRouteLength = gamePlayer.longestRouteLength;
        this.turnOrder = gamePlayer.turnOrder;
    }

    public setColor(color: string) {
        this.color = Color[color.toUpperCase()];
    }

    public getStyleColor(): StyleColor{
        return  StyleColor[this.color];
    }

    public getCSSColor(attribute: string){
        return { [attribute]: '#' + StyleColor[this.color]};
    }

    public getAvatar(){
        return ColorAvatar[this.color];
    }
}
