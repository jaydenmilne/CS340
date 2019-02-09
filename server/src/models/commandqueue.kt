package models

import com.google.gson.Gson
import commands.INormalClientCommand

class CommandQueue {

    var commands = mutableListOf<INormalClientCommand>()

    fun push(command: INormalClientCommand) {
        commands.add(command)
    }

    fun pollCommands(): String {
        val rendered = Gson().toJson(commands)
        commands.clear()

        return rendered
    }
}