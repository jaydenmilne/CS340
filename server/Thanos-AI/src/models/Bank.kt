package models

import IShardCard
import commands.UpdateBankCommand

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * Bank: Keeps track of bank information
 */
class Bank {
    var faceUpCards: List<IShardCard> = listOf()
    var shardCardDeckSize: Int = 0
    var destinationCardDeckSize: Int = 0
}