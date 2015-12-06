package test;

import java.util.Map;
import java.util.Set;

import cz.yellen.xpg.common.stuff.GameObject;

abstract public class GameObjectCreator implements GameObject {

	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static GameObject create(final int position, final String type)
	{
		return new GameObjectCreator() {
			
			@Override
			public String getType() {
				// TODO Auto-generated method stub
				return type;
			}
			@Override
			public int getPosition() {
				// TODO Auto-generated method stub
				return position;
			}
		};
	}
	/**
	 * 
	 */
	abstract public int getPosition();

	/**
	 * 
	 */
	abstract public String getType();

	
	public Map<String, String> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getProperty(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set<GameObject> getStuff() {
		// TODO Auto-generated method stub
		return null;
	}


}
