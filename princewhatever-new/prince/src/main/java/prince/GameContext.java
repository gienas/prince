package prince;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.stuff.GameObject;
import cz.yellen.xpg.common.stuff.GameSituation;
import prince.domain.AbstractGameObject;
import prince.domain.PrinceGameObject;

public class GameContext {

	private static GameContext gc;

	public static GameContext createContext(GameSituation gs) {
		if (gc == null) {
			gc = new GameContext();
			gc.setGameSituation(gs);
			gc.analyzeEnvFillObjectSet(gs);
			gc.setInitialDirection();
		}
		return gc;
	}

	public static GameContext getContext() {
		if (gc != null) {
			return gc;
		}
		throw new IllegalStateException("Context not initialized yet");
	}

	/*---------------------------------------------------------------*/

	private Set<AbstractGameObject> gameObjectSet = new LinkedHashSet<>();

	private Direction currentDirection = Direction.BACKWARD;

	private PrinceGameObject prince;

	private AlgorithmStrategy currentAlgorithm;

	private GameSituation gameSituation;

	public Direction getCurrentDirection() {
		// TODO Auto-generated method stub
		return currentDirection;
	}

	public void setCurrentDirection(Direction cd) {
		// TODO Auto-generated method stub
		currentDirection = cd;
	}

	public void changeDirection() {
		if (getCurrentDirection() == Direction.FORWARD) {
			setCurrentDirection(Direction.BACKWARD);
		} else {
			setCurrentDirection(Direction.FORWARD);
		}
	}

	public boolean isCurrentDirectionForward() {
		return getCurrentDirection() == Direction.FORWARD;
	}

	public boolean isCurrentDirectionBackward() {
		return getCurrentDirection() == Direction.BACKWARD;
	}

	public void analyzeEnvFillObjectSet(GameSituation gs) {
		gameObjectSet = new LinkedHashSet<>();
		for (GameObject go : gs.getGameObjects()) {
			AbstractGameObject ago = GameObjectEnum.createObject(go);
			gameObjectSet.add(ago);
			if (ago instanceof PrinceGameObject)
				setPrince((PrinceGameObject) ago);
		}
	}

	private void setInitialDirection() {
		for (AbstractGameObject aos : getGameObjectSet()) {
			if (aos.getEnumType().equals(GameObjectEnum.GUARD) && aos.isObjectAnywhereBefore()) {
				changeDirection();
				System.out.println("direction changed to " + getCurrentDirection().name());
			}
		}

	}

	public void applyAlgorithm(AlgorithmStrategy st) {
		// sort game objects according strategy
		Map<GameObjectEnum, Integer> orderMap = st.getOrderProcessing();
		currentAlgorithm = st;
		List<AbstractGameObject> list = new ArrayList<>(gameObjectSet);
		Collections.sort(list, new Comparator<AbstractGameObject>() {
			@Override
			public int compare(AbstractGameObject o1, AbstractGameObject o2) {
				// TODO Auto-generated method stub
				Integer o1Order = orderMap.get(o1.getEnumType());
				Integer o2Order = orderMap.get(o2.getEnumType());
				return o1Order.compareTo(o2Order);
			}
		});
		gameObjectSet = new LinkedHashSet<>(list);
	}

	public PrinceGameObject getPrince() {
		return prince;
	}

	public void setPrince(PrinceGameObject prince) {
		this.prince = prince;
	}

	public Set<AbstractGameObject> getGameObjectSet() {
		return gameObjectSet;
	}

	public void setGameObjectSet(Set<AbstractGameObject> gameObjectSet) {
		this.gameObjectSet = gameObjectSet;
	}

	public GameSituation getGameSituation() {
		return gameSituation;
	}

	public void setGameSituation(GameSituation gameSituation) {
		this.gameSituation = gameSituation;
	}
}
