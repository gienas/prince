package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;

public class GateGameObject extends AbstractGameObject implements Obstackle {

	public GateGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action processObject() {
		// TODO
		Action retAction = null;
		if (isObjectOnMyPosition()) {
			retAction = new Enter(getOrgObject());
		} else if (isObjectAnywhereBefore()) {
			retAction = new Move(getContext().getCurrentDirection());
		} else if (isObjectAnywhereBehind()) {
			getContext().changeDirection();
			retAction = new Move(getContext().getCurrentDirection());
		}
		return retAction;

	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
	}

}
