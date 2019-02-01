export class User {
    private ID: string;
    private username: string;
    private authToken: string;

    constructor() {}

    public getID(): string {
        return this.ID;
    }

    public setColor(ID: string) {
        this.ID = ID;
    }

    public getUsername(): string {
        return this.username;
    }

    public setUsername(username: string) {
        this.username = username;
    }

    public getAuthToken(): string {
        return this.authToken;
    }

    public setAuthToken(authToken: string) {
        this.authToken = authToken;
    }
}
