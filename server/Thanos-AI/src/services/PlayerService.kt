package services

import ICommandRouter
import GamePlayerDTO
import IProxyServer
import commands.*

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * services.PlayerService: Handles updates to player
 */
class PlayerService(private val server: IProxyServer, private val cmdRouter: ICommandRouter, private val players: MutableSet<GamePlayerDTO>, val myPlayerId: Int) {
    var myPlayer: GamePlayerDTO? = null

    init {
        this.cmdRouter.registerCallback(UPDATE_PLAYER) { handleUpdatePlayer(it as UpdatePlayerCommand) }
    }


    private fun updateMyPlayer(){
        myPlayer = players.firstOrNull { it.userId == myPlayerId }
    }

    fun handleUpdatePlayer(updatePlayer: UpdatePlayerCommand){
        val player = players.firstOrNull { it.userId == updatePlayer.gamePlayer.userId }

        if(player != null){
            players.remove(player)
        }
        players.add(updatePlayer.gamePlayer)
        updateMyPlayer()
    }
}