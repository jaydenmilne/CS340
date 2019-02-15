export class ChatMessage {
    private message: string;
    private gameId: number;
    private userId: number;
    private userName: string;

    constructor(gameId: number, userId: number, userName: string, message: string) {
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

    public getgameId(): number {
        return this.gameId;
    }

    public setgameId(gameId: number) {
        this.gameId = gameId;
    }

    public getuserId(): number {
        return this.userId;
    }

    public getUserName(): string {
        return this.userName;
    }

    public setuserId(userId: number) {
        this.userId = userId;
    }

    public setUserName(userName: string) {
        this.userName = userName;
    }
}
