package plugin
import IPersistanceManager

class NullPersistenceManager : IPersistanceManager {
    override fun speak() {
        System.out.println("Null says")
    }
}