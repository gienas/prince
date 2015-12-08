package throwable.prince.strategies;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private static final String TILE = "tile";
	private static final String PORTCULLIS = "portcullis";

	private static Map<String, Integer> positions = new HashMap<>();

	Direction actualDirection = Direction.FORWARD;

	int startPosition = 0;
	int actualPosition = 0;
	Action actualAction = move();
	boolean stopProcessing = false;

	GameObject prince;
	GameObject wall;
	GameObject gate;
	List<GameObject> pits;
	List<GameObject> bottles;
	List<GameObject> guards;
	List<GameObject> choppers;
	List<GameObject> tiles;
	List<GameObject> portcullises;
	private GameObject sword;
	private static Set<Integer> porticullisSet = new HashSet<>();
	private static Integer lastporticullisId = null;
	
	Set<GameObject> gameObjects = new HashSet<GameObject>();

	Map<Integer, GameObject> gameMap = new HashMap<Integer, GameObject>();

	public UnbreakableStratyegy() {
	}

	public Action step(GameSituation situation) {

		actualAction = move();

		lookAround(situation);

		// If the gate is visible

		if (gateHandler())
			return actualAction;

		if (tileHandler())
			return actualAction;

		if (wallHandler())
			return actualAction;

		if (pitHandler())
			return actualAction;

		if (guardHandler())
			return actualAction;

		if (swordHandler())
			return actualAction;

		if (chopperHandler())
			return actualAction;

		if (portcullisHandler())
			return actualAction;

		if (bottleHandler())
			return actualAction;

		return actualAction;
	}

	
	private boolean gateHandler() {
		if (gate != null) { // We can see the gate so we move towards the gate
			String opened = gate.getProperty("opened");
			if (gate.getPosition() == prince.getPosition() && opened != null && "true".equals(opened)) {
				actualAction = new Enter(gate);
				return true;
			} else if (gate.getPosition() < prince.getPosition()) {
				actualAction = new Move(Direction.BACKWARD);
			} else if (gate.getPosition() > prince.getPosition()) {
				actualAction = new Move(Direction.FORWARD);
			}
		}
		return false;
	}

	private boolean bottleHandler() {
		Integer health = Integer.valueOf(prince.getProperty("health"));
		if (bottles != null) {
			for (GameObject bottle : bottles) {
				if (bottle.getPosition() == prince.getPosition()) {
					actualAction = new PickUp(bottle);
				}
			}
		}

		if (health < 5) {
			// FIXME bootels from prince
			for (GameObject obj : prince.getStuff()) {
				if (obj.getProperty("odour") != null && obj.getProperty("odour").equals("mint")
						&& !obj.getProperty("volume").equals("0")) {
					actualAction = new Use(obj, prince);
				}

			}
		}
		return false;
	}

	/**
	 * If we see wall and we headed towards it, prince changes direction.
	 * <p>
	 * After direction change prince is obstacle aware.
	 */
	private boolean wallHandler() {
		if (wall != null) {
			if (prince.getPosition() < wall.getPosition()) {
				actualDirection = Direction.BACKWARD;
			} else {
				actualDirection = Direction.FORWARD;
			}
		}
		return false;
	}

	
	
	private boolean portcullisHandler() {
		if (portcullises != null) {
			for (GameObject portcullis : portcullises) {
				String opened = portcullis.getProperty("opened");
				if (prince.getPosition() < portcullis.getPosition() && !"true".equals(opened)) {
					//porticullisSet.put(new Integer(portcullis.getId()), new HashSet<>() );
					actualDirection = Direction.BACKWARD;
					lastporticullisId = portcullis.getId();
				} else if (prince.getPosition() > portcullis.getPosition() && !"true".equals(opened)) {
					//porticullisMap.put(new Integer(portcullis.getId()), new HashSet<>() );
					actualDirection = Direction.FORWARD;
					lastporticullisId = portcullis.getId();
				}
			}
		}
		return false;
	}

	
	
	/**
	 * If prince sees a pit TODO
	 */
	private boolean pitHandler() {
		// warning! Pit spotted // the pit handler :D
		if (pits != null) {
			// we are interested in the actual direction located obstacle

			GameObject pit = getObstackleInDirectionOfMove(pits);
			if (pit != null) {
				actualAction = jump();
			}

			// // jump over it
			// if (pit != null && prince.getPosition() < pit.getPosition()) {
			// if (actualDirection == Direction.FORWARD) {
			// actualAction = jump();
			// } else {
			// actualAction = move();
			// System.out.println("mmmmmmmmmmmmmmmmmmmmmove");
			// }
			// } else { // pit on the left
			// if (actualDirection == Direction.BACKWARD) {
			// actualAction = jump();
			// } else {
			// actualAction = move();
			// System.out.println("mmmmmmmmmmmmmmmmmmmmmove");
			// }
			// }
		}
		return false;
	}

	private boolean guardHandler() {

		if (guards == null)
			return false;

		GameObject guard = getObstackleInDirectionOfMove(guards);
		GameObject sword = GameSituationPredicates.getObject("sword", prince.getStuff());
		if (guard != null && !isGuardDead(guard)) {
			if (sword != null) {
				actualAction = new Use(sword, guard);
			} else {
				switchActualDirection();
				actualAction = new Move(actualDirection);
			}
		}
		return false;
	}

	private boolean chopperHandler() {
		// TODO Auto-generated method stub
		if (choppers == null)
			return false;

		// GameObject chopper = getChopperInDirectionOfMove(choppers);
		GameObject chopper = getObstackleInDirectionOfMove(choppers);
		if (chopper != null && chopper.getProperty("opening").equals("true")) {
			actualAction = new Jump(actualDirection);
		} else if (chopper != null) {
			actualAction = new Wait();
		}
		return false;
	}

	private boolean swordHandler() {
		if (sword != null) { // We can see the gate so we move towards the gate
			if (sword.getPosition() == prince.getPosition()) {
				actualAction = new PickUp(sword);
			} else if (sword.getPosition() < prince.getPosition()) {
				actualAction = new Move(Direction.BACKWARD);
			} else if (sword.getPosition() > prince.getPosition()) {
				actualAction = new Move(Direction.FORWARD);
			}
		}
		return false;
	}

	private boolean tileHandler() {
		// TODO Auto-generated method stub
		if (tiles == null)
			return false;
		int position = prince.getPosition();

		for (GameObject tile : tiles) {
			if (tile.getPosition() == position) {
				if (lastporticullisId != null) {
				if (porticullisSet.contains(tile.getId()))
				{	
					porticullisSet.add(tile.getId());
					switchActualDirection();
					actualAction = new Move(actualDirection);
				}	
				else
				{
					actualAction = new Move(actualDirection);
				}
				}
					
				
//				Integer count = visitedTile.get(new Integer(tile.getId()));
//				if (count == null) {
//					count = new Integer(1);
//				} else {
//					count++;
//				}
//				visitedTile.put(new Integer(tile.getId()), count);
			}
		}

		return false;
	}

	// FIXME
	private GameObject getObstackleInDirectionOfMove(List<GameObject> obstackles) {
		int position = prince.getPosition();
		if (actualDirection == Direction.FORWARD) {
			for (GameObject obstacle : obstackles) {
				if (obstacle.getPosition() == position + 1) {
					return obstacle;
				}
			}
		} else { // BACK
			for (GameObject obstacle : obstackles) {
				if (obstacle.getPosition() == position - 1) {
					return obstacle;
				}
			}
		}
		return null;
	}

	private Action move() {
		if (actualDirection == Direction.BACKWARD) {
			actualPosition--;

		} else {
			actualPosition++;
		}

//		if (tiles != null) {
//			for (GameObject tile : tiles) {
//				int position = prince.getPosition();
//				if (visitedTile == null || visitedTile.isEmpty())
//					return new Move(actualDirection);
//				Integer currid = tile.getId();
//				Integer visited = visitedTile.get(currid);
//				if (visited == null)
//					return new Move(actualDirection);
//
//				if (tile.getPosition() > position && visited.intValue() > 2)
//					return new Jump(Direction.BACKWARD);
//				else if (tile.getPosition() < position && visited.intValue() > 2) {
//					return new Jump(Direction.FORWARD);
//				}
//			}
//		}
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

		tiles = GameSituationPredicates.getObjects(TILE, gameObjects);

		portcullises = GameSituationPredicates.getObjects(PORTCULLIS, gameObjects);
	}

	private boolean isGuardDead(GameObject guard) {
		return guard.getProperties().get("dead").equals("true");
	}

//	class PortcullisHelper
//	{
//		Integer id;
//		//<id tile, >
//		Map<Integer, Integer> tiles = new HashMap<>();
//	
//		public PortcullisHelper(Integer idPort, Integer idTile) {
//		// TODO Auto-generated constructor stub
//		}
//		
//		public Integer getId() {
//			return id;
//		}
//		
//		private putTail()
//		{
//			
//		}
//		
//	}
	
}
