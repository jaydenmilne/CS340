import { Component, OnInit } from '@angular/core';
import { GamePreview } from 'src/app/Model/GamePreview';
import { Player } from 'src/app/Model/Player';
import { Color } from 'src/app/Model/color.enum';

@Component({
  selector: 'app-game-info',
  templateUrl: './game-info.component.html',
  styleUrls: ['./game-info.component.scss']
})
export class GameInfoComponent implements OnInit {

  
  constructor() { 
    let players: Player []= [
      new Player(Color.YELLOW, 'user1', false, 'riffraff78'),
      new Player(Color.BLUE, 'user2', true, 'toughstuff56'),
      new Player(Color.GREEN, 'user3', true, 'hotshot33'),
      new Player(Color.PURPLE, 'user4', false, 'tooslow64'),
    ];
    this.gamePreview = new GamePreview('Game1', '1564313', false, players);

  }

  gamePreview : GamePreview;

  ngOnInit() {
  }

  public getPlayerColorStyle(player:Player){
    let style = {
      'background-color': "#" + player.getColor()
    }
    return style;
  }

  public onJoinGame(){
    // call join game on lobby service
  }
}
