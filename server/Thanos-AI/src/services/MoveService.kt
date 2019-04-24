package services

import models.Game

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * MoveService: Responsible for selecting the next move. Houses the brains of the AI.
 */
class MoveService(private val game: Game, private val cardService: CardService, private val bankService: BankService,
                  private val playerService: PlayerService, private val routeService: RouteService, private val turnService: TurnService) {

    fun chooseNextMove(){
        if(turnService.canDrawShards()){
            bankService.drawFaceUpCard(game.bank.faceUpCards.first().type)
        }
    }

    fun selectDestinationCards(minRequired: Int){
        cardService.selectDestinationCards(cardService.stagedDestCards.subList(0,2), cardService.stagedDestCards.subList(2,3))
    }
}