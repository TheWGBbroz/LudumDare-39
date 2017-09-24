package nl.thewgbbroz.ld39.text;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Text {
	private String text;
	private TextureRegion[] textures;
	private int size;
	private int lineCutoff;
	
	protected Text(String text, TextureRegion[] textures, int size, int lineCutoff) {
		this.text = text;
		this.textures = textures;
		this.size = size;
		this.lineCutoff = lineCutoff;
	}
	
	public void render(Batch b, int x, int y) {
		b.begin();
		
		int j = 0;
		for(int i = 0; i < textures.length; i++) {
			int ii;
			if(lineCutoff == -1)
				ii = i;
			else
				ii = i % lineCutoff;
			
			if(i != 0 && ii == 0)
				j--;
			
			TextureRegion tex = textures[i];
			if(tex == null)
				continue;
			
			b.draw(tex, x + ii * size, y + j * size, size, size);
		}
		
		b.end();
	}
	
	public String getText() {
		return text;
	}
	
	public int getSize() {
		return size;
	}
	
	public int getLineCutoff() {
		return lineCutoff;
	}
}
