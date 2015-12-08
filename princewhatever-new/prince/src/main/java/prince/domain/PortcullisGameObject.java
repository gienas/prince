package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Enter;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;
import prince.domain.AbstractGameObject.ActionRet;

public class PortcullisGameObject extends AbstractGameObject implements Obstackle {

	public static String PROPERTY_OPENED = "opened";
	public static String PROPERTY_CLOSED = "closed";

	public PortcullisGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	Boolean opened = false;
	Boolean closed = true;

	@Override
	public ActionRet processObject() {
		// TODO
		Action retAction = null;
//		if (isObjectOnMyPosition() && getProperty("opened").equals("true")) {
//
//			retAction = new Enter(getOrgObject());
//			// stop testing till
//			setOpenerId(getContext().getTileHelper().getTestedTile());
//			getContext().getTileHelper().stopTestTile();
//
//		} else 
			
//		if (isObjectBeforePosition() && getProperty("opened").equals("true")) {
//			retAction = new Move(getContext().getCurrentDirection());
//			getContext().getTileHelper().stopTestTile();
//		}
		if (isObjectBeforePosition() && getProperty("closed").equals("true")) {
			getContext().changeDirection();
			retAction = new Move(getContext().getCurrentDirection());
		} else if (isObjectAnywhereBefore()) {
			retAction = new Move(getContext().getCurrentDirection());
		} 
		return (retAction == null) ? null: new ActionRet(retAction, true);

	}

	@Override
	public void fillObject() {
		// wall has not aditiona properties
		String op = getProperty(PROPERTY_OPENED);
		if (op != null && !op.trim().isEmpty()) {
			opened = Boolean.valueOf(op);
		}

		// convert dead
		String clo = getProperty(PROPERTY_CLOSED);
		if (clo != null && !clo.trim().isEmpty()) {
			closed = Boolean.valueOf(clo);
		}
	}

	public Boolean isOpened() {
		return opened;
	}

	public Boolean isClosed() {
		return closed;
	}

	public Integer getOpenerId() {
		PortculissState port = getContext().getPortcullisStates().get(getCurrentId());
		if ( port != null)
		{
			return port.getOpenerId();
		}	
		return null;
	}

	public Integer getCloserId() {
		PortculissState port = getContext().getPortcullisStates().get(getCurrentId());
		if ( port != null)
		{
			return port.getCloserId();
		}	
		return null;
	}
	
	public void setOpenerId(Integer openerId) {
		PortculissState port = getContext().getPortcullisStates().get(getCurrentId());
		if (port == null)
		{
			port = new PortculissState();
			port.setGateId(getCurrentId());
			getContext().getPortcullisStates().put(getCurrentId(), port);
		}		
		port.setOpenerId(openerId);
	}

	public void setCloserId(Integer closerId) {
		PortculissState port = getContext().getPortcullisStates().get(getCurrentId());
		if (port == null)
		{
			port = new PortculissState();
			port.setGateId(getCurrentId());
			getContext().getPortcullisStates().put(getCurrentId(), port);
		}		
		port.setOpenerId(closerId);
	}

	public static class PortculissState {
		Integer gateId;
		Integer openerId;
		Integer closerId;

		public Integer getGateId() {
			return gateId;
		}

		public void setGateId(Integer gateId) {
			this.gateId = gateId;
		}

		public Integer getOpenerId() {
			return openerId;
		}

		public void setOpenerId(Integer openerId) {
			this.openerId = openerId;
		}

		public Integer getCloserId() {
			return closerId;
		}

		public void setCloserId(Integer closerId) {
			this.closerId = closerId;
		}

	}

}
