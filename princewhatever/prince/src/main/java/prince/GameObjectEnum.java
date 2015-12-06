package prince;

import java.lang.reflect.InvocationTargetException;

import cz.yellen.xpg.common.stuff.GameObject;
import prince.domain.AbstractGameObject;
import prince.domain.BootleGameObject;
import prince.domain.ChopperGameObject;
import prince.domain.GateGameObject;
import prince.domain.GuardGameObject;
import prince.domain.PitGameObject;
import prince.domain.PrinceGameObject;
import prince.domain.SwordGameObject;
import prince.domain.WallGameObject;

public enum GameObjectEnum {

	PRINCE("prince",  PrinceGameObject.class),
	GATE("gate",  GateGameObject.class),
	WALL("wall",  WallGameObject.class),
	PIT("pit",  PitGameObject.class),
	GUARD("guard",  GuardGameObject.class),
	SWORD("sword",  SwordGameObject.class),
	CHOPPER("chopper",  ChopperGameObject.class),
	BOOTLE("bottle", BootleGameObject.class);
	
	private String type;
	private Class<? extends AbstractGameObject> clazz;
	
	
	GameObjectEnum(String t, Class<? extends AbstractGameObject> cl)
	{
		type = t;
		clazz = cl;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public Class<? extends AbstractGameObject> getClazz() {
		return clazz;
	}


	public void setClazz(Class<? extends AbstractGameObject> clazz) {
		this.clazz = clazz;
	}
	

	public static AbstractGameObject createObject(GameObject go)
	{
		for (GameObjectEnum e : GameObjectEnum.values())
		{
			if (go.getType().equals(e.getType()) )
			{
				try {
				
					return (AbstractGameObject) e.getClazz().getConstructor(GameObject.class, GameObjectEnum.class).newInstance(go, e);
					
				} catch (Exception exc)
				{
					exc.printStackTrace();
					throw new IllegalArgumentException("Param  " + go.getType() + " problem with class initialization");
				}
			}	
		}	
		throw new IllegalArgumentException("Param  " + go.getType() + " not implemented yet");
	}
	
}
