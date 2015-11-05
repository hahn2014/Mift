package fontRender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fontCreator.FontType;
import fontCreator.GUIText;
import fontCreator.TextData;
import renderEngine.Loader;

public class Text {

	public static Loader loader;
	public static Map<FontType, List<GUIText>> texts = new HashMap<FontType, List<GUIText>>();
	public static FontRenderer renderer;
	
	public static void init(Loader ldr) {
		renderer = new FontRenderer();
		loader = ldr;
	}
	
	/**
	 * Simple method of which calls
	 * the render method in the 
	 * renderer class. 
	 */
	public static void render() {
		renderer.render(texts);
	}
	
	/**
	 * Create the new GUIText object identifier
	 * into the rendering queue. This method
	 * takes in one GUIText object with all 
	 * required parameters then adds it to 
	 * list of text objects.
	 * @param text The GUIText object you wish to add to the queue
	 */
	public static void loadText(GUIText text) {
		FontType font = text.getFont();
		TextData data = font.loadText(text);
		int vao = loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		text.setMeshInfo(vao, data.getVertexCount());
		List<GUIText> textBatch = texts.get(font);
		if (textBatch == null) {
			textBatch = new ArrayList<GUIText>();
			texts.put(font, textBatch);
		}
		textBatch.add(text);
	}
	
	/**
	 * Remove a GUIText object from the rendering process
	 * Permanent, but you can always call a recreate
	 * event to create the object again manually.
	 * @param text The GUIText object you wish to have deleted
	 */
	public static void removeText(GUIText text) {
		//get a list of all the available texts we have
		List<GUIText> textBatch = texts.get(text.getFont());
		//remove the text object we wish to have deleted
		textBatch.remove(text);
		//if its empty we dont need the list anymore
		if (textBatch.isEmpty()) {
			texts.remove(text.getFont());
		}
	}
	
	public static void cleanUp() {
		renderer.cleanUp();
	}
}