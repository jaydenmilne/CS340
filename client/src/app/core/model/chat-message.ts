export class ChatMessage {

    constructor(private userId: number, private username: string, private message: string, private sequenceNum: number) {
    }

    public getMessage(): string {
        return this.message;
    }

    public setMessage(message: string) {
        this.message = message;
    }

    public getUserId(): number {
        return this.userId;
    }

    public getUserName(): string {
        return this.username;
    }

    public getSequenceNum() {
        return this.sequenceNum;
    }

    public setUserId(userId: number) {
        this.userId = userId;
    }

    public setUserName(username: string) {
        this.username = username;
    }

    public setSequenceNum(sequenceNum: number) {
        this.sequenceNum = sequenceNum;
    }
}
