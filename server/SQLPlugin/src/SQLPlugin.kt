import IPersistanceManager

class SQLPlugin : IPersistanceManager {

    override fun speak() {
        System.out.println("Duck says quack")
    }

}