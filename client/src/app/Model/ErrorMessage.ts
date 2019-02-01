export class ErrorMessage {
    private message: string;

    constructor() {}

    public getMessage(): string {
        return this.message;
    }

    public setMessage(message: string) {
        this.message = message;
    }
}
