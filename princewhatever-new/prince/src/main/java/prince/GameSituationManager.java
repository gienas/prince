package prince;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameSituation;
import prince.domain.AbstractGameObject;

public class GameSituationManager {


	
	public Action manage(GameSituation gs)
	{
		//create game context
		GameContext.createContext(gs);
		
		//set data structure
		GameContext.getContext().analyzeEnvFillObjectSet(gs);
		
		//select algorithm
		AlgorithmStrategy algorithm = selectAlgorithm();
		
		//apply algorithm
		GameContext.getContext().applyAlgorithm(algorithm);
		
		//default action
		Action retAction = new Move(GameContext.getContext().getCurrentDirection());
		
		for ( AbstractGameObject go:  GameContext.getContext().getGameObjectSet())
		{
			Action objAction = go.processObject();
			retAction = objAction != null ? objAction : retAction;
		}
		
		//System.out.println("Call " + retAction);
		return retAction;
	}

	private AlgorithmStrategy selectAlgorithm() {
		// now only one algoritm exists
		return new AlgorithmStrategyDefault();
	}
	
	
	
}
