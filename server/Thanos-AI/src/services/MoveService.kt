package services

import models.Game
import MaterialType
import models.Route

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * MoveService: Responsible for selecting the next move. Houses the brains of the AI.
 */
class MoveService(private val game: Game, private val cardService: CardService, private val bankService: BankService,
                  private val playerService: PlayerService, private val routeService: RouteService, private val turnService: TurnService) {

    private val moveThread = MoveThread(turnService) { chooseNextMove() }

    init {
        moveThread.start()
    }

    fun chooseNextMove(){
        drawFaceUpCard(game.bank.faceUpCards.first().type)
        drawFaceUpCard(game.bank.faceUpCards.first().type)
    }

    fun drawFaceUpCard(card: MaterialType){
        if (card == MaterialType.INFINITY_GAUNTLET && turnService.canDrawWild()){
            bankService.drawFaceUpCard(card)
            turnService.onDrawWild()
            return
        } else if(turnService.canDrawShards()){
            bankService.drawFaceUpCard(card)
            turnService.onDrawFaceUp()
            while(!bankService.drawIsComplete());    // Wait for update bank command
        }
    }

    fun requestDestinationCards(){
        if(turnService.canDrawDestinations()){
            bankService.requestDestinations()
        }
    }

    fun drawFromDeck(){
        if(turnService.canDrawShards()){
            bankService.drawCardFromDeck()
        }
    }

    fun selectDestinationCards(minRequired: Int){
        cardService.selectDestinationCards(cardService.stagedDestCards.subList(0,2), cardService.stagedDestCards.subList(2,3))
    }

    fun claimRoute(route: Route){
        if(turnService.canClaimRoutes()){
            routeService.claimRoute(route.routeId, cardService.getCardsForRoute(route))
        }
    }

    fun close() {
        moveThread.exit = true
    }
}

private class MoveThread( private val turnService: TurnService, private val doMove: () -> Unit): Thread("Move Thread") {
    var enabled = false
    var period:Long = 500
    var exit = false
    private val debugMode = true

    override fun run() {
        super.run()
        while(!exit){
            if(turnService.isMyTurn()){
                if(debugMode) println("Making Move...")
                doMove()
            }
            sleep(period)
        }
    }
}