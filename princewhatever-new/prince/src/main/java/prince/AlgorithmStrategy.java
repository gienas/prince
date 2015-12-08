package prince;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface AlgorithmStrategy {
	
	/**
	 * 
	 * @return order element processing
	 */
	public Map<GameObjectEnum, Integer> getOrderProcessing ();
	
	public Set<GameObjectEnum> procesOnlyTypes();
	
}
