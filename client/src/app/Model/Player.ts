export class Player{
    private color:string;
    private userID:string;
    private ready:boolean;
    private handel:string;

    constructor(){}

    public getColor():string{
        return this.color;
    }

    public setColor(color:string){
        this.color = color;
    }

    public getUserID():string{
        return this.userID;
    }

    public setUserID(userID:string){
        this.userID = userID;
    }

    public isReady():boolean{
        return this.ready;
    }

    public setReady(ready:boolean){
        this.ready = ready;
    }

    public getHandle():string{
        return this.handel;
    }

    public setHandle(handle:string){
        this.handel = handle;
    }
}