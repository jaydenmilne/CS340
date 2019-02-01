export class ChatMessage{
    private message: string;
    private gameID: string;
    private userID: string;

    constructor(){}

    public getMessage():string {
        return this.message;
    }

    public setMessage(message:string){
        this.message = message;
    }

    public getGameID():string{
        return this.gameID;
    }

    public setGameID(gameID:string){
        this.gameID = gameID;
    }

    public getUserID():string{
        return this.userID;
    }

    public setUserID(userID:string){
        this.userID = userID;
    }
}