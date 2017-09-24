package nl.thewgbbroz.ld39.gamestates;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.text.TextRenderer;

public class GameStateManager {
	public static final int PLAY_STATE = 1;
	public static final int GAME_OVER_STATE = 2;
	public static final int MENU_STATE = 3;
	public static final int HELP_STATE = 4;
	
	private GameState state;
	private LD39 main;
	
	protected Map<String, Object> crossStateValues = new HashMap<>();
	
	public GameStateManager(LD39 main) {
		this.main = main;
		
		setState(MENU_STATE);
	}
	
	public void setState(int id) {
		if(state != null)
			state.dispose();
		
		switch(id) {
		case PLAY_STATE:
			state = new PlayState(this);
			break;
		case GAME_OVER_STATE:
			state = new GameOverState(this);
			break;
		case MENU_STATE:
			state = new MenuState(this);
			break;
		case HELP_STATE:
			state = new HelpState(this);
			break;
		}
	}
	
	public void update(float delta) {
		if(state != null)
			state.update(delta);
	}
	
	public void render() {
		if(state != null)
			state.render();
	}
	
	public void dispose() {
		if(state != null)
			state.dispose();
	}
	
	public int getMouseX() {
		return Gdx.input.getX();
	}
	
	public int getMouseY() {
		return LD39.HEIGHT - Gdx.input.getY();
	}
	
	public SpriteBatch getSpriteBatch() {
		return main.getSpriteBatch();
	}
	
	public TextRenderer getTextRenderer() {
		return main.getTextRenderer();
	}
	
	public ShapeRenderer getShapeRenderer() {
		return main.getShapeRenderer();
	}
}
