package prince;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameSituation;
import prince.domain.AbstractGameObject;
import prince.domain.AbstractGameObject.ActionRet;

/**
 * 
 * @author neugeeug
 *
 */
public class GameSituationManager {


	
	public Action manage(GameSituation gs)
	{
		//create game context
		GameContext.createContext(gs);
		
		//set up data structure
		GameContext.getContext().analyzeEnvFillObjectSet(gs);
		
		//select algorithm
		AlgorithmStrategy algorithm = selectAlgorithm();
		
		//apply algorithm
		GameContext.getContext().applyAlgorithm(algorithm);
		
		//default action
		ActionRet retAction = new ActionRet(new Move(GameContext.getContext().getCurrentDirection()));
		
		int step =0;
		
		for ( AbstractGameObject go:  GameContext.getContext().getGameObjectSet())
		{
			ActionRet objAction = go.processObject();
			retAction = objAction != null ? objAction : retAction;
			if (retAction.isNow()) break;
		}
		
		step++;
		return retAction.getAction();
	}

	private AlgorithmStrategy selectAlgorithm() {
		// now only one algoritm exists
		return new AlgorithmStrategyDefault();
	}
	
	
	
}
