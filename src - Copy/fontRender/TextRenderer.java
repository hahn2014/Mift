package fontRender;

import java.util.List;

import fontCreator.GUIText;

public class TextRenderer {

	public static FontRenderer renderer;
	
	public TextRenderer() {
		renderer = new FontRenderer();
	}
	
	/**
	 * Simple method of which calls
	 * the render method in the 
	 * renderer class. 
	 */
	public void render(List<GUIText> texts) {
		for (GUIText text : texts) {
			renderer.render(text.getFont(), text);
		}
	}
	
	public void render(GUIText text) {
		renderer.render(text.getFont(), text);
	}
	
	public void cleanUp() {
		renderer.cleanUp();
	}
}