package prince;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.action.PickUp;
import cz.yellen.xpg.common.action.Use;
import cz.yellen.xpg.common.stuff.GameObject;
import cz.yellen.xpg.common.stuff.GameSituation;

public class GameSituationWrapper {

	private static Direction currentDirection = Direction.FORWARD;
	// private static List<String> pastMoves = new ArrayList<>();

	GameSituation gsm;

	public GameSituationWrapper(GameSituation gs) {
		gsm = gs;
	}

	public Action analyze() {

		Map<String, GameObject> map = new HashMap<String, GameObject>();

		for (GameObject go : gsm.getGameObjects()) {
			// String type = go.getType();
			/*
			 * switch (type) { case "prince": PrinceAction(); case "wall":
			 * WallAction(); case "gate": GateAction(); }
			 */
			if ("prince".equals(go.getType())) {
				map.put("prince", go);
			} else if ("gate".equals(go.getType())) {
				map.put("gate", go);

			} else if ("wall".equals(go.getType())) {
				map.put("wall", go);
			} else if ("pit".equals(go.getType())) {
				map.put("pit", go);
			}

		}

		return getAction(map);

	}

	private Action getAction(Map<String, GameObject> map) {

		GameObject prince = map.get("prince");
		GameObject wall = map.get("wall");
		GameObject gate = map.get("gate");
		GameObject sword = map.get("sword");

		boolean safePassage = true;

		Action action = new Move(getCurrentDirection());

		if (wall != null && wall.getPosition() == 1 && getCurrentDirection() == Direction.FORWARD) {
			setCurrentDirection(Direction.BACKWARD);
			// pastMoves.removeAll();
		} else if (wall != null && wall.getPosition() == -1 && getCurrentDirection() == Direction.BACKWARD) {
			setCurrentDirection(Direction.FORWARD);
		}

		for (String key : map.keySet()) {
			if (key.equals("pit")) {
				GameObject pit = map.get("pit");

				if (pit.getPosition() == 1 && getCurrentDirection() == Direction.FORWARD) {
					action = new Jump(Direction.FORWARD);
					safePassage = false;
				} else if (pit.getPosition() == -1 && getCurrentDirection() == Direction.BACKWARD) {
					action = new Jump(Direction.BACKWARD);
					safePassage = false;
				}
			}

			GameObject guard = map.get("guard");
			if (key.equals("guard")) {
				if (prince.getStuff().contains("sword")) {
					if (guard.getPosition() == 1 && getCurrentDirection() == Direction.FORWARD
							&& "false".equals(guard.getProperty("dead"))) {
						action = new Use(sword, guard);
					} else if (guard.getPosition() == -1 && getCurrentDirection() == Direction.BACKWARD
							&& "false".equals(guard.getProperty("dead"))) {
						action = new Use(sword, guard);
					}
				}
				else //go away
				{
					if  (guard.getPosition() == 1 && getCurrentDirection() == Direction.FORWARD) {
						setCurrentDirection(Direction.BACKWARD);
						// pastMoves.removeAll();
					} else if (guard.getPosition() == -1 && getCurrentDirection() == Direction.BACKWARD) {
						setCurrentDirection(Direction.FORWARD);
					}
				}
			}
		}

		if (sword != null) {
			if (sword.getPosition() == 0)
				action = new PickUp(sword);
			else if (sword.getPosition() == 1)
				action = new Move(Direction.FORWARD);
			else if (sword.getPosition() == -1)
				action = new Move(Direction.BACKWARD);
		}

		if (gate != null) {
			if (gate.getPosition() == 0)
				action = new Enter(gate);
			else if (gate.getPosition() == 1)
				action = new Move(Direction.FORWARD);
			else if (gate.getPosition() == -1)
				action = new Move(Direction.BACKWARD);
		}

		/*
		 * if (safePassage == true) pastMoves.add("safe"); else
		 * pastMoves.add("notsafe");
		 */

		System.out.println(action);
		return action;
	}

	// private useSword()
	// {
	//
	// }

	public static Direction getCurrentDirection() {
		return currentDirection;
	}

	public static void setCurrentDirection(Direction current) {
		currentDirection = current;
	}

}
