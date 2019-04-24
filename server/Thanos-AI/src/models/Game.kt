package models

import GamePlayerDTO
import LobbyGameDTO
/**
 * Created by Jordan Gassaway on 4/23/2019.
 * models.Game: Client AI models.Game Model
 */
class Game() {
    var players: MutableSet<GamePlayerDTO> = mutableSetOf()
    var bank: Bank = Bank()
    var map: RouteList = RouteList()

    constructor(lobbyGameDTO: LobbyGameDTO) : this() {
        players = lobbyGameDTO.players
    }

}