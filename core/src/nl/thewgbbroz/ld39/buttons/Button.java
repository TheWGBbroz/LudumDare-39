package nl.thewgbbroz.ld39.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

import nl.thewgbbroz.ld39.text.TextRenderer;

public class Button {
	private int x, y;
	private int w, h;
	private String text;
	private int size;
	
	private boolean hovering = false;
	private boolean pressing = false;
	
	public Button(int x, int y, String text, int size) {
		this.x = x;
		this.y = y;
		this.text = text;
		this.size = size;
		
		w = text.length() * size + 8 * 2;
		h = size + 8 * 2;
	}
	
	public void update(int mouseX, int mouseY) {
		hovering = mouseX > x && mouseY > y && mouseX < x + w && mouseY < y + h;
		pressing = hovering && Gdx.input.isButtonPressed(0);
	}
	
	public void render(ShapeRenderer sr, TextRenderer tr, Batch b) {
		sr.begin(ShapeType.Filled);
		
		if(pressing)
			sr.setColor(0.4f, 0.4f, 0.4f, 1f);
		else if(hovering)
			sr.setColor(0.45f, 0.45f, 0.45f, 1f);
		else
			sr.setColor(0.5f, 0.5f, 0.5f, 1f);
		
		sr.rect(x, y, w, h);
		
		sr.end();
		
		tr.renderText(text, b, x + 8, y + 8, size);
		
		sr.begin(ShapeType.Line);
		sr.setColor(0f, 0f, 0f, 1f);
		sr.rect(x, y, w, h);
		sr.end();
	}
	
	public boolean isHovering() {
		return hovering;
	}
	
	public boolean isPressing() {
		return pressing;
	}
}
