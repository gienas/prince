package prince;

import cz.yellen.xpg.common.GameStrategy;
import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.stuff.GameSituation;

public class GameStrategyImpl implements GameStrategy{
	
	public Action step(GameSituation situation) {
		
		return new GameSituationWrapper(situation).analyze();
	}
	
}
