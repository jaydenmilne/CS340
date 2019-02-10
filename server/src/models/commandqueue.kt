package models

import com.google.gson.Gson
import commands.INormalClientCommand
import commands.IRegisterClientCommand

class CommandQueue {

    var commands = mutableListOf<INormalClientCommand>()

    fun push(command: INormalClientCommand) {
        commands.add(command)
    }

    fun pollCommands(): String {
        val rendered = Gson().toJson(this)
        commands.clear()

        return rendered
    }
}

class RegisterCommandQueue {

    var commands = mutableListOf<IRegisterClientCommand>()

    fun push(command: IRegisterClientCommand) {
        commands.add(command)
    }

}