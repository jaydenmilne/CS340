import java.io.*

class Serializer {
    companion object {
        fun serialize(obj: Serializable): ByteArray {
            val bytes = ByteArrayOutputStream()
            val objStream = ObjectOutputStream(bytes)

            objStream.writeObject(obj)
            return bytes.toByteArray()
        }

        fun deserialize(data: ByteArray): Serializable {
            val objStream = ObjectInputStream(ByteArrayInputStream(data))
            return objStream.readObject() as Serializable
        }
    }
}