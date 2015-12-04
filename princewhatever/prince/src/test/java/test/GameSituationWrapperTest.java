package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.stuff.GameSituation;
import prince.GameSituationWrapper;

public class GameSituationWrapperTest {

	@Test
	public void princeInGateTest() {
		
		GameSituation situation =  new GameSituationPrinceInGate();
		GameSituationWrapper wrapper = new GameSituationWrapper(situation);		
		assertTrue( wrapper.analyze() instanceof Enter);
	}

	@Test
	public void princeBeforeSecondPit() {
		
		GameSituation situation =  new GameSituationPrinceBeforeSecondPit();
		GameSituationWrapper wrapper = new GameSituationWrapper(situation);		
		assertTrue( wrapper.analyze() instanceof Jump);
	}

}

