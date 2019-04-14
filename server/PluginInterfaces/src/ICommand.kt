import java.io.Serializable

interface ICommand: Serializable {
    val command: String
}