import { User } from './user';
import { GameList } from './game-list';
import { GamePreview } from './game-preview';

export class Model {
    private myUser: User;
    private availableGames: GameList;
    private allGames: GamePreview[];

    constructor() {}

    public getMyUser(): User {
        return this.myUser;
    }

    public setMyUser(myUser: User) {
        this.myUser = myUser;
    }

    public getAvailableGames(): GameList {
        return this.availableGames;
    }

    public setAvailableGames(availableGames: GameList) {
        this.availableGames = availableGames;
    }

    public getAllGames(): GamePreview[] {
        return this.allGames;
    }

    public setAllGames(allGames: GamePreview[]) {
        this.allGames = allGames;
    }
}
