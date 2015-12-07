package prince.domain;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import com.ctc.wstx.dtd.StructValidator;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Direction;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameContext;
import prince.GameObjectEnum;

/**
 * Abstract wrapper class to all game objects 
 * @author neugeeug
 *
 */
public abstract class AbstractGameObject {

	private GameObject orgObject;

	private GameObjectEnum enumType;
	
	private Set<AbstractGameObject> stuff = new LinkedHashSet<>();

	public AbstractGameObject(GameObject o, GameObjectEnum type) {
		// TODO Auto-generated constructor stub
		orgObject = o;
		enumType = type;
		fillObject();
		setStuff();
	}
	/**
	 * 
	 */
	public abstract Action processObject();

	/**
	 * 
	 */
	public abstract void fillObject();

	public int getId() {
		// TODO Auto-generated method stub
		return orgObject.getId();
	}

	public int getPosition() {
		// TODO Auto-generated method stub
		return orgObject.getPosition();
	}

	public Map<String, String> getProperties() {
		// TODO Auto-generated method stub
		return orgObject.getProperties();
	}

	public String getProperty(String arg0) {
		// TODO Auto-generated method stub
		return orgObject.getProperty(arg0);
	}

	// FIXME
	public Set<AbstractGameObject> getStuff() {
		// TODO Auto-generated method stub
		return stuff;
	}

	private void setStuff() {
		if (getOrgObject().getStuff() == null)
			return;

		for (GameObject go : getOrgObject().getStuff()) {
			AbstractGameObject ago = GameObjectEnum.createObject(go);
			stuff.add(ago);
		}
		
	}

	public String getType() {
		// TODO Auto-generated method stub
		return orgObject.getType();
	}

	public GameObjectEnum getEnumType() {
		return enumType;
	}
	
	public GameObject getOrgObject() {
		return orgObject;
	}

	public GameContext getContext() {
		return GameContext.getContext();
	}

	/**
	 * Check if object if position before in current direction
	 * 
	 * @return
	 */
	public boolean isObjectBeforePosition() {
		return (getContext().isCurrentDirectionForward() && getPosition() == 1)
				|| (getContext().isCurrentDirectionBackward() && getPosition() == -1);
	}

	/**
	 * Check if object if anywhere position before in current direction
	 * 
	 * @return
	 */
	public boolean isObjectAnywhereBefore() {
		return (getContext().isCurrentDirectionForward() && getContext().getPrince().getPosition() < getPosition())
				|| (getContext().isCurrentDirectionBackward()
						&& getContext().getPrince().getPosition() > getPosition());
	}

	public boolean isObjectAnywhereBehind() {
		return (getContext().isCurrentDirectionForward() && getContext().getPrince().getPosition() > getPosition())
				|| (getContext().isCurrentDirectionBackward()
						&& getContext().getPrince().getPosition() < getPosition());
	}
	
	public boolean isObjectOnMyPosition() {
		return getPosition() == getContext().getPrince().getPosition();
	}

	// public boolean isObject

}
