package nl.thewgbbroz.ld39.tilepicker;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.text.TextRenderer;
import nl.thewgbbroz.ld39.tiles.Tile;
import nl.thewgbbroz.ld39.utils.SoundLoader;

public class TilePicker {
	private static final int OFFSET = 20;
	private static final int WIDTH = 3 * (Tile.TILE_SIZE + 8) + 2 * 16;
	private static final int HEIGHT = 9 * (Tile.TILE_SIZE + 8) + 2 * 16;
	
	private int x, y;
	private Tile current;
	private Map<Point, Tile> tiles = new HashMap<>();
	
	public TilePicker() {
		x = OFFSET;
		y = LD39.HEIGHT - OFFSET - HEIGHT;
		
		for(int i = 0; i < Tile.getTiles().size(); i++) {
			Tile t = Tile.getTiles().get(i);
			
			int row = i % 3;
			int col = i / 3;
			
			int tx = x + 16 + row * (Tile.TILE_SIZE + 8);
			int ty = y + 16 + col * (Tile.TILE_SIZE + 8);
			tiles.put(new Point(tx, ty), t);
		}
	}
	
	public boolean update(int mouseX, int mouseY) {
		if(Gdx.input.isButtonPressed(0)) {
			Tile press = null;
			for(Point p : tiles.keySet()) {
				if(mouseX > p.x && mouseY > p.y && mouseX < p.x + Tile.TILE_SIZE && mouseY < p.y + Tile.TILE_SIZE) {
					press = tiles.get(p);
					break;
				}
			}
			
			if(press != null && press != current) {
				current = press;
				System.out.println("Current tile is " + current.getId());
				
				SoundLoader.getSound("click").play();
			}
		}
		
		return mouseX > x && mouseY > y && mouseX < x + WIDTH && mouseY < y + HEIGHT;
	}
	
	public void render(Batch b, ShapeRenderer sr, TextRenderer tr, int mouseX, int mouseY) {
		sr.begin(ShapeType.Filled);
		sr.setColor(0.5f, 0.5f, 0.5f, 1f);
		sr.rect(x, y, WIDTH, HEIGHT);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.setColor(0f, 0f, 0f, 1f);
		sr.rect(x, y, WIDTH, HEIGHT);
		sr.end();
		
		b.begin();
		
		for(Point p : tiles.keySet()) {
			b.draw(tiles.get(p).getRawTexture(), p.x, p.y, Tile.TILE_SIZE, Tile.TILE_SIZE);
		}
		
		b.end();
		
		Tile hover = null;
		for(Point p : tiles.keySet()) {
			if(mouseX > p.x && mouseY > p.y && mouseX < p.x + Tile.TILE_SIZE && mouseY < p.y + Tile.TILE_SIZE) {
				hover = tiles.get(p);
				break;
			}
		}
		
		if(hover != null) {
			tr.renderTextWithBG(hover.getName() + " ($" + hover.getCost() + ")", b, sr, mouseX, mouseY, 16);
		}
		
		tr.renderText("Tile      picker", b, x + 10, y + HEIGHT - 30, 16, 10);
	}
	
	public Tile getCurrent() {
		return current;
	}
}
