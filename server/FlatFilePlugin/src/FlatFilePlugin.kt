import jdk.nashorn.internal.runtime.JSType.toLong
import org.kamranzafar.jtar.TarEntry
import org.kamranzafar.jtar.TarHeader
import org.kamranzafar.jtar.TarInputStream
import org.kamranzafar.jtar.TarOutputStream
import java.io.*
import java.util.*

class TarFlatFile {
    private val filePerms = 508     // chmod 774
    var entry: TarEntry
    var data: ByteArray

    constructor(fileName: String, data: ByteArray) {
        entry = TarEntry(TarHeader.createHeader(fileName, toLong(data.size), Date().time / 1000, false, filePerms))
        this.data = data
    }

    constructor(entry: TarEntry, data: ByteArray) {
        this.entry = entry
        this.data = data
    }

    fun getFileName(): String { return entry.header.name.toString() }
}

class TarWriter(private val tarFile: File) {
    fun writeFiles(files: List<TarFlatFile>) {
        val tarStreamOut = TarOutputStream(tarFile)

        files.forEach {
            tarStreamOut.putNextEntry(it.entry)
            tarStreamOut.write(it.data)
        }
        tarStreamOut.close()
    }

    fun readFiles(): MutableMap<String, TarFlatFile> {
        val files = mutableMapOf<String, TarFlatFile>()
        val tarStreamIn = TarInputStream(BufferedInputStream(FileInputStream(tarFile)))

        var tarFile: TarEntry? = tarStreamIn.nextEntry

        while (tarFile != null) {
            val data = ByteArray(tarFile.size.toInt())
            tarStreamIn.read(data)
            files[tarFile.header.name.toString()] = TarFlatFile(tarFile, data)

            tarFile = tarStreamIn.nextEntry
        }
        tarStreamIn.close()
        return files
    }

    fun clean() {
        tarFile.delete()
        tarFile.createNewFile()
    }
}

class FlatFileStatement {
    private var filesChanged: MutableList<TarFlatFile> = mutableListOf<TarFlatFile>()
    private var filesDeleted: MutableList<String> = mutableListOf<String>()

    fun getFilesChanged(): List<TarFlatFile>{ return filesChanged.toList() }
    fun getFilesDeleted(): List<String>{ return filesDeleted.toList() }

    fun addFile(fileName: String, obj: Serializable) {
        val data = Serializer.serialize(obj)
        this.filesChanged.add(TarFlatFile(fileName, data))
    }

    fun removeFile(fileName: String) {
        filesDeleted.add(fileName)
    }

    fun rollback() {
        this.filesChanged.clear()
        this.filesDeleted.clear()
    }
}

class FlatFileDatabase {
    private var files = mutableMapOf<String, TarFlatFile>()

    fun import(importedFiles: MutableMap<String, TarFlatFile>) {
        this.files = importedFiles
    }

    fun export(): List<TarFlatFile> {
        return this.files.values.toList()
    }

    fun commit(statement: FlatFileStatement) {
        statement.getFilesChanged().forEach { files[it.getFileName()] = it }
        statement.getFilesDeleted().forEach { files.remove(it) }
    }

    fun clean() {
        this.files.clear()
    }

    fun getFolder(path: String): List<TarFlatFile> {
        return files.values.filter { it.getFileName().startsWith(path) }
    }
}

class FlatFilePlugin : IPersistenceManager {
    private val currentDir: String = File(".").canonicalPath
    private val databaseFile: File = File("$currentDir/resources/database/flatfileDB.tar")
    private var statement: FlatFileStatement = FlatFileStatement()
    private var database: FlatFileDatabase = FlatFileDatabase()
    private var tarWriter: TarWriter = TarWriter(databaseFile)

    override fun openTransaction() {
        statement = FlatFileStatement() // Create new statement
    }

    override fun closeTransaction(commit: Boolean) {
        if (!commit) {
            statement.rollback()
        } else {
            database.commit(statement)
            tarWriter.writeFiles(database.export())
        }
        statement = FlatFileStatement()
    }

    override fun getCommandDAO(): ICommandDAO { return FlatFileCommandDAO(statement) }
    override fun getUserDAO(): IUserDAO { return FlatFileUserDAO(statement) }
    override fun getGameDAO(): IGameDAO { return FlatFileGameDAO(statement) }

    override fun initialize(): Boolean {
        if (!databaseFile.exists()) {
            databaseFile.createNewFile()
        }

        database.import(tarWriter.readFiles())
        return true
    }

    fun close() {
        this.closeTransaction(false)
        tarWriter.writeFiles(database.export())
    }

    override fun clear(): Boolean {
        this.database.clean()
        tarWriter.clean()
        return true
    }

    fun getFolder(path: String): List<Serializable> {
        return database.getFolder(path).map { it -> Serializer.deserialize(it.data) }       // deserialize files
    }
}