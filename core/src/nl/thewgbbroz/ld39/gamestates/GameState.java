package nl.thewgbbroz.ld39.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import nl.thewgbbroz.ld39.text.TextRenderer;

public abstract class GameState {
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void update(float delta);
	public abstract void render();
	
	public void dispose() {
	}
	
	protected SpriteBatch spriteBatch() {
		return gsm.getSpriteBatch();
	}
	
	protected TextRenderer textRenderer() {
		return gsm.getTextRenderer();
	}
	
	protected ShapeRenderer shapeRenderer() {
		return gsm.getShapeRenderer();
	}
}
