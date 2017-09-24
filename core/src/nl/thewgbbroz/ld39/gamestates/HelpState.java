package nl.thewgbbroz.ld39.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

import nl.thewgbbroz.ld39.LD39;

public class HelpState extends GameState {
	protected HelpState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void update(float delta) {
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			gsm.setState(GameStateManager.MENU_STATE);
		}
	}
	
	public void render() {
		textRenderer().renderText("Help", spriteBatch(), 550, LD39.HEIGHT - 100, 48);
		textRenderer().renderText("Press ESC to go back", spriteBatch(), 490, LD39.HEIGHT - 130, 16);
		
		textRenderer().renderText("Goal", spriteBatch(), 270, LD39.HEIGHT - 200, 16);
		textRenderer().renderText(" The goal is to get as many citizens in your city", spriteBatch(), 270, LD39.HEIGHT - 230, 16);
		textRenderer().renderText(" while they're still satisfied", spriteBatch(), 270, LD39.HEIGHT - 250, 16);
		
		textRenderer().renderText("Controls", spriteBatch(), 270, LD39.HEIGHT - 310, 16);
		textRenderer().renderText(" Left mouse button  - Select or place tiles", spriteBatch(), 270, LD39.HEIGHT - 340, 16);
		textRenderer().renderText(" Right mouse button - Break tiles", spriteBatch(), 270, LD39.HEIGHT - 360, 16);
		
		textRenderer().renderText("Game mechanics", spriteBatch(), 270, LD39.HEIGHT - 420, 16);
		textRenderer().renderText(" 1. The power station will only work when it's connected", spriteBatch(), 270, LD39.HEIGHT - 450, 16);
		textRenderer().renderText(" to a road", spriteBatch(), 270, LD39.HEIGHT - 470, 16);
		textRenderer().renderText(" 2. Citizens will only live in houses connected to power", spriteBatch(), 270, LD39.HEIGHT - 490, 16);
		textRenderer().renderText(" and a road", spriteBatch(), 270, LD39.HEIGHT - 510, 16);
		textRenderer().renderText(" 3. For every factory you place, each hotel gets", spriteBatch(), 270, LD39.HEIGHT - 530, 16);
		textRenderer().renderText(" 10 additional residents", spriteBatch(), 270, LD39.HEIGHT - 550, 16);
	}
}
