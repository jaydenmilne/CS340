package models
import java.io.Serializable

class Message(val userId: Int, val username: String, val message: String, var sequenceNum: Int, var isEvent: Boolean): Serializable {
    constructor() : this(-1, "", "", -1, false)
}
