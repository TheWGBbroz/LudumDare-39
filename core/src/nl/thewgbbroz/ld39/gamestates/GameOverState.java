package nl.thewgbbroz.ld39.gamestates;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import nl.thewgbbroz.ld39.LD39;

public class GameOverState extends GameState {
	private int population = 0;
	private int highScore = -1;
	
	private float timer;
	
	protected GameOverState(GameStateManager gsm) {
		super(gsm);
		
		if(gsm.crossStateValues.containsKey("population"))
			population = (int) gsm.crossStateValues.get("population");
		
		File hsFile = new File("highscore.txt");
		if(hsFile.exists()) {
			try{
				BufferedReader br = new BufferedReader(new FileReader(hsFile));
				highScore = Integer.parseInt(br.readLine());
				br.close();
			}catch(Exception e) {}
		}
		
		if(population >= highScore) {
			highScore = population;
			
			try{
				BufferedWriter bw = new BufferedWriter(new FileWriter(hsFile));
				bw.write(String.valueOf(highScore));
				bw.close();
			}catch(Exception e) {}
		}
	}

	public void update(float delta) {
		timer += delta;
		if(timer > 1)
			timer = 0;
		
		if(Gdx.input.isKeyJustPressed(Keys.SPACE)) {
			gsm.setState(GameStateManager.PLAY_STATE);
		}
	}

	public void render() {
		shapeRenderer().begin(ShapeType.Filled);
		shapeRenderer().setColor(0.7f, 0.3f, 0.3f, 1f);
		shapeRenderer().rect(0, 0, LD39.WIDTH, LD39.HEIGHT);
		shapeRenderer().end();
		
		textRenderer().renderText("You are broke.", spriteBatch(), 330, LD39.HEIGHT - 150, 48);
		textRenderer().renderText("Better luck next time!", spriteBatch(), 310, LD39.HEIGHT - 230, 32);
		
		textRenderer().renderText("Final population: " + population, spriteBatch(), 500, LD39.HEIGHT - 380, 16);
		textRenderer().renderText("High score: " + highScore, spriteBatch(), 500, LD39.HEIGHT - 400, 16);
		
		if(timer < 0.5f) {
			textRenderer().renderText("Press space to play again", spriteBatch(), 250, 100, 32);
		}
	}

}
