package test

import FlatFilePlugin
import kotlin.test.assertEquals


fun testReadWrite() {
    val db = FlatFilePlugin()
    db.initialize()
    db.openWrite()
    val s = "hello world"
    db.writeToTar("newFile.txt", s)

    db.openRead()
    val x:String = db.readFromTar("newFile.txt") as String
    assertEquals(s, x)
    db.close()
}

fun main(args: Array<String>){
    testReadWrite()
}