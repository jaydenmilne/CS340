package services

import models.Game

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * MoveService: Responsible for selecting the next move. Houses the brains of the AI.
 */
class MoveService(private val game: Game, private val cardService: CardService, private val bankService: BankService,
                  private val playerService: PlayerService, private val routeService: RouteService, private val turnService: TurnService) {

    private val moveThread = MoveThread(turnService) { chooseNextMove() }
    @Volatile private var bankUpdated = false

    fun onUpdateBank(){
        bankUpdated = true
    }

    fun chooseNextMove(){
        if(turnService.canDrawShards()){
            val card  = game.bank.faceUpCards.first()
            bankUpdated = false
            bankService.drawFaceUpCard(card.type)
            if (card.type == MaterialType.INFINITY_GAUNTLET){
                turnService.onDrawWild()
                return
            } else {
                turnService.onDrawFaceUp()
                while(!bankUpdated);    // Wait for update bank command
                bankService.drawFaceUpCard(game.bank.faceUpCards.first().type)
            }
        }
    }

    fun selectDestinationCards(minRequired: Int){
        cardService.selectDestinationCards(cardService.stagedDestCards.subList(0,2), cardService.stagedDestCards.subList(2,3))
    }


    init {
        moveThread.start()
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