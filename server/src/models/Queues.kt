package models

import java.io.Serializable
import com.google.gson.Gson
import commands.INormalClientCommand
import commands.IRegisterClientCommand

/**
 * Contains the client commands that a particular user needs to properly know game state.
 */
class CommandQueue : Serializable {

    var commands = mutableListOf<INormalClientCommand>()

    fun push(command: INormalClientCommand) {
        commands.add(command)
    }

    /**
     * Render this CommandQueue in JSON, clearing the current queue of commands in the process.
     */
    fun render(): String {
        val rendered = Gson().toJson(this)
        commands.clear()

        return rendered
    }
}

/**
 * Temporarily used in the login and registration logic.
 */
class RegisterCommandQueue : Serializable  {

    var commands = mutableListOf<IRegisterClientCommand>()

    fun push(command: IRegisterClientCommand) {
        commands.add(command)
    }

}