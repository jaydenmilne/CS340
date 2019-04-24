package services

import commands.ChangeTurnCommand

/**
 * Created by Jordan Gassaway on 4/23/2019.
 * TurnService Keeps track of who's turn it is
 */

class TurnService(private val myPlayerId: Int){
    //TODO: Port from Web client
    private var state: ITurnState = NotMyTurn(this)

    fun isMyTurn(): Boolean { return state.isMyTurn() }
    fun canDrawShards(): Boolean { return state.canDrawShards() }
    fun canDrawWild(): Boolean { return state.canDrawWild() }
    fun canDrawDestinations(): Boolean { return state.canDrawDestinations() }
    fun canClaimRoutes(): Boolean { return state.canClaimRoutes() }

    fun handleChangeTurn(changeTurn: ChangeTurnCommand){
        if (changeTurn.userId == myPlayerId){
            state = MyTurn(this)
        } else {
            state = NotMyTurn(this)
        }

    }

    fun setNextState(newState: ITurnState){
        state = newState
    }
}

interface ITurnState {
    fun isMyTurn(): Boolean
    fun canDrawShards(): Boolean
    fun canDrawWild(): Boolean
    fun canDrawDestinations(): Boolean
    fun canClaimRoutes(): Boolean

    fun onDrawFaceUp()
    fun onDrawWild()
    fun onDrawDeck()
    fun onDrawDestinations()
    fun onClaimRoute()
}

class NotMyTurn(private val turnService: TurnService): ITurnState {
    override fun isMyTurn(): Boolean {
        return false
    }

    override fun canDrawShards(): Boolean {
        return false
    }

    override fun canDrawWild(): Boolean {
        return false
    }

    override fun canDrawDestinations(): Boolean {
        return false
    }

    override fun canClaimRoutes(): Boolean {
        return false
    }

    override fun onDrawFaceUp()   {}
    override fun onDrawWild()      {}
    override fun onDrawDeck()       {}
    override fun onDrawDestinations() {}
    override fun onClaimRoute()        {}
}

class MyTurn(private val turnService: TurnService): ITurnState {
    override fun isMyTurn(): Boolean {
        return true
    }

    override fun canDrawShards(): Boolean {
        return true
    }

    override fun canDrawWild(): Boolean {
        return true
    }

    override fun canDrawDestinations(): Boolean {
        return true
    }

    override fun canClaimRoutes(): Boolean {
        return true
    }

    override fun onDrawFaceUp()   { turnService.setNextState( DrawnFirstCard(turnService) )}
    override fun onDrawWild()      { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onDrawDeck()       { turnService.setNextState( DrawnFirstCard(turnService) )}
    override fun onDrawDestinations() { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onClaimRoute()        { turnService.setNextState( NotMyTurn(turnService) )}
}

class DrawnFirstCard(private val turnService: TurnService): ITurnState {
    override fun isMyTurn(): Boolean {
        return true
    }

    override fun canDrawShards(): Boolean {
        return true
    }

    override fun canDrawWild(): Boolean {
        return false
    }

    override fun canDrawDestinations(): Boolean {
        return false
    }

    override fun canClaimRoutes(): Boolean {
        return false
    }

    override fun onDrawFaceUp()   { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onDrawWild()      { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onDrawDeck()       { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onDrawDestinations() { turnService.setNextState( NotMyTurn(turnService) )}
    override fun onClaimRoute()        { turnService.setNextState( NotMyTurn(turnService) )}
}
