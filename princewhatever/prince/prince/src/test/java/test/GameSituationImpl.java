package test;

import java.util.Map;
import java.util.Set;

import cz.yellen.xpg.common.stuff.GameObject;
import cz.yellen.xpg.common.stuff.GameSituation;
import cz.yellen.xpg.common.stuff.GameStatus;

/**
 * 
 * @author neugeeug
 *
 */
public abstract class GameSituationImpl implements GameSituation {

	public int getGameId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public abstract Set<GameObject> getGameObjects();

	public GameStatus getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getStepNr() {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean isGameOver() {
		// TODO Auto-generated method stub
		return false;
	}


}
