package test;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cz.yellen.xpg.common.GameStrategy;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.action.PickUp;
import cz.yellen.xpg.common.stuff.GameSituation;
import prince.GameContext;
import prince.GameStrategyImpl;

public class GameSituationWrapperTest {

	@Test
	public void princeBeforeWallForwardTest() {
		
		GameSituation situation =  new GameSituationPrinceBeforeWallForward();	
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		GameContext.getContext().setCurrentDirection(Direction.FORWARD);
		Direction before = Direction.FORWARD;	
		//check if direction was changed		
		assertTrue( gs.step(situation) instanceof Move && before != GameContext.getContext().getCurrentDirection() );
	}
	
	@Test
	public void princeBeforeWallBackwardTest() {
		
		GameSituation situation =  new GameSituationPrinceBeforeWallBackward();	
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		GameContext.getContext().setCurrentDirection(Direction.BACKWARD);
		Direction before = GameContext.getContext().getCurrentDirection();	
		//check if direction was changed		
		assertTrue( gs.step(situation) instanceof Move && before != GameContext.getContext().getCurrentDirection() );
	}
	
	@Test
	public void princeBeforeSecondPitTest() {
		
		GameSituation situation =  new GameSituationPrinceBeforeSecondPit();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		assertTrue( gs.step(situation) instanceof Jump);
	}
	
	@Test
	public void princeOnSword() {
		
		GameSituation situation =  new GameSituationPrinceOnSword();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		assertTrue( gs.step(situation) instanceof PickUp);
	}
	
	@Test
	public void princeBeforeSwordForward() {
		
		GameSituation situation =  new GameSituationPrinceBeforeSwordForward();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		GameContext.getContext().setCurrentDirection(Direction.FORWARD);
		Direction before = GameContext.getContext().getCurrentDirection();	
		assertTrue( gs.step(situation) instanceof Move && before == GameContext.getContext().getCurrentDirection());
		
		
	}
	
	@Test
	public void princeBeforeSwordBackward() {
		
		GameSituation situation =  new GameSituationPrinceBeforeSwordBackward();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		GameContext.getContext().setCurrentDirection(Direction.BACKWARD);
		Direction before = GameContext.getContext().getCurrentDirection();	
		assertTrue( gs.step(situation) instanceof Move && before == GameContext.getContext().getCurrentDirection());
	}
	
	
	@Test
	public void princeBehindSwordTurnAround() {
		
		GameSituation situation =  new GameSituationPrinceBeforeSwordForward();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		GameContext.getContext().setCurrentDirection(Direction.BACKWARD);
		Direction before = GameContext.getContext().getCurrentDirection();	
		assertTrue( gs.step(situation) instanceof Move && before != GameContext.getContext().getCurrentDirection());
	}
	
	@Test
	public void princeInGateTest() {
		GameSituation situation =  new GameSituationPrinceInGate();
		GameContext.createContext(situation);
		GameStrategy gs = new GameStrategyImpl();
		assertTrue( gs.step(situation) instanceof Enter);
	}



}

