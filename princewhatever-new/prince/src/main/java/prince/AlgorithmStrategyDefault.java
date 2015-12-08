package prince;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import prince.domain.GateGameObject;

public class AlgorithmStrategyDefault  implements AlgorithmStrategy{

	@Override
	public Map<GameObjectEnum, Integer> getOrderProcessing() {
		// TODO Auto-generated method stub	
		Map<GameObjectEnum, Integer> ordermap = new LinkedHashMap<>();
		ordermap.put(GameObjectEnum.PRINCE,1);
		ordermap.put(GameObjectEnum.WALL,3);
		ordermap.put(GameObjectEnum.PIT,4);
		ordermap.put(GameObjectEnum.GUARD,5);
		ordermap.put(GameObjectEnum.SWORD,6);
		ordermap.put(GameObjectEnum.CHOPPER,7);
		ordermap.put(GameObjectEnum.BOOTLE,8);
		ordermap.put(GameObjectEnum.TILE,9);
		ordermap.put(GameObjectEnum.PORTCULLIS,10);
		ordermap.put(GameObjectEnum.GATE,11);
		
		return ordermap; 
	}

	public Set<GameObjectEnum> procesOnlyTypes()
	{
		Set<GameObjectEnum> set = new HashSet<>();
		set.add(GameObjectEnum.PRINCE);
		set.add(GameObjectEnum.WALL);
		set.add(GameObjectEnum.PORTCULLIS);
		set.add(GameObjectEnum.GATE);
		set.add(GameObjectEnum.TILE);
		return set;
	}

	
}
