package nl.thewgbbroz.ld39.utils;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundLoader {
	private SoundLoader() {
	}
	
	private static Map<String, Sound> loadedAudio = new HashMap<>();
	
	public static Sound getSound(String name) {
		if(loadedAudio.containsKey(name))
			return loadedAudio.get(name);
		
		Sound m = Gdx.audio.newSound(Gdx.files.internal(name + ".wav"));
		loadedAudio.put(name, m);
		return m;
	}
	
	public static void dispose() {
		for(String n : loadedAudio.keySet())
			loadedAudio.get(n).dispose();
	}
}
