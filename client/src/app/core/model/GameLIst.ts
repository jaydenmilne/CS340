export class GameList {
    private selectedGame: string;
    private gameIDs: string [];

    constructor(gameIDs: string[]) {
        this.gameIDs = gameIDs;
    }

    public getSelectedGame(): string {
        return this.selectedGame;
    }

    public setSelectedGame(selectedGame: string) {
        this.selectedGame = selectedGame;
    }

    public getGameIDs(): string [] {
        return this.gameIDs;
    }

    public setGameIDs(gameIDs: string []) {
        this.gameIDs = gameIDs;
    }

}
