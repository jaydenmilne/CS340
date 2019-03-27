export class PlayerPoint {
    public userId: number;
    public username: string;
    public totalPoints: number;
    public claimedRoutePoints: number;
    public completedDestinationPoints: number;
    public incompleteDestinationPoints: number;
    public longestRoutePoints: number;


    constructor(playerPoint: any) {
        if (!('userId' in playerPoint &&
            'username' in playerPoint &&
            'totalPoints' in playerPoint &&
            'claimedRoutePoints' in playerPoint &&
            'completedDestinationPoints' in playerPoint &&
            'incompleteDestinationPoints' in playerPoint &&
            'longestRoutePoints' in playerPoint)) {
            throw new TypeError('Unable to deserialize PlayerPoint object, ' + JSON.stringify(playerPoint));
        }

        this.userId = playerPoint.userId;
        this.username = playerPoint.username;
        this.totalPoints = playerPoint.totalPoints;
        this.claimedRoutePoints = playerPoint.claimedRoutePoints;
        this.completedDestinationPoints = playerPoint.completedDestinationPoints;
        this.incompleteDestinationPoints = playerPoint.incompleteDestinationPoints;
        this.longestRoutePoints = playerPoint.longestRoutePoints;
    }
}
