import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileUserDAO Used to access users in a Flat File Tar database
 */
class FlatFileUserDAO(persistenceManager: IPersistenceManager) : IUserDAO(persistenceManager) {
    override fun persistUser(user: IUser) {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        persistenceManager.addFile(makeFileName(user), user)
    }

    override fun loadUsers(): List<IUser> {
        if(persistenceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistenceManager.getFolder("users").map{ it -> it as IUser}
    }

    private fun makeFileName(user: IUser): String {
        return "users/%d.user".format(user.userId)
    }
}