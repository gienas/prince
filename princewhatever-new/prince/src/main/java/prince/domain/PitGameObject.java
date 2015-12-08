package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;
import prince.domain.AbstractGameObject.ActionRet;

public class PitGameObject extends AbstractGameObject implements Obstackle {

	public PitGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionRet processObject() {
		Action retAction = null;
		if ( isObjectBeforePosition())
		{
			retAction = new Jump(getContext().getCurrentDirection());
		}	
		return (retAction == null) ? null: new ActionRet(retAction, true);
	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
	}

	
}
