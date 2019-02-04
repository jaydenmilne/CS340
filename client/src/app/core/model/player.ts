import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userId: string;
    private ready: boolean;
    private name: string;

    constructor(color: Color, userId: string, ready: boolean, name: string) {
        this.color = color;
        this.userId = userId;
        this.ready = ready;
        this.name = name;
    }

    public setColor(color: string) {
        this.color = Color[color.toUpperCase()];
    }

    public getColor(): Color {
        return this.color;
    }

    public getuserId(): string {
        return this.userId;
    }

    public setuserId(userId: string) {
        this.userId = userId;
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