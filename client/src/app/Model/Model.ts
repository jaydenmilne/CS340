import { User } from './User';
import { GameList } from './GameLIst';
import { GamePreview } from './GamePreview';

export class Model {
    private myUser: User;
    private availableGames: GameList;
    private allGames: GamePreview;

    constructor() {}

    public getMyUser(): User {
        return this.myUser;
    }

    public setMyUser(myUser: User) {
        this.myUser = myUser;
    }

    private getAvailableGames(): GameList {
        return this.availableGames;
    }

    private setAvailableGames(availableGames: GameList) {
        this.availableGames = availableGames;
    }

    private getAllGames(): GamePreview {
        return this.allGames;
    }

    private setAllGames(allGames: GamePreview) {
        this.allGames = allGames;
    }
}
