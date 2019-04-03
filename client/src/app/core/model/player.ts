import { Color, StyleColor } from './color.enum';

export class Player {
    public color: Color;
    public userId: number;
    public ready: boolean;
    public username: string;

    constructor(player: any) {
        if (!('userId' in player &&
            'ready' in player &&
            'username' in player &&
            'color' in player)) {
            throw new TypeError('Unable to deserialize Player object, ' + JSON.stringify(player));
        }

        this.color = <Color> Color[player.color];
        this.userId = player.userId;
        this.ready = player.ready;
        this.username = player.username;
    }

    public getStyleColor(): StyleColor{
        return  StyleColor[this.color];
    }

    public getCSSColor(attribute: string){
        return { [attribute]: '#' + StyleColor[this.color]}
    }
}

