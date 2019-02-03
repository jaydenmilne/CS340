export class ChatMessage {
    private message: string;
    private gameId: string;
    private userId: string;

    constructor(gameId: string, userId: string, message: string) {
        this.gameId = gameId;
        this.userId = userId;
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

    public setuserId(userId: string) {
        this.userId = userId;
    }
}
