package commands

import ICommand
import IDestinationCard
import IShardCard

open class ICreateGameCommand(protected var name: String = ""): ICommand {
    override val command = CREATE_GAME
}

open class IJoinGameCommand(protected val gameId: String = "") : ICommand {
    override val command = JOIN_GAME
}

open class IListGamesCommand : ICommand {
    override val command = LIST_GAMES
}

open class ILeaveGameCommand(protected val gameId: String = "") : ICommand {
    override val command = LEAVE_GAME
}

open class ILoginCommand(protected val username: String = "", protected val password: String = "") : ICommand {
    override val command = LOGIN
}

open class IPlayerReadyCommand(protected val gameId: String = "", protected val playerIsReady: Boolean = false) : ICommand {
    override val command = PLAYER_READY
}

open class IRegisterCommand(protected val username: String = "", protected val password: String = "") : ICommand {
    override val command = REGISTER
}

open class IChangeColorCommand(protected val gameId: String = "", protected var newColor: String = "") : ICommand {
    override val command = CHANGE_COLOR
}

open class IRequestDestinationsCommand : ICommand {
    override val command = REQUEST_DESTINATIONS
}

open class IRejoinGameCommand : ICommand {
    override val command = REJOIN_GAME
}

open class IDiscardDestinationsCommand(protected val discardedDestinations: List<IDestinationCard> = listOf<IDestinationCard>()) : ICommand {
    override val command = DISCARD_DESTINATIONS
}

open class IClaimRouteCommand(protected val routeId: String = "", protected val shardsUsed: List<IShardCard> = listOf<IShardCard>()) : ICommand {
    override val command = CLAIM_ROUTE
}

open class IDrawShardCardCommand(protected var card: String = "") : ICommand {
    override val command = DRAW_SHARD_CARD
}

open class ISkipTurnCommand : ICommand {
    override val command = SKIP_TURN
}