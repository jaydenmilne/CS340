import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userId: string;
    private ready: boolean;
    private handle: string;

    constructor(color: Color, userId: string, ready: boolean, handle: string) {
        this.color = color;
        this.userId = userId;
        this.ready = ready;
        this.handle = handle;
    }

    public getColor(): Color {
        return this.color;
    }

    public setColor(color: string) {
        this.color = Color[color.toUpperCase()];
    }

    public getUserID(): string {
        return this.userId;
    }

    public setUserID(userId: string) {
        this.userId = userId;
    }

    public isReady(): boolean {
        return this.ready;
    }

    public setReady(ready: boolean) {
        this.ready = ready;
    }

    public getHandle(): string {
        return this.handle;
    }

    public setHandle(handle: string) {
        this.handle = handle;
    }
}
