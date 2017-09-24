package nl.thewgbbroz.ld39.tiles;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import nl.thewgbbroz.ld39.utils.SoundLoader;
import nl.thewgbbroz.ld39.utils.TextureLoader;
import nl.thewgbbroz.ld39.world.World;

public class Tile {
	private static int nextId;
	private static List<Tile> tiles = new ArrayList<>();
	
	public static final int TILE_SIZE = 32;
	
	public static final Tile ROAD = new TileRoad(20, 0.2f);
	public static final Tile HOUSE = new Tile("house", true, false, 150, 0.8f, "House", null);
	public static final Tile POWER_LINE = new TilePowerLine(40, 0.05f);
	public static final Tile POWER_STATION = new TilePowerStation(500, 1f);
	public static final Tile HOTEL = new Tile("hotel", true, false, 2000, 5f, "Hotel", null);
	public static final Tile SOLAR_PANEL = new TileSolarPanel(100, 1f);
	public static final Tile FACTORY = new Tile("factory", true, false, 800, 1.5f, "Factory", null);
	
	public static Tile getById(int id) {
		for(Tile t : tiles) {
			if(t.getId() == id)
				return t;
		}
		
		return null;
	}
	
	public static List<Tile> getTiles() {
		return tiles;
	}
	
	
	private int id;
	private String texName;
	private boolean hasPower;
	private boolean emitPower;
	private int cost;
	private float diffFactor;
	private String name;
	private Sound placeSound;
	
	protected Tile(String texName, boolean hasPower, boolean emitPower, int cost, float diffFactor, String name, Sound placeSound) {
		this(hasPower, emitPower, cost, diffFactor, name, placeSound);
		
		this.texName = texName;
	}
	
	protected Tile(boolean hasPower, boolean emitPower, int cost, float diffFactor, String name, Sound placeSound) {
		this.id = nextId++;
		this.hasPower = hasPower;
		this.emitPower = emitPower;
		this.cost = cost;
		this.diffFactor = diffFactor;
		this.name = name;
		this.placeSound = placeSound;
		
		tiles.add(this);
	}
	
	public Texture getTexture(World w, int row, int col) {
		return TextureLoader.getTexture(texName);
	}
	
	public Texture getRawTexture() {
		return getTexture(null, -1, -1);
	}
	
	public int getId() {
		return id;
	}
	
	public boolean hasPower(World w, int row, int col) {
		return hasPower;
	}
	
	public boolean canEmitPower(World w, int row, int col) {
		return emitPower;
	}
	
	public int getCost() {
		return cost;
	}
	
	public float getDifficultyFactor() {
		return diffFactor;
	}
	
	public String getName() {
		return name;
	}
	
	public Sound getPlaceSound() {
		return placeSound == null ? SoundLoader.getSound("click") : placeSound;
	}
}
