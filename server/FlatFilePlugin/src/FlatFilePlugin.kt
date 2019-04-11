import IPersistanceManager

class FlatFilePlugin : IPersistanceManager {
    override fun speak() {
        System.out.println("Cow says moo")
    }
}