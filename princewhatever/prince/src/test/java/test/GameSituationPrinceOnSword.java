package test;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import cz.yellen.xpg.common.stuff.GameObject;

public class GameSituationPrinceOnSword extends GameSituationImpl {

	@Override
	public Set<GameObject> getGameObjects() {
		// TODO Auto-generated method stub
		Set<GameObject> set = new LinkedHashSet<GameObject>();
		set.add( GameObjectCreator.create(0, "sword"));
		return set;
	}

}
