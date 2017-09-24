package nl.thewgbbroz.ld39.tiles;

import com.badlogic.gdx.graphics.Texture;

import nl.thewgbbroz.ld39.utils.SoundLoader;
import nl.thewgbbroz.ld39.utils.TextureLoader;
import nl.thewgbbroz.ld39.world.World;

public class TilePowerLine extends Tile {
	private static final String VERTICAL = "powerV";
	private static final String HORIZONTAL = "powerH";
	private static final String CORNER_LEFT_UP = "powerC_LU";
	private static final String CORNER_RIGHT_UP = "powerC_RU";
	private static final String CORNER_LEFT_DOWN = "powerC_LD";
	private static final String CORNER_RIGHT_DOWN = "powerC_RD";
	private static final String ALL_CORNERS = "powerCorners";
	
	protected TilePowerLine(int cost, float diffFactor) {
		super(true, false, cost, diffFactor, "Power line", SoundLoader.getSound("power_line_place"));
	}
	
	@Override
	public Texture getTexture(World w, int row, int col) {
		if(w == null)
			return TextureLoader.getTexture(ALL_CORNERS);
		
		Tile up = w.getTile(row, col + 1);
		Tile down = w.getTile(row, col - 1);
		
		Tile left = w.getTile(row - 1, col);
		Tile right = w.getTile(row + 1, col);
		
		/*
		if((up == POWER_LINE || down == POWER_LINE) && left != POWER_LINE && right != POWER_LINE)
			return TextureLoader.getTexture(VERTICAL);
		*/
		if((hasPower(up, w, row, col + 1) || hasPower(down, w, row, col - 1)) && !hasPower(left, w, row - 1, col) && !hasPower(right, w, row + 1, col))
			return TextureLoader.getTexture(VERTICAL);
		
		/*
		if((left == POWER_LINE || right == POWER_LINE) && up != POWER_LINE && down != POWER_LINE)
			return TextureLoader.getTexture(HORIZONTAL);
		*/
		if((hasPower(left, w, row - 1, col) || hasPower(right, w, row + 1, col)) && !hasPower(up, w, row, col + 1) && !hasPower(down, w, row, col - 1))
			return TextureLoader.getTexture(HORIZONTAL);
		
		/*
		if(left == POWER_LINE && up == POWER_LINE && right != POWER_LINE && down != POWER_LINE)
			return TextureLoader.getTexture(CORNER_LEFT_UP);
		*/
		if(hasPower(left, w, row - 1, col) && hasPower(up, w, row, col + 1) && !hasPower(right, w, row + 1, col) && !hasPower(down, w, row, col - 1))
			return TextureLoader.getTexture(CORNER_LEFT_UP);
		
		/*
		if(right == POWER_LINE && up == POWER_LINE && left != POWER_LINE && down != POWER_LINE)
			return TextureLoader.getTexture(CORNER_RIGHT_UP);
		*/
		if(hasPower(right, w, row + 1, col) && hasPower(up, w, row, col + 1) && !hasPower(left, w, row - 1, col) && !hasPower(down, w, row, col - 1))
			return TextureLoader.getTexture(CORNER_RIGHT_UP);
		
		/*
		if(left == POWER_LINE && down == POWER_LINE && right != POWER_LINE && up != POWER_LINE)
			return TextureLoader.getTexture(CORNER_LEFT_DOWN);
		*/
		if(hasPower(left, w, row - 1, col) && hasPower(down, w, row, col - 1) && !hasPower(right, w, row + 1, col) && !hasPower(up, w, row, col + 1))
			return TextureLoader.getTexture(CORNER_LEFT_DOWN);
		
		/*
		if(right == POWER_LINE && down == POWER_LINE && left != POWER_LINE && up != POWER_LINE)
			return TextureLoader.getTexture(CORNER_RIGHT_DOWN);
		*/
		if(hasPower(right, w, row + 1, col) && hasPower(down, w, row, col - 1) && !hasPower(left, w, row - 1, col) && !hasPower(up, w, row, col + 1))
			return TextureLoader.getTexture(CORNER_RIGHT_DOWN);
		
		return TextureLoader.getTexture(ALL_CORNERS);
	}
	
	private boolean hasPower(Tile tile, World w, int row, int col) {
		if(tile == null)
			return false;
		
		return tile.hasPower(w, row, col);
	}
}
