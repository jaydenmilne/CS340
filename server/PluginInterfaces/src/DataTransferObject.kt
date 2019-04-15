import java.io.Serializable

class serializedCmdDTO(val command: ICommand,
                       val userId: Int) : Serializable