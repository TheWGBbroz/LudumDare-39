package nl.thewgbbroz.ld39.gamestates;

import nl.thewgbbroz.ld39.LD39;
import nl.thewgbbroz.ld39.buttons.Button;
import nl.thewgbbroz.ld39.utils.SoundLoader;

public class MenuState extends GameState {
	private Button playBtn, helpBtn;
	
	protected MenuState(GameStateManager gsm) {
		super(gsm);
		
		playBtn = new Button(LD39.WIDTH / 2 - ("Play".length() * 32 + 8 * 2) / 2, LD39.HEIGHT - 260, "Play", 32);
		helpBtn = new Button(LD39.WIDTH / 2 - ("Help".length() * 32 + 8 * 2) / 2, LD39.HEIGHT - 330, "Help", 32);
	}

	public void update(float delta) {
		playBtn.update(gsm.getMouseX(), gsm.getMouseY());
		helpBtn.update(gsm.getMouseX(), gsm.getMouseY());
		
		if(playBtn.isPressing()) {
			SoundLoader.getSound("click_deep").play();
			gsm.setState(GameStateManager.PLAY_STATE);
		}
			
		if(helpBtn.isPressing()) {
			SoundLoader.getSound("click_deep").play();
			gsm.setState(GameStateManager.HELP_STATE);
		}
	}

	public void render() {
		playBtn.render(shapeRenderer(), textRenderer(), spriteBatch());
		helpBtn.render(shapeRenderer(), textRenderer(), spriteBatch());
		
		textRenderer().renderText("Power city", spriteBatch(), 415, LD39.HEIGHT - 100, 48);
		textRenderer().renderText("Made for Ludum Dare 39 in 48 hours", spriteBatch(), 380, LD39.HEIGHT - 140, 16);
		
		textRenderer().renderText("Made by TheWGBbroz", spriteBatch(), 50, 50, 16);
	}
}
