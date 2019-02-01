export interface Command {
    command : string;
}

export interface ICommandArray {
    commands : Array<Command>;
}

export class CommandArray implements ICommandArray {
    public constructor (public commands : Array<Command>) {};
}