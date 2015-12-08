package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;
import prince.domain.AbstractGameObject.ActionRet;

public class GateGameObject extends AbstractGameObject implements Obstackle {

	public static String PROPERTY_OPENED = "opened";
	public static String PROPERTY_CLOSED = "closed";

	public GateGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	boolean opened = false;
	boolean closed = true;

	@Override
	public ActionRet processObject() {
		// TODO
		System.out.println("Gate position" + getPosition());
		System.out.println("Prince position" + getContext().getPrince().getPosition());
		System.out.println("Curent direction" + getContext().getCurrentDirection());
		Action retAction = null;
		if ( isObjectOnMyPosition())
		{
			retAction = new Enter(getOrgObject());
		}	//&& isOpened() getProperty("opened").equals("true")
		else if (isObjectBeforePosition() && getProperty("opened").equals("true")) {
			retAction = new Move(getContext().getCurrentDirection());

		} else if (isObjectBeforePosition() && isClosed()) {
			getContext().changeDirection();
			retAction = new Move(getContext().getCurrentDirection());
		} 
		
//		else if (isObjectAnywhereBefore()) {
//			retAction = new Move(getContext().getCurrentDirection());
//		} else if (isObjectAnywhereBehind()) {
//			getContext().changeDirection();
//			retAction = new Move(getContext().getCurrentDirection());
//		}
		return (retAction == null) ? null : new ActionRet(retAction, true);

	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
		String op = getProperty(PROPERTY_OPENED);
		if (op != null && !op.trim().isEmpty()) {
			opened = Boolean.parseBoolean(op);
		}

		// convert dead
		String clo = getProperty(PROPERTY_CLOSED);
		if (clo != null && !clo.trim().isEmpty()) {
			closed = Boolean.parseBoolean(clo);
		}
	}

	public boolean isOpened() {
		return opened;
	}

	public Boolean isClosed() {
		return closed;
	}

}
