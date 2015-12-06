package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;

public class WallGameObject extends AbstractGameObject implements Obstackle {

	public WallGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action processObject() {
		//TODO
		if (isObjectBeforePosition()) getContext().changeDirection();
		return null;
	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
	}

	
}
