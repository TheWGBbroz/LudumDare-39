package nl.thewgbbroz.ld39.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class TextureLoader {
	private TextureLoader() {
	}
	
	private static Map<String, Texture> loadedTextures = new HashMap<>();
	
	public static Texture getTexture(String name) {
		if(loadedTextures.containsKey(name))
			return loadedTextures.get(name);
		
		Texture tex = new Texture(name + ".png");
		loadedTextures.put(name, tex);
		
		System.out.println("Loaded " + name + ".png");
		
		return tex;
	}
	
	public static void dispose() {
		for(String n : loadedTextures.keySet()) {
			loadedTextures.get(n).dispose();
		}
	}
}
