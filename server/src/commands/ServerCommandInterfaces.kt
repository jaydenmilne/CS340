package commands

import models.User

interface IRegistrationServerCommand : IRegistrationCommand {
    override val command: String
    fun execute(): IRegistrationClientCommand
}

interface INormalServerCommand : INormalCommand {
    override val command: String
    fun execute(user: User)
}