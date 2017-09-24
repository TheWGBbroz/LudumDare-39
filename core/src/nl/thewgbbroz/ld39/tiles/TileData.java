package nl.thewgbbroz.ld39.tiles;

import java.util.ArrayList;
import java.util.List;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.world.World;

public class TileData {
	private World world;
	private Tile tile;
	private int row, col;
	
	private float power;
	private int residents = -1;
	
	public TileData(World world, Tile tile, int row, int col) {
		this.world = world;
		this.tile = tile;
		this.row = row;
		this.col = col;
		
		if(tile == Tile.HOUSE) {
			residents = LD39.RAND.nextInt(5) + 1;
			System.out.println("House population is " + residents);
		}else if(tile == Tile.HOTEL) {
			residents = LD39.RAND.nextInt(30) + 51;
			System.out.println("Hotel population is " + residents);
		}
	}
	
	public void update(float delta) {
		if(tile == Tile.HOTEL) {
			double d = LD39.RAND.nextDouble();
			if(d < 0.01) {
				if(d < 0.003) {
					// 0.0 - 0.003
					residents--;
					if(residents < 10)
						residents = 10;
				}else{
					// 0.01 - 0.007
					residents++;
					if(residents > 250)
						residents = 250;
				}
			}
		}
		
		if(tile.hasPower(world, row, col) && !tile.canEmitPower(world, row, col)) {
			float avgPow = 0;
			int amount = 0;
			
			for(TileData td : getNeighbors()) {
				if(td.getTile().hasPower(world, td.getRow(), td.getCol())) {
					avgPow += td.getPower();
					amount++;
				}
			}
			
			if(amount > 0) {
				avgPow /= amount;
			}
			
			this.power = avgPow;
			this.power -= delta * 10 * world.getDifficulty() * tile.getDifficultyFactor();
			
			if(this.power < 0)
				this.power = 0;
		}
		
		if(tile == Tile.POWER_STATION)
			power = 100;
		else if(tile == Tile.SOLAR_PANEL)
			power = 80;
	}
	
	private List<TileData> getNeighbors() {
		List<TileData> res = new ArrayList<>();
		
		TileData left = world.getTileData(row - 1, col);
		TileData right = world.getTileData(row + 1, col);
		TileData up = world.getTileData(row, col + 1);
		TileData down = world.getTileData(row, col - 1);
		
		if(left != null) res.add(left);
		if(right != null) res.add(right);
		if(up != null) res.add(up);
		if(down != null) res.add(down);
		
		return res;
	}
	
	public float getPower() {
		return power;
	}
	
	public int getResidents() {
		if(power < 5 || !isConnectedToRoad() || residents <= 0)
			return -1;
		
		if(tile == Tile.HOTEL)
			return residents + world.getNumFactories() * 10;
		
		return residents;
	}
	
	public World getWorld() {
		return world;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}
	
	public boolean isConnectedToRoad() {
		for(TileData td : getNeighbors()) {
			if(td.getTile() == Tile.ROAD)
				return true;
		}
		
		return false;
	}
}
