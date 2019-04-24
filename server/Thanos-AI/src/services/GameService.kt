package services

import CommandRouter
import ProxyServer
import commands.*
import models.Game

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.GameService Handles models.Game functions (starting game, selecting cards, discarding cards, etc.)
 */
class GameService(private val server: ProxyServer, private val cmdRouter: CommandRouter, private val myPlayerId: Int) {
    var game = Game()

    // Sub-services
    private val cardService = CardService(server, cmdRouter)
    private val bankService = BankService(server, cmdRouter, game.bank)
    private val playerService = PlayerService(server, cmdRouter, game.players, myPlayerId)
    private val routeService = RouteService(server, cmdRouter, game.map)

    init {
        this.cmdRouter.registerCallback(START_GAME) { handleStartGame(it as StartGameCommand) }
        this.cmdRouter.registerCallback(DEAL_CARDS) { handleDealCards(it as DealCardsCommand) }
        this.cmdRouter.registerCallback(CHANGE_TURN) { handleChangeTurn(it as ChangeTurnCommand) }
    }


    fun handleStartGame(startGame: StartGameCommand){
        bankService.requestDestinations()
    }

    fun handleDealCards(dealCards: DealCardsCommand){
        cardService.handleDealCards(dealCards)
        if(dealCards.minDestinations != 0){
            // TODO Discard dest cards
        }
    }

    fun handleChangeTurn(changeTurn: ChangeTurnCommand){
        // TODO: If my turn, make a move
    }
}