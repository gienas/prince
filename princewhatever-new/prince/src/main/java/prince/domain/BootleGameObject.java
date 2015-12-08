package prince.domain;

import cz.yellen.xpg.common.action.Action;
import cz.yellen.xpg.common.action.PickUp;
import cz.yellen.xpg.common.action.Use;
import cz.yellen.xpg.common.stuff.GameObject;
import prince.GameObjectEnum;
import prince.domain.AbstractGameObject.ActionRet;

public class BootleGameObject extends AbstractGameObject implements Obstackle {

	static enum ODOUR {
		puke, mint
	};

	public static String PROPERTY_ODOUR = "odour";
	public static String PROPERTY_VOLUME = "volume";

	private ODOUR odour;

	private Integer volume;

	public BootleGameObject(GameObject o, GameObjectEnum type) {
		super(o, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActionRet processObject() {
		// TODO
		Action retAction = null;
		if (isObjectOnMyPosition()) {
			retAction = new PickUp(this.getOrgObject());
			System.out.println("get bootle");
		}
		
		return (retAction == null) ? null: new ActionRet(retAction, true);
	}

	@Override
	public void fillObject() {

		// odour
		String o = getProperty(PROPERTY_ODOUR);
		if (o != null && !o.trim().isEmpty()) {
			odour = o.equals(ODOUR.mint.name()) ? ODOUR.mint : ODOUR.puke;
		}
		// volume
		String lvolume = getProperty(PROPERTY_VOLUME);
		if (lvolume != null && !lvolume.trim().isEmpty()) {
			volume = Integer.valueOf(lvolume);
		}
	}

	public Integer getVolume() {
		return volume;
	}

	public ODOUR getOdour() {
		return odour;
	}

	public boolean hasMint() {
		return odour.equals(ODOUR.mint);
	}

}
