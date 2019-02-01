export class GamePreview {
    private name: string;
    private ID: string;
    private started: boolean;

    constructor() {}

    public getName(): string {
        return this.name;
    }

    public setColor(name: string) {
        this.name = name;
    }

    public getID(): string {
        return this.ID;
    }

    public setUserID(ID: string) {
        this.ID = ID;
    }

    public isStarted(): boolean {
        return this.started;
    }

    public setStarted(started: boolean) {
        this.started = started;
    }

}
