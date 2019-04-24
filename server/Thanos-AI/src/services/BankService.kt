package services

import CommandRouter
import MaterialType
import ProxyServer
import commands.*
import models.Bank

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.BankService: Handles Bank updates and drawing cards.
 */
class BankService(private val server: ProxyServer, private val cmdRouter: CommandRouter, private val bank: Bank) {
    init {
    }


    fun requestDestinations(){
        server.executeCommand(IRequestDestinationsCommand())
    }

    fun drawFaceUpCard(card: MaterialType){
        server.executeCommand(IDrawShardCardCommand(card.material))
    }

    fun drawCardFromDeck(){
        server.executeCommand(IDrawShardCardCommand("deck"))
    }

    fun handleUpdateBank(updateBank: UpdateBankCommand){
        bank.faceUpCards = updateBank.faceUpCards
        bank.shardCardDeckSize = updateBank.shardDrawPileSize
        bank.destinationCardDeckSize = updateBank.destinationPileSize
    }
}