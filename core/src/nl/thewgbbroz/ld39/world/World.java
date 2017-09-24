package nl.thewgbbroz.ld39.world;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;

import nl.thewgbbroz.ld39.tiles.Tile;
import nl.thewgbbroz.ld39.tiles.TileData;

public class World {
	private int w, h;
	private Tile[][] tiles;
	
	private List<TileData> tileData = new ArrayList<>();
	
	private float diff = 0.9f;
	
	private int power;
	private int population;
	private boolean hasPowerStation;
	
	public World(int w, int h) {
		this.w = w;
		this.h = h;
		
		tiles = new Tile[w][h];
	}
	
	public void update(float delta) {
		for(TileData td : tileData)
			td.update(delta);
		
		if(hasPowerStation)
			diff += delta / 30;
		
		if(diff > 2)
			diff = 2;
		
		int pow = 0;
		int amount = 0;
		
		for(TileData td : tileData) {
			if(td.getTile().hasPower(this, td.getRow(), td.getCol())) {
				pow += td.getPower();
				amount++;
			}
		}
		
		if(amount == 0)
			power = 0;
		else
			power = pow / amount;
		
		population = 0;
		for(TileData td : tileData) {
			if(td.getResidents() != -1)
				population += td.getResidents();
		}
	}
	
	public void render(Batch b) {
		b.begin();
		
		for(int r = 0; r < w; r++) {
			for(int c = 0; c < h; c++) {
				Tile t = tiles[r][c];
				if(t == null)
					continue;
				
				b.draw(t.getTexture(this, r, c), r * Tile.TILE_SIZE, c * Tile.TILE_SIZE);
			}
		}
		
		b.end();
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Tile getTile(int r, int c) {
		if(r < 0 || c < 0 || r >= w || c >= h)
			return null;
		
		return tiles[r][c];
	}
	
	public void setTile(int r, int c, Tile t) {
		if(r < 0 || c < 0 || r >= w || c >= h)
			return;
		
		Tile old = tiles[r][c];
		
		tiles[r][c] = t;
		
		if(t != old) {
			removeTileData(r, c);
			if(t != null) {
				tileData.add(new TileData(this, t, r, c));
				System.out.println("Generated tile data");
			}
		}
		
		if(t == Tile.POWER_STATION)
			hasPowerStation = true;
	}
	
	public TileData getTileData(int r, int c) {
		for(TileData td : tileData) {
			if(td.getRow() == r && td.getCol() == c) {
				return td;
			}
		}
		
		return null;
	}
	
	public int getAvgPower() {
		return power;
	}
	
	public float getDifficulty() {
		return diff;
	}
	
	public int getPopulation() {
		return population;
	}
	
	public String getHappinessStr() {
		if(population == 0)
			return "non existent";
		
		if(power > 90)
			return "happy";
		else if(power > 70)
			return "ok";
		else if(power > 60)
			return "neutral";
		else if(power > 45)
			return "disappointed";
		else if(power > 30)
			return "angry";
		
		return "super angry";
	}
	
	public float getHappiness() {
		if(population == 0)
			return 0;
		
		if(power > 90)
			return 1.5f;
		else if(power > 70)
			return 1;
		else if(power > 60)
			return 0.5f;
		else if(power > 45)
			return 0.1f;
		else if(power > 30)
			return 0;
		
		return 0;
	}
	
	public int getNumFactories() {
		int amount = 0;
		
		for(TileData td : tileData) {
			if(td.getTile() == Tile.FACTORY)
				amount++;
		}
		
		return amount;
	}
	
	private void removeTileData(int r, int c) {
		for(int i = 0; i < tileData.size(); i++) {
			TileData td = tileData.get(i);
			if(td.getRow() == r && td.getCol() == c) {
				System.out.println("Removed tile data");
				tileData.remove(i--);
			}
		}
	}
}
