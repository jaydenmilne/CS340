package test

import FlatFilePersistenceManager
import kotlin.test.assertEquals


fun testReadWrite() {
    val db = FlatFilePersistenceManager()
    db.init()
    db.openWrite()
    val s: String = "hello world"
    db.writeToTar("newFile.txt", s)

    db.openRead()
    val x:String = db.readFromTar("newFile.txt") as String
    assertEquals(s, x)
    db.close()
}

fun main(args: Array<String>){
    testReadWrite()
}