import java.io.Serializable

/**
 * Created by Jordan Gassaway on 4/10/2019.
 * FlatFileUserDAO Used to access users in a Flat File Tar database
 */
class FlatFileUserDAO(private val statement: FlatFileStatement) : IUserDAO {
    override fun persistUser(user: Serializable) {
        statement.addFile(makeFileName(user), user)
    }

    override fun loadUsers(persistanceManager: IPersistanceManager): List<Serializable> {
        if(persistanceManager !is FlatFilePlugin) throw DatabaseException("Incompatible persistence manager!")

        return persistanceManager.getFolder("users")
    }

    private fun makeFileName(user: Serializable): String {
        return "users/%d.user".format(user.hashCode())
    }
}