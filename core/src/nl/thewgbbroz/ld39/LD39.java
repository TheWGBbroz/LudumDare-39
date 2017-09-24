package nl.thewgbbroz.ld39;

import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import nl.thewgbbroz.ld39.gamestates.GameStateManager;
import nl.thewgbbroz.ld39.text.TextRenderer;
import nl.thewgbbroz.ld39.utils.SoundLoader;
import nl.thewgbbroz.ld39.utils.TextureLoader;

public class LD39 extends ApplicationAdapter {
	public static final Random RAND = new Random();
	
	public static int WIDTH, HEIGHT;
	
	private SpriteBatch spriteBatch;
	private TextRenderer textRenderer;
	private ShapeRenderer shapeRenderer;
	
	private GameStateManager gsm;
	
	@Override
	public void create() {
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		spriteBatch = new SpriteBatch();
		textRenderer = new TextRenderer();
		shapeRenderer = new ShapeRenderer();
		
		gsm = new GameStateManager(this);
	}

	@Override
	public void render() {
		gsm.update(Gdx.graphics.getDeltaTime());
		
		Gdx.gl.glClearColor(0.71f, 0.5f, 0.41f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.render();
	}
	
	@Override
	public void dispose() {
		spriteBatch.dispose();
		textRenderer.dispose();
		shapeRenderer.dispose();
		gsm.dispose();
		TextureLoader.dispose();
		SoundLoader.dispose();
	}
	
	public SpriteBatch getSpriteBatch() {
		return spriteBatch;
	}
	
	public TextRenderer getTextRenderer() {
		return textRenderer;
	}
	
	public ShapeRenderer getShapeRenderer() {
		return shapeRenderer;
	}
}
