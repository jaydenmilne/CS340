import { ChangeTurnCommand } from '@core/game-commands';
import { ShardCard } from '@core/model/cards';

export abstract class ITurnState {
	abstract isMyTurn(): boolean;
	abstract canDrawShards(): boolean;
	abstract canDrawWild(): boolean;
	abstract canDrawDestinations(): boolean;
	abstract canClaimRoutes(): boolean;

	abstract onChangeTurn(cmd: ChangeTurnCommand);
	abstract onDrawDestCard();
	abstract onDrawShardCard();
	abstract onSelectShardCard(card: ShardCard);

	enter() { }
	leave() { }
}
