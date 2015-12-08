package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.Move;
import cz.yellen.xpg.common.action.Use;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameContext;
import prince.GameObjectEnum;
import prince.domain.AbstractGameObject.ActionRet;


public class GuardGameObject extends AbstractGameObject {

	public static String PROPERTY_HEALTH = "health"; 
	public static String PROPERTY_DEAD = "dead"; 
	
	private Integer healthLevel;
	
	private Boolean dead;
	
	
	public GuardGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionRet processObject() {
		//TODO
		Action retAction = null;
		
		
		if (isObjectBeforePosition() && !isDead().booleanValue() && getContext().getPrince().haveSword() )
		{
			retAction = new  Use(getContext().getPrince().getMySword().getOrgObject(), this.getOrgObject());
		} else if (isObjectBeforePosition() && !isDead().booleanValue())
		{
			//run away
			getContext().changeDirection();
			retAction = new Move(getContext().getCurrentDirection());
		}	
			
		// drink bootles
		if ( getContext().getPrince().getHealthLevel() < 5 && getContext().getPrince().hasMintBoottle()) {
			System.out.println("bootles size:" + getContext().getPrince().getMyMintBootles().size());
			for (BootleGameObject o : getContext().getPrince().getMyMintBootles()) {
				if (o.getVolume() > 0)
				{
					retAction = new Use(o.getOrgObject(), getContext().getPrince().getOrgObject());
				}	
			}
		}	
		
		
		return (retAction == null) ? null: new ActionRet(retAction, true);
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

	
	public Integer getHealthLevel() {
		return healthLevel;
	}
	
	public Boolean isDead() {
		return dead;
	}
	
}
