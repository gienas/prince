package throwable.prince.strategies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ctc.wstx.ent.ParsedExtEntity;

import cz.yellen.xpg.common.GameStrategy;
import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.action.PickUp;
import cz.yellen.xpg.common.action.Use;
import cz.yellen.xpg.common.action.Wait;
import cz.yellen.xpg.common.stuff.GameObject;
import cz.yellen.xpg.common.stuff.GameSituation;
import throwable.prince.util.GameSituationPredicates;

public class UnbreakableStratyegy implements GameStrategy {

	private static final String GUARD = "guard";
	private static final int JUMP_LENGTH = 2;
	private static final String PIT = "pit";
	private static final String GATE = "gate";
	private static final String WALL = "wall";
	private static final String PRINCE = "prince";
	private static final String BOTTLE = "bottle";
	private static final String CHOPPER = "chopper";

	Direction actualDirection = Direction.FORWARD;

	int startPosition = 0;
	int actualPosition = 0;
	Action actualAction = move();

	GameObject prince;
	GameObject wall;
	GameObject gate;
	List<GameObject> pits;
	List<GameObject> bottles;
	List<GameObject> guards;
	List<GameObject> choppers;
	private GameObject sword;

	Set<GameObject> gameObjects = new HashSet<GameObject>();

	Map<Integer, GameObject> gameMap = new HashMap<Integer, GameObject>();

	public UnbreakableStratyegy() {
	}

	public Action step(GameSituation situation) {

		actualAction = move();
		lookAround(situation);

		// If the gate is visible
		if (gate != null) { // We can see the gate so we move towards the gate
			if (gate.getPosition() == prince.getPosition()) {
				return new Enter(gate);
			} else if (gate.getPosition() < prince.getPosition()) {
				return new Move(Direction.BACKWARD);
			} else if (gate.getPosition() > prince.getPosition()) {
				return new Move(Direction.FORWARD);
			}
		}

		wallHandler();

		pitHandler();

		guardHandler();

		swordHandler();

		chopperHandler();
		
		bottleHandler();

		return actualAction;
	}

	private void bottleHandler() {
		Integer health = Integer.valueOf(prince.getProperty("health"));
		if (bottles != null) {
			for (GameObject bottle : bottles) {
				if (bottle.getPosition() == prince.getPosition()) {
					actualAction = new PickUp(bottle);
				}
			}
		}

		/**
		 * if (sword != null) { // We can see the gate so we move towards the
		 * gate if (sword.getPosition() == prince.getPosition()) { actualAction
		 * = new PickUp(sword); } else if (sword.getPosition() <
		 * prince.getPosition()) { actualAction = new Move(Direction.BACKWARD);
		 * } else if (sword.getPosition() > prince.getPosition()) { actualAction
		 * = new Move(Direction.FORWARD); } }
		 */

		if (health < 5) {
			// FIXME bootels from prince
			for (GameObject obj : prince.getStuff()) {
				if (obj.getProperty("odour") != null && obj.getProperty("odour").equals("mint")
						&& !obj.getProperty("volume").equals("0")) {
					actualAction = new Use(obj, prince);
				}

			}
		}
	}

	/**
	 * If we see wall and we headed towards it, prince changes direction.
	 * <p>
	 * After direction change prince is obstacle aware.
	 */
	private void wallHandler() {
		if (wall != null) {
			if (prince.getPosition() < wall.getPosition()) {
				actualDirection = Direction.BACKWARD;
			} else {
				actualDirection = Direction.FORWARD;
			}
		}
	}

	/**
	 * If prince sees a pit TODO
	 */
	private void pitHandler() {
		// warning! Pit spotted // the pit handler :D
		if (pits != null) {
			// we are interested in the actual direction located obstacle

			GameObject pit = getPitInDirectionOfMove(pits);

			// jump over it
			if (pit != null && prince.getPosition() < pit.getPosition()) {
				if (actualDirection == Direction.FORWARD) {
					actualAction = jump();
				} else {
					actualAction = move();
				}
			} else { // pit on the left
				if (actualDirection == Direction.BACKWARD) {
					actualAction = jump();
				} else {
					actualAction = move();
				}
			}
		}
	}

	private GameObject getPitInDirectionOfMove(List<GameObject> pits2) {
		int position = prince.getPosition();
		if (actualDirection == Direction.FORWARD) {
			for (GameObject obstacle : pits2) {
				if (obstacle.getPosition() == position + 1) {
					return obstacle;
				}
			}
		} else { // BACK
			for (GameObject obstacle : pits2) {
				if (obstacle.getPosition() == position - 1) {
					return obstacle;
				}
			}
		}
		return null;
	}

	//FIXME switch on getObstacleInDirectionOfMove
	private GameObject getGuardInDirectionOfMove(List<GameObject> guards) {
		int position = prince.getPosition();
		if (actualDirection == Direction.FORWARD) {
			for (GameObject obstacle : guards) {
				if (obstacle.getPosition() == position + 1) {
					return obstacle;
				}
			}
		} else { // BACK
			for (GameObject obstacle : guards) {
				if (obstacle.getPosition() == position - 1) {
					return obstacle;
				}
			}
		}
		return null;
	}

	//FIXME switch on getObstacleInDirectionOfMove
	private GameObject getChopperInDirectionOfMove(List<GameObject> choppers) {
		int position = prince.getPosition();
		if (actualDirection == Direction.FORWARD) {
			for (GameObject obstacle : choppers) {
				if (obstacle.getPosition() == position + 1) {
					return obstacle;
				}
			}
		} else { // BACK
			for (GameObject obstacle : choppers) {
				if (obstacle.getPosition() == position - 1) {
					return obstacle;
				}
			}
		}
		return null;
	}

	//FIXME 
	private GameObject getObstacleInDirectionOfMove( List<GameObject> choppers )
	{
		int position = prince.getPosition();
		if (actualDirection == Direction.FORWARD) {
			for (GameObject obstacle : choppers) {
				if (obstacle.getPosition() == position + 1) {
					return obstacle;
				}
			}
		} else { // BACK
			for (GameObject obstacle : choppers) {
				if (obstacle.getPosition() == position - 1) {
					return obstacle;
				}
			}
		}
		return null;
	}
	
	private Move move() {
		if (actualDirection == Direction.BACKWARD) {
			actualPosition--;

		} else {
			actualPosition++;
		}
		return new Move(actualDirection);
	}

	private Jump jump() {
		// adjust actual position
		if (actualDirection == Direction.BACKWARD) {
			actualPosition = actualPosition - JUMP_LENGTH;
		} else {
			actualPosition = actualPosition + JUMP_LENGTH;
		}
		return new Jump(actualDirection);
	}

	private void switchActualDirection() {
		if (actualDirection == Direction.FORWARD) {
			actualDirection = Direction.BACKWARD;
		} else {
			actualDirection = Direction.FORWARD;
		}
	}

	private void lookAround(GameSituation situation) {
		gameObjects = situation.getGameObjects();
		// get prince
		prince = GameSituationPredicates.getObject(PRINCE, gameObjects);
		// get wall
		wall = GameSituationPredicates.getObject(WALL, gameObjects);
		// find the gate
		gate = GameSituationPredicates.getObject(GATE, gameObjects);
		// find pit
		pits = GameSituationPredicates.getObjects(PIT, gameObjects);

		guards = GameSituationPredicates.getObjects(GUARD, gameObjects);

		sword = GameSituationPredicates.getObject("sword", gameObjects);

		bottles = GameSituationPredicates.getObjects(BOTTLE, gameObjects);

		choppers = GameSituationPredicates.getObjects(CHOPPER, gameObjects);
	}

	private void guardHandler() {

		if (guards == null)
			return;

		GameObject guard = getGuardInDirectionOfMove(guards);
		GameObject sword = GameSituationPredicates.getObject("sword", prince.getStuff());
		if (guard != null && !isGuardDead(guard)) {
			if (sword != null) {
				actualAction = new Use(sword, guard);
			} else {
				switchActualDirection();
				actualAction = new Move(actualDirection);
			}
		}
	}

	private void chopperHandler() {
		// TODO Auto-generated method stub
		if (choppers == null)
			return;
		
		GameObject chopper = getChopperInDirectionOfMove(choppers);
		if (chopper != null && chopper.getProperty("opening").equals("true")) {
			actualAction = new Jump(actualDirection);
		} else if (chopper != null) {
			actualAction = new Wait();
		}
	}

	private boolean isGuardDead(GameObject guard) {
		return guard.getProperties().get("dead").equals("true");
	}

	private void swordHandler() {
		if (sword != null) { // We can see the gate so we move towards the gate
			if (sword.getPosition() == prince.getPosition()) {
				actualAction = new PickUp(sword);
			} else if (sword.getPosition() < prince.getPosition()) {
				actualAction = new Move(Direction.BACKWARD);
			} else if (sword.getPosition() > prince.getPosition()) {
				actualAction = new Move(Direction.FORWARD);
			}
		}
	}

}
