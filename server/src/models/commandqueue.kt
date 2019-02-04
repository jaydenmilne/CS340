package models

import com.google.gson.Gson
import commands.IClientCommand

class CommandQueue {

    var commands = mutableListOf<IClientCommand>()

    fun push(command: IClientCommand) {
        commands.add(command)
    }

    fun pollCommands(): String {
        var rendered = Gson().toJson(commands)
        commands.clear()

        return rendered
    }
}