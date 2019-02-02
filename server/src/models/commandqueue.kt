package models

import command.ICommand
import java.util.*

class CommandQueue(var commands: Queue<ICommand>) {

    fun executeNextCommand() {
        commands.peek().execute()
        commands.remove()
    }
}