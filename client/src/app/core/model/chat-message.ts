export class ChatMessage {
    private userId: number;
    private username: string;
    private message: string;
    private sequenceNum: number;
    private isEvent: boolean;

    constructor(chatMessage: any) {
        if (!('message' in chatMessage &&
              'userId' in chatMessage &&
              'username' in chatMessage &&
              'sequenceNum' in chatMessage &&
              'isEvent' in chatMessage)) {
            throw new TypeError('Unable to deserialize chatMessage object, ' + JSON.stringify(chatMessage));
        }

        this.userId = chatMessage.userId;
        this.message = chatMessage.message;
        this.sequenceNum = chatMessage.sequenceNum;
        this.username = chatMessage.username;
        this.isEvent = chatMessage.isEvent;
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

    public getIsEvent(): boolean {
        return this.isEvent
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

    public setIsEvent(isEvent: boolean) {
        this.isEvent = isEvent
    }
}
