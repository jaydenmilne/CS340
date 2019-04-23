package commands

import models.User

interface IRegisterServerCommand : IRegisterCommand {
    override val command: String
    fun execute(): IRegisterClientCommand
}

interface INormalServerCommand : INormalCommand {
    override val command: String
    fun execute(user: User)
}