package nl.thewgbbroz.ld39.tiles;

import com.badlogic.gdx.graphics.Texture;

import nl.thewgbbroz.ld39.utils.TextureLoader;
import nl.thewgbbroz.ld39.world.World;

public class TileRoad extends Tile {
	private static final String VERTICAL = "roadV";
	private static final String HORIZONTAL = "roadH";
	private static final String CORNER_LEFT_UP = "roadC_LU";
	private static final String CORNER_RIGHT_UP = "roadC_RU";
	private static final String CORNER_LEFT_DOWN = "roadC_LD";
	private static final String CORNER_RIGHT_DOWN = "roadC_RD";
	private static final String ALL_CORNERS = "roadCorners";
	
	protected TileRoad(int cost, float diffFactor) {
		super(false, false, cost, diffFactor, "Road", null);
	}
	
	@Override
	public Texture getTexture(World w, int row, int col) {
		if(w == null)
			return TextureLoader.getTexture(ALL_CORNERS);
		
		Tile up = w.getTile(row, col + 1);
		Tile down = w.getTile(row, col - 1);
		
		Tile left = w.getTile(row - 1, col);
		Tile right = w.getTile(row + 1, col);
		
		if((up == ROAD || down == ROAD) && left != ROAD && right != ROAD)
			return TextureLoader.getTexture(VERTICAL);
		
		if((left == ROAD || right == ROAD) && up != ROAD && down != ROAD)
			return TextureLoader.getTexture(HORIZONTAL);
		
		if(left == ROAD && up == ROAD && right != ROAD && down != ROAD)
			return TextureLoader.getTexture(CORNER_LEFT_UP);
		
		if(right == ROAD && up == ROAD && left != ROAD && down != ROAD)
			return TextureLoader.getTexture(CORNER_RIGHT_UP);
		
		if(left == ROAD && down == ROAD && right != ROAD && up != ROAD)
			return TextureLoader.getTexture(CORNER_LEFT_DOWN);
		
		if(right == ROAD && down == ROAD && left != ROAD && up != ROAD)
			return TextureLoader.getTexture(CORNER_RIGHT_DOWN);
		
		return TextureLoader.getTexture(ALL_CORNERS);
	}
}
