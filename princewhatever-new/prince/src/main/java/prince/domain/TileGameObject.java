package prince.domain;

import java.util.Random;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Jump;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;

public class TileGameObject extends AbstractGameObject implements Obstackle {

	public TileGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionRet processObject() {
		// TODO
		Action retAction = null;
		if (isObjectBeforePosition()) {

			Random random = new Random();
			boolean b = random.nextBoolean();
			if (b)
				retAction = new Jump(getContext().getCurrentDirection());
			else
				retAction = new Move(getContext().getCurrentDirection());

			// if (getContext().getTileHelper().isAnyTileCurrentTested() )
			// {
			// retAction = new Jump(getContext().getCurrentDirection());
			// }
			// else
			// {
			// retAction = new Move(getContext().getCurrentDirection());
			// getContext().getTileHelper().startTestTile(getOrgObject().getId());
			// }
		}
		return (retAction == null) ? null : new ActionRet(retAction, true);
	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
	}

	public int getTileById() {
		return getOrgObject().getId();
	}
}
