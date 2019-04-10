/**
 * Created by Jordan Gassaway on 4/9/2019.
 * FlatFilePersistenceManager: Persistence plugin for Flat File (tar) database
 */
import jdk.nashorn.internal.runtime.JSType.toLong
import org.kamranzafar.jtar.TarOutputStream
import org.kamranzafar.jtar.TarEntry
import org.kamranzafar.jtar.TarHeader
import org.kamranzafar.jtar.TarInputStream
import java.io.*
import java.util.*


class FlatFilePersistenceManager {
    private val filePerms = 508     // chmod 774
    private val currentDir: String = File(".").canonicalPath
    private val databaseFile: File = File("$currentDir/resources/database/flatfileDB.tar")
    private var tarStreamOut: TarOutputStream? = null
    private var tarStreamIn: TarInputStream? = null


    fun init(){
        if(!databaseFile.exists()){
            databaseFile.createNewFile()
        }
    }

    fun openRead(){
        if (tarStreamOut != null) close()

        val srcFileOS = FileInputStream(databaseFile)
        tarStreamIn = TarInputStream(BufferedInputStream(srcFileOS))
    }

    fun openWrite(){
        if (tarStreamIn != null) close()

        val destFileOS = FileOutputStream(databaseFile)
        tarStreamOut = TarOutputStream(BufferedOutputStream(destFileOS))
    }

    fun writeToTar(fileName: String, obj: Serializable){
        val bytes = ByteArrayOutputStream()
        val objStream = ObjectOutputStream(bytes)

        // Serialize object
        objStream.writeObject(obj)

        val time = Date().time / 1000
        tarStreamOut!!.putNextEntry(TarEntry(TarHeader.createHeader(fileName, toLong(bytes.size()), time, false, filePerms)))
        tarStreamOut!!.write(bytes.toByteArray())
    }

    fun readFromTar(fileName: String): Any{
        var tarFile: TarEntry? = tarStreamIn!!.nextEntry ?: throw IOException("File %s Not Found! ".format(fileName))

        while (tarFile!!.header.name.toString() != fileName){
            tarFile = tarStreamIn!!.nextEntry ?: throw IOException("File %s Not Found! ".format(fileName))
        }

        val data = ByteArray(tarFile.size.toInt())
        tarStreamIn!!.read(data)
        val bytes = ByteArrayInputStream(data)

        // Deserialize object
        val objStream = ObjectInputStream(bytes)
        return objStream.readObject()
    }

    fun close(){
        tarStreamOut?.close()
        tarStreamIn?.close()

        tarStreamOut = null
        tarStreamIn = null
    }
}