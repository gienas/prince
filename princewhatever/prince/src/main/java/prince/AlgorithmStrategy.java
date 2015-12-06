package prince;

import java.util.List;
import java.util.Map;

public interface AlgorithmStrategy {
	
	/**
	 * 
	 * @return order element processing
	 */
	public Map<GameObjectEnum, Integer> getOrderProcessing ();
	
}
