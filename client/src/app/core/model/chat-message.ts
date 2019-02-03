export class ChatMessage {
    private message: string;
    private gameId: string;
    private userId: string;
    private userName: string;

    constructor(gameId: string, userId: string, userName: string, message: string) {
        this.gameId = gameId;
        this.userId = userId;
        this.userName = userName;
        this.message = message;
    }

    public getMessage(): string {
        return this.message;
    }

    public setMessage(message: string) {
        this.message = message;
    }

    public getgameId(): string {
        return this.gameId;
    }

    public setgameId(gameId: string) {
        this.gameId = gameId;
    }

    public getuserId(): string {
        return this.userId;
    }

    public getUserName(): string {
        return this.userName;
    }

    public setuserId(userId: string) {
        this.userId = userId;
    }

    public setUserName(userName: string) {
        this.userName = userName;
    }
}
