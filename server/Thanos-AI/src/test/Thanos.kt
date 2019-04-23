package test

import Thanos

fun main(args: Array<String>){
    val ai = Thanos()
    ai.login("bob3", "hunter2")
    ai.processCommands()
}