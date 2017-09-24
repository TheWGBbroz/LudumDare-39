package nl.thewgbbroz.ld39.tiles;

import nl.thewgbbroz.ld39.world.World;

public class TileSolarPanel extends Tile {
	protected TileSolarPanel(int cost, float diffFactor) {
		super("solarPanel", true, true, cost, diffFactor, "Solar panel", null);
	}
	
	public boolean hasPower(World w, int row, int col) {
		Tile left = w.getTile(row - 1, col);
		Tile right = w.getTile(row + 1, col);
		Tile up = w.getTile(row, col + 1);
		Tile down = w.getTile(row, col - 1);
		
		return left == ROAD || right == ROAD || up == ROAD || down == ROAD;
	}
	
	public boolean canEmitPower(World w, int row, int col) {
		return hasPower(w, row, col);
	}
}
