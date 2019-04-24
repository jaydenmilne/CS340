package test

import Thanos

fun main(args: Array<String>){
    val ai = Thanos("Thanos001", "hunter2")
    try{
        ai.login()
        ai.joinGame(8)
    } finally{
//        ai.close()
    }

}