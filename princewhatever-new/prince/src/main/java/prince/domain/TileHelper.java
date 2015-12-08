package prince.domain;

import prince.GameContext;

public class TileHelper {

	GameContext context;
	
	Integer testedTile = null;
	
	public TileHelper() {
		// TODO Auto-generated constructor stub
	}
	
	public void setContext(GameContext context) {
		this.context = context;
	}

	public Integer getTestedTile() {
		return testedTile;
	}

	public void setTestedTile(Integer testedTile) {
		this.testedTile = testedTile;
	}

	boolean isTileCurrentTested(Integer tile)
	{
		return (testedTile != null && tile.equals(testedTile));
	}
	
	boolean isAnyTileCurrentTested()
	{
		return testedTile != null; 
	}
	
	public void stopTestTile()
	{
		testedTile = null;
	}
	
	public void startTestTile(Integer tile)
	{
		testedTile = tile;
	}
}
