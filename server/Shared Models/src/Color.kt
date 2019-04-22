import java.io.Serializable

enum class Color(val rgb: String)  : Serializable {
    YELLOW("fdd835"),
    GREEN("66bb6a"),
    BLUE("1976d2"),
    PURPLE("7b1fa2"),
    RED("d32f2f"),
    ORANGE("ff5722");
}