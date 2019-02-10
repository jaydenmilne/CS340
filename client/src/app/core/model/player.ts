import { Color } from './color.enum';

export class Player {
    private color: Color;
    private userId: string;
    private ready: boolean;
    private username: string;

    constructor(player: any) {
        if (!('userId' in player ||
            'ready' in player ||
            'username' in player ||
            'color' in player)) {
            throw new TypeError('Unable to deserialize Player object, ' + JSON.stringify(player));
        }

        this.color = <Color> Color[player.color];
        this.userId = player.userId;
        this.ready = player.ready;
        this.username = player.username;
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
        return this.username;
    }

    public setHandle(handle: string) {
        this.username = handle;
    }
}
