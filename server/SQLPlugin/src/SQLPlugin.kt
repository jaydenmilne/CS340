import IPersistanceManager

class SQLPlugin : IPersistanceManager {
    override fun initialize() {
        System.out.println("Duck says quack")
    }

}