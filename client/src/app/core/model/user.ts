export class User {
    private userId: string;
    private username: string;
    private authToken: string;

    constructor(user: any) {
        // Construct a User from a user-like object
        if (!('authToken' in user) ||
            !('username' in user)  ||
            !('userId' in user)) {
                throw new TypeError('Unable to deserialize user object, ' + JSON.stringify(user));
            }

        this.authToken = user.authToken;
        this.username = user.username;
        this.userId = user.userId;
    }

    public getUserId(): string {
        return this.userId;
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
