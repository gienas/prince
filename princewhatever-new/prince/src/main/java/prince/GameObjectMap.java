package prince;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.yellen.xpg.common.stuff.GameObject;

public class GameObjectMap {

               private Map<String, List<GameObject>> map = new HashMap<String, List<GameObject>>();
               
               
               //FIXME add dedicated methods addPrince, add...
               public void put( String key,  GameObject o )
               {
                              if (map.containsKey(key))
                              {
                                             map.get(key).add(o);
                              }              
                              else 
                              {
                                             List<GameObject> list = new ArrayList<>();
                                             list.add(o);
                                             map.put(key, list);
                              }              
               }
               
               /**
               * 
                * @param key
               * @return first element from list or null
               */
               public GameObject getFirst(String key)
               {
                              if (map.containsKey(key))
                              {
                                             return map.get(key).get(0);
                              }
                              return null;
               }
               
               /**
               * 
                * @param key
               * @return list or null
               */
               public List<GameObject> getList(String key)
               {
                              if (map.containsKey(key))
                              {
                                             return map.get(key);
                              }
                              return null;
               }
               
}
