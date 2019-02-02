import { GamePreview } from './game-preview';

export class GameList {
    private selectedGame: GamePreview;
    private games: GamePreview [];

    constructor(games: GamePreview []) {
        this.games = games;
    }

    public getSelectedGame(): GamePreview {
        return this.selectedGame;
    }


    public setSelectedGameByID(selectedGameID: string) {
        const gamePreview: GamePreview = this.games.find(g => g.getID() === selectedGameID);
        this.selectedGame = gamePreview;
    }

    public setSelectedGame(selectedGame: GamePreview) {
        this.selectedGame = selectedGame;
    }

    public getGames(): GamePreview [] {
        return this.games;
    }

    public setGames(games: GamePreview []) {
        this.games = games;
    }

    public isSelectedGame(gameID: string): boolean {
        if (this.selectedGame === undefined) {
            return false;
        }
        return this.selectedGame.getID() === gameID;
    }
}
