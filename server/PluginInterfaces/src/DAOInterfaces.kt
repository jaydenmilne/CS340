interface ICommandDAO {
    fun persistCommand(command: ICommand, gameID: Int)
    fun loadCommands(): List<ICommand>
}

interface IGameDAO {
    fun persistGame(game: Game)
    fun loadGames(): List<Game>
}

interface IUserDAO {
    fun persistUser(user: User)
    fun loadUsers(): List<User>
}