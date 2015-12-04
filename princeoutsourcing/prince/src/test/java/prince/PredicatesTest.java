package prince;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import cz.yellen.xpg.common.stuff.GameObject;
import throwable.prince.util.GameSituationPredicates;

public class PredicatesTest {

	Set<GameObject> objects = new HashSet<GameObject>();
	
	@Before
	public void init() {
		
		objects.add(new GameObject() {
			
			public String getType() {
				// TODO Auto-generated method stub
				return "prince";
			}
			
			public Set<GameObject> getStuff() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public String getProperty(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
			
			public Map<String, String> getProperties() {
				// TODO Auto-generated method stub
				return null;
			}
			
			public int getPosition() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			public int getId() {
				// TODO Auto-generated method stub
				return 0;
			}
		});
	}
	
	@Test
	public void test() {
		GameObject prince = GameSituationPredicates.getObject("prince", objects);
		assertNotNull(prince);
	}

}
