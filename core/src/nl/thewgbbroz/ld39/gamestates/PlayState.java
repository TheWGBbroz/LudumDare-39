package nl.thewgbbroz.ld39.gamestates;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.tilepicker.TilePicker;
import nl.thewgbbroz.ld39.tiles.Tile;
import nl.thewgbbroz.ld39.tiles.TileData;
import nl.thewgbbroz.ld39.tips.TipBox;
import nl.thewgbbroz.ld39.utils.SoundLoader;
import nl.thewgbbroz.ld39.utils.Utils;
import nl.thewgbbroz.ld39.world.World;

public class PlayState extends GameState {
	private static final String[] MONTH_NAMES = {
			"January",
			"February",
			"March",
			"April",
			"May",
			"June",
			"July",
			"August",
			"September",
			"October",
			"November",
			"December"
	};
	private static final float DAY_LENGTH = 10f;
	
	private World world;
	
	private TilePicker tilePicker;
	private float timer;
	private float money = 5000;
	private TipBox tb;
	
	private boolean placedPowerStation = false;
	
	private int year;
	private int month;
	private float dateTimer;
	
	private String cityName;
	
	private boolean morePowerTipbox = false;
	private float lastSoundPlay = 0;
	private float brokeTimer = 0;
	
	protected PlayState(GameStateManager gsm) {
		super(gsm);
		
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		
		System.out.println("Current date: " + (month + 1) + "-" + year + " (" + MONTH_NAMES[month] + ", " + year + ")");
		
		cityName = getRandomCityName();
		
		world = new World(LD39.WIDTH / Tile.TILE_SIZE + Tile.TILE_SIZE, LD39.HEIGHT / Tile.TILE_SIZE + Tile.TILE_SIZE);
		tilePicker = new TilePicker();
		
		tb = new TipBox("You are the mayor of          '" + cityName + "'. Build it up!", 16, 30);
	}
	
	public void update(float delta) {
		timer += delta;
		dateTimer += delta;
		
		if(timer > 12 && !morePowerTipbox) {
			morePowerTipbox = true;
			tb = new TipBox("As time goes on people need more power!", 16, 28);
		}
		
		if(world.getPopulation() > 0)
			money += delta * 50 * world.getHappiness();
		
		if(dateTimer > DAY_LENGTH) {
			dateTimer = 0;
			month++;
			
			if(month >= 12) {
				month = 0;
				year++;
			}
		}
		
		world.update(delta);
		
		boolean mouseInTilePicker = tilePicker.update(gsm.getMouseX(), gsm.getMouseY());
		
		int row = gsm.getMouseX() / Tile.TILE_SIZE;
		int col = gsm.getMouseY() / Tile.TILE_SIZE;
		if(!mouseInTilePicker) {
			if(Gdx.input.isButtonPressed(0)) {
				if(tilePicker.getCurrent() != null && world.getTile(row, col) != tilePicker.getCurrent()) {
					int cost = tilePicker.getCurrent().getCost();
					if(money < cost) {
						System.out.println("Insufficient balance");
						tb = new TipBox("Insufficient balance!", 16, -1);
						
						if(timer - lastSoundPlay > 0.5f) {
							SoundLoader.getSound("error").play();
							lastSoundPlay = timer;
						}
					}else{
						world.setTile(row, col, tilePicker.getCurrent());
						money -= cost;
						
						tilePicker.getCurrent().getPlaceSound().play();
						
						if(tilePicker.getCurrent() == Tile.POWER_STATION && !placedPowerStation) {
							placedPowerStation = true;
							tb = new TipBox("For the power station to work people need to be able to get there!", 16, 30);
						}
					}
				}
			}else if(Gdx.input.isButtonPressed(1)) {
				if(world.getTile(row, col) != null) {
					world.setTile(row, col, null);
					
					SoundLoader.getSound("explosion").play();
				}
			}
		}
		
		if(tb != null)
			tb.update(delta);
		
		if(world.getHappinessStr().contains("angry")) {
			if(tb.isClosed())
				tb = new TipBox("Your citizens are angry! Make sure they have enhough power  in their houses.", 16, 30);
		}
		
		if(world.getHappiness() == 0 && money < Tile.POWER_STATION.getCost()) {
			brokeTimer += delta;
			if(brokeTimer > 5) {
				System.out.println("Player is broke");
				gsm.crossStateValues.put("population", (int) world.getPopulation());
				gsm.setState(GameStateManager.GAME_OVER_STATE);
			}
		}else{
			brokeTimer = 0;
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.M)) {
			tb = new TipBox("Money cheat activated", 16, 30);
			money = 1000000000;
		}
	}
	
	public void render() {
		world.render(spriteBatch());
		
		tilePicker.render(spriteBatch(), shapeRenderer(), textRenderer(), gsm.getMouseX(), gsm.getMouseY());
		
		int avgPow = world.getAvgPower();
		int size = 16;
		if(avgPow < 50) {
			size += (int) ((Math.sin(timer * 2) + 1) * 2d);
		}
		
		textRenderer().renderText("Avg. power: " + avgPow, spriteBatch(), 20, 20, size);
		
		int row = gsm.getMouseX() / Tile.TILE_SIZE;
		int col = gsm.getMouseY() / Tile.TILE_SIZE;
		TileData td = world.getTileData(row, col);
		if(td != null) {
			textRenderer().renderTextWithBG("Power: " + (int) td.getPower(), spriteBatch(), shapeRenderer(), gsm.getMouseX(), gsm.getMouseY(), 16);
			if(td.getResidents() > 0)
				textRenderer().renderTextWithBG("Residents: " + td.getResidents(), spriteBatch(), shapeRenderer(), gsm.getMouseX(), gsm.getMouseY() + 24, 16);
		}
		
		textRenderer().renderText("Date: " + MONTH_NAMES[month] + ", " + year, spriteBatch(), 20, 40, 16);
		textRenderer().renderText("Money: " + Utils.formatLargeNumber(money), spriteBatch(), 20, 60, 16);
		textRenderer().renderText("Population: " + world.getPopulation(), spriteBatch(), 20, 80, 16);
		textRenderer().renderText("Your citizens are " + world.getHappinessStr(), spriteBatch(), 20, 100, 16);
		
		if(tb != null)
			tb.render(textRenderer(), spriteBatch(), shapeRenderer());
		
		/*
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		shapeRenderer().begin(ShapeType.Filled);
		shapeRenderer().setColor(0f, 0f, 0f, ((float) Math.sin(timer / 10f) + 1f) / 2f * 0.3f);
		shapeRenderer().rect(0, 0, LD39.WIDTH, LD39.HEIGHT);
		shapeRenderer().end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		*/
	}
	
	private static String getRandomCityName() {
		BufferedReader br = new BufferedReader(new InputStreamReader(PlayState.class.getResourceAsStream("/cities.txt")));
		List<String> cities = new ArrayList<>();
		
		String line;
		try {
			while((line = br.readLine()) != null) {
				cities.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return cities.get(LD39.RAND.nextInt(cities.size()));
	}
}
