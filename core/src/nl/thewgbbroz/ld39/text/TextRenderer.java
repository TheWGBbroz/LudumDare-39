package nl.thewgbbroz.ld39.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import nl.thewgbbroz.ld39.utils.TextureLoader;

public class TextRenderer {
	private Texture alphabetTexture;
	private Map<Character, TextureRegion> charTextures = new HashMap<>();
	
	private List<Text> loadedTexts = new ArrayList<>();
	
	public TextRenderer() {
		alphabetTexture = TextureLoader.getTexture("alphabet");
		
		int i = 0;
		for(char c = 'a'; c <= 'z'; c++) {
			TextureRegion tr = new TextureRegion(alphabetTexture, i * 32, 0, 32, 32);
			charTextures.put(c, tr);
			
			i++;
		}
		
		charTextures.put('.', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put(',', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('!', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('?', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		
		//i = 30;
		for(char c = '0'; c <= '9'; c++) {
			TextureRegion tr = new TextureRegion(alphabetTexture, i * 32, 0, 32, 32);
			charTextures.put(c, tr);
			
			i++;
		}
		
		charTextures.put(':', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('(', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put(')', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('$', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('-', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		charTextures.put('\'', new TextureRegion(alphabetTexture, i++ * 32, 0, 32, 32));
		
	}
	
	public void renderText(String s, Batch b, int x, int y, int size, int lineCutoff) {
		s = s.toLowerCase();
		
		Text t = getText(s, size, lineCutoff);
		if(t == null) {
			System.out.println("Text not found in cache, generating one.");
			t = generateText(s, size, lineCutoff);
			loadedTexts.add(t);
			
			if(loadedTexts.size() > 50) {
				System.out.println("More than 50 texts loaded. Removing oldest one.");
				loadedTexts.remove(0);
			}
		}
		
		t.render(b, x, y);
	}
	
	public void renderText(String s, Batch b, int x, int y, int size) {
		renderText(s, b, x, y, size, -1);
	}
	
	public void renderTextWithBG(String s, Batch b, ShapeRenderer sr, int x, int y, int size, int lineCutoff) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
		sr.begin(ShapeType.Filled);
		sr.setColor(0f, 0f, 0f, 0.2f);
		sr.rect(x - 3, y - 3, s.length() * size + 2 * 3, size + 2 * 3);
		sr.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
		
		renderText(s, b, x, y, size, lineCutoff);
	}
	
	public void renderTextWithBG(String s, Batch b, ShapeRenderer sr, int x, int y, int size) {
		renderTextWithBG(s, b, sr, x, y, size, -1);
	}
	
	private Text generateText(String s, int size, int lineCutoff) {
		TextureRegion[] textures = new TextureRegion[s.length()];
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			
			TextureRegion tr = charTextures.get(c);
			
			textures[i] = tr;
		}
		
		return new Text(s, textures, size, lineCutoff);
	}
	
	public void dispose() {
		alphabetTexture.dispose();
	}
	
	private Text getText(String s, int size, int lineCutoff) {
		for(Text t : loadedTexts) {
			if(t.getSize() == size && t.getLineCutoff() == lineCutoff && t.getText().equals(s)) {
				return t;
			}
		}
		
		return null;
	}
}
