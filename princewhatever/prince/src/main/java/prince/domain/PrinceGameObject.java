package prince.domain;

import java.util.ArrayList;
import java.util.List;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;


public class PrinceGameObject extends AbstractGameObject implements Obstackle {

	public static String PROPERTY_HEALTH = "health"; 
	public static String PROPERTY_DEAD = "dead"; 
	
	private Integer healthLevel;
	
	private Boolean dead;
	
	
	public PrinceGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Action processObject() {
		//TODO
		return null;
	}

	@Override
	public void fillObject() {
		
		//convert health
		String health = getProperty(PROPERTY_HEALTH);
		if (health != null && !health.trim().isEmpty())
		{
			healthLevel = Integer.valueOf(health);
		}	
		
		//convert dead
		String ldead = getProperty(PROPERTY_DEAD);
		if (ldead != null && !ldead.trim().isEmpty())
		{
			dead = Boolean.valueOf(ldead);
		}	
	}

	public boolean haveSword()
	{
		for ( AbstractGameObject o: getStuff())
		{
			if (o.getEnumType() == GameObjectEnum.SWORD)
				return true;
		}	
		return false;
	}
	
	public SwordGameObject getMySword()
	{
		for ( AbstractGameObject o: getStuff())
		{
			if (o.getEnumType() == GameObjectEnum.SWORD)
				return (SwordGameObject)o;
		}	
		throw new RuntimeException("Don't have sword");
	}
	
	public boolean hasMintBoottle()
	{
		for ( AbstractGameObject o: getStuff())
		{
			if (o.getEnumType().equals(GameObjectEnum.BOOTLE) && ((BootleGameObject)o).hasMint() )
			{	
				return true;
			}	
		}	
		return false;
	}
	
	public List<BootleGameObject> getMyMintBootles()
	{
		List<BootleGameObject> bootles = new ArrayList<>();
		for ( AbstractGameObject o: getStuff())
		{
			if (o.getEnumType().equals(GameObjectEnum.BOOTLE) && ((BootleGameObject)o).hasMint() )
			{
				bootles.add((BootleGameObject)o);
			}	
		}	
		return bootles;
	}
	
	
	public Integer getHealthLevel() {
		return healthLevel;
	}
	
	
}
