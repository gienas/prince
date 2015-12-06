package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.action.Wait;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;

public class ChopperGameObject extends AbstractGameObject implements Obstackle {

	public static String PROPERTY_opening = "opening";
	public static String PROPERTY_closing = "closing";

	private boolean opening;
	private boolean closing;

	public ChopperGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action processObject() {
		Action retAction = null;
		if (isObjectBeforePosition() && isOpening()) {
			retAction = new Jump(getContext().getCurrentDirection());
		} else if (isObjectBeforePosition())
		{
			retAction = new Wait();
		}	
		return retAction;
	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
		// convert health
		String op = getProperty(PROPERTY_opening);
		if (op != null && !op.trim().isEmpty()) {
			opening = Boolean.parseBoolean(op);
		}

		// convert dead
		String cl = getProperty(PROPERTY_closing);
		if (cl != null && !cl.trim().isEmpty()) {
			closing = Boolean.parseBoolean(cl);
		}
	}

	public boolean isClosing() {
		return closing;
	}

	public boolean isOpening() {
		return opening;
	}
}
