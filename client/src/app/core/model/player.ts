import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userID: string;
    private ready: boolean;
    private name: string;

    constructor(color: Color, userID: string, ready: boolean, name: string) {
        this.color = color;
        this.userID = userID;
        this.ready = ready;
        this.name = name;
    }

    public setColor(color: string) {
        this.color = Color[color.toUpperCase()];
    }

    public getColor(): Color {
        return this.color;
    }

    public getUserID(): string {
        return this.userID;
    }

    public setUserID(userID: string) {
        this.userID = userID;
    }

    public isReady(): boolean {
        return this.ready;
    }

    public setReady(ready: boolean) {
        this.ready = ready;
    }

    public getName(): string {
        return this.name;
    }

    public setHandle(handle: string) {
        this.name = handle;
    }
}
