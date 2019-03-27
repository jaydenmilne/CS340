export class ChatMessage {
    private userId: number;
    private username: string;
    private message: string;
    private sequenceNum: number;

    constructor(chatMessage: any) {
        if (!('message' in chatMessage &&
              'userId' in chatMessage &&
              'username' in chatMessage &&
              'sequenceNum' in chatMessage)) {
            throw new TypeError('Unable to deserialize chatMessage object, ' + JSON.stringify(chatMessage));
        }

        this.userId = chatMessage.userId;
        this.message = chatMessage.message;
        this.sequenceNum = chatMessage.sequenceNum;
        this.username = chatMessage.username;
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
