package nl.thewgbbroz.ld39.tips;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.text.TextRenderer;

public class TipBox {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 100;
	
	private String text;
	private int size;
	private int lineCutoff;
	private float timeAlive;
	private float x, y;
	private int state = 0;
	
	public TipBox(String text, int size, int lineCutoff) {
		this.text = text;
		this.size = size;
		this.lineCutoff = lineCutoff;
		
		x = LD39.WIDTH - WIDTH;
		y = -HEIGHT;
	}
	
	public void update(float delta) {
		timeAlive += delta;
		
		if(state == 0) {
			y += delta * 90;
			if(y > 0) {
				y = 0;
				state = 1;
			}
		}else if(state == 1) {
			if(timeAlive > 7) {
				state = 2;
			}
		}else if(state == 2) {
			y -= delta * 60;
			if(y < -HEIGHT) {
				y = -HEIGHT;
				state = 3;
			}
		}
	}
	
	public int getState() {
		return state;
	}
	
	public boolean isClosed() {
		return state == 3;
	}
	
	public void render(TextRenderer tr, Batch b, ShapeRenderer sr) {
		sr.begin(ShapeType.Filled);
		sr.setColor(0.7f, 0.7f, 0.7f, 1f);
		sr.rect(x, y, WIDTH, HEIGHT);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.setColor(0f, 0f, 0f, 0f);
		sr.rect(x, y, WIDTH, HEIGHT);
		sr.end();
		
		tr.renderText(text, b, (int) x + 20, (int) y + HEIGHT - 20 - size, size, lineCutoff);
	}
}
