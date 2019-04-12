import com.sun.xml.internal.messaging.saaj.util.ByteInputStream
import jdk.nashorn.internal.runtime.JSType.toLong
import org.kamranzafar.jtar.TarEntry
import org.kamranzafar.jtar.TarHeader
import org.kamranzafar.jtar.TarInputStream
import org.kamranzafar.jtar.TarOutputStream
import java.io.*
import java.util.*

class TarFlatFile(public val entry: TarEntry, public val data: ByteArray){
    fun getFileName(): String { return entry.header.name.toString()}
}

class FlatFileStatement(){
    private var filesChanged: MutableList<TarFlatFile> = mutableListOf<TarFlatFile>()
    private var filesDeleted: MutableList<String> = mutableListOf<String>()
    private val filePerms = 508     // chmod 774

    fun getFilesChanged(): List<TarFlatFile>{ return filesChanged.toList() }
    fun getFilesDeleted(): List<String>{ return filesDeleted.toList() }

    fun addFile(fileName: String, obj: Serializable){
        val bytes = ByteArrayOutputStream()
        val objStream = ObjectOutputStream(bytes)

        // Serialize object
        objStream.writeObject(obj)

        val time = Date().time / 1000
        val entry = TarEntry(TarHeader.createHeader(fileName, toLong(bytes.size()), time, false, filePerms))
        val data = bytes.toByteArray()
        this.filesChanged.add( TarFlatFile(entry, data))
    }

    fun removeFile(fileName: String){
        filesDeleted.add(fileName)
    }

    fun rollback(){
        this.filesChanged.clear()
        this.filesDeleted.clear()
    }
}

class FlatFileDatabase(private val databaseFile: File){
    var files = mutableMapOf<String, TarFlatFile>()

    fun load(){
        val tarStreamIn = TarInputStream(BufferedInputStream(FileInputStream(databaseFile)))

        var tarFile: TarEntry? = tarStreamIn.nextEntry

        while (tarFile != null){
            val data = ByteArray(tarFile.size.toInt())
            tarStreamIn.read(data)
            files[tarFile.header.name.toString()] = TarFlatFile(tarFile, data)

            tarFile = tarStreamIn.nextEntry
        }
        tarStreamIn.close()
    }

    fun save() {
        val tarStreamOut = TarOutputStream(databaseFile)

        files.values.forEach {
            tarStreamOut.putNextEntry(it.entry)
            tarStreamOut.write(it.data)
        }
        tarStreamOut.close()
    }

    fun commit(statement: FlatFileStatement){
        statement.getFilesChanged().forEach{ files[it.getFileName()] = it }
        statement.getFilesDeleted().forEach{ files.remove(it) }
        save()
    }

    fun clean(){
        this.files.clear()
    }

    fun getFolder(path: String): List<TarFlatFile>{
        return files.values.filter { it.getFileName().startsWith(path) }
    }
}

class FlatFilePlugin : IPersistanceManager {
    private val currentDir: String = File(".").canonicalPath
    private val databaseFile: File = File("$currentDir/resources/database/flatfileDB.tar")
    private var statement: FlatFileStatement = FlatFileStatement()
    private var database: FlatFileDatabase = FlatFileDatabase(databaseFile)

    override fun openTransaction() {
        statement = FlatFileStatement() // Create new statement
        return
    }

    override fun closeTransaction(commit: Boolean) {
        if(!commit){
            statement.rollback()
        }
        else {
            database.commit(statement)
        }
        statement = FlatFileStatement()
    }

    override fun getCommandDAO(): ICommandDAO { return FlatFileCommandDAO(statement) }
    override fun getUserDAO(): IUserDAO { return FlatFileUserDAO(statement) }
    override fun getGameDAO(): IGameDAO { return FlatFileGameDAO(statement) }

    override fun initialize(): Boolean {
        if(!databaseFile.exists()){
            databaseFile.createNewFile()
        }

        database.load()
        return true
    }

    fun close(){
        this.closeTransaction(false)
        this.database.save()
    }

    fun clean(){
        this.database.clean()
        this.databaseFile.delete()
        this.databaseFile.createNewFile()
    }

    fun getFolder(path: String): List<Serializable>{
        val files = this.database.getFolder(path)

        // Deserialize objects
        return files.map { it -> ObjectInputStream(ByteArrayInputStream(it.data)).readObject() as Serializable }
    }
}