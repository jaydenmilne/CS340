package commands

interface ICommand {
    val type: String
    fun execute()
}

interface IServerCommand : ICommand {
    override val type: String
    override fun execute()
}

interface IClientCommand : ICommand {
    override val type: String
    override fun execute()
}