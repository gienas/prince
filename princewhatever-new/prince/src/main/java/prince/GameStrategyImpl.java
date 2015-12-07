package prince;

import cz.yellen.xpg.common.GameStrategy;
import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.stuff.GameSituation;

/**
 * 
 * @author neugeeug
 *
 */
public class GameStrategyImpl implements GameStrategy{
	
	private GameSituationManager manager ;
	
	public GameStrategyImpl() {
		manager = new GameSituationManager();
	}
	
	public Action step(GameSituation situation) {
		
		return getManager().manage(situation);
	}
	
	public GameSituationManager getManager() {
		return manager;
	}
}
