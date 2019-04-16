import java.io.Serializable

class SQLUserDAO(persistenceManager: IPersistenceManager) : IUserDAO(persistenceManager) {
    override fun persistUser(user: IUser) {
    }

    override fun loadUsers(): List<IUser> {
        var users = listOf<IUser>()
        return users
    }
}