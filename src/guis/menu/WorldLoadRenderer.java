package guis.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis.GuiRenderer;
import guis.GuiTexture;
import loader.WorldLoader;
import main.Mift;

public class WorldLoadRenderer extends MenuController {
	private static List<GUIText> texts = new ArrayList<GUIText>();
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	private static List<GUIText> worldNames = new ArrayList<GUIText>();
	
	public static WorldLoadController controller = new WorldLoadController();
	public static TextRenderer textRenderer = new TextRenderer();
	
	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	public WorldLoadRenderer() {
		texts.add(new GUIText(Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD + " World Loader", 2.5f, FontHolder.getFreestyle(), new Vector2f(0f, 0f), 1f, ALIGNMENT.CENTER));
		texts.get(0).setColor(100, 0, 200);
		
		staticTexts.add(new GUIText("OGPC v9 Participant", 1f, FontHolder.getFreestyle(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText("[ESC] Return To Main Menu", 1f, FontHolder.getFreestyle(), new Vector2f(.07f, .97f), 0.35f, ALIGNMENT.CENTER));
		staticTexts.get(1).setColor(160, 0, 200);
		staticTexts.add(new GUIText("[S] To Test Save System", 1f, FontHolder.getFreestyle(), new Vector2f(.85f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(2).setColor(160, 0, 200);
		staticTexts.add(new GUIText("[L] To Test Load System", 1f, FontHolder.getFreestyle(), new Vector2f(0f, .97f), 1f, ALIGNMENT.CENTER));
		staticTexts.get(3).setColor(160, 0, 200);
		
		updateWorldList();
		
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/menuBG"), new Vector2f(0f, -1f), new Vector2f(1.20f, 2f)));
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/ogpc-logo"), new Vector2f(-0.9f, -0.82f), new Vector2f(0.08f, 0.1f)));
	}
	
	public static void updateWorldList() {
		File[] worlds;
		
		//update world list
		if (new File(WorldLoader.SAVE_DIRECTORY).exists()) {
			worlds = new File(WorldLoader.SAVE_DIRECTORY).listFiles();
		} else {
			worlds = null;
		}
		
		//see if there are at least one world
		if (worlds != null) {
			for (int i = 0; i < worlds.length; i++) {
				if (worlds[i].getName().endsWith(".world")) {
					worldNames.add(new GUIText(worlds[i].getName().substring(0, worlds[i].getName().length() - 6),
							1f, FontHolder.getFreestyle(), new Vector2f(0.33f, 0.15f + (worldNames.size() * 0.1f)), 0.35f, ALIGNMENT.CENTER));
					if (worldNames.size() == 1) {
						worldNames.get(0).setColor(255, 100, 100);
					} else {
						worldNames.get(worldNames.size() - 1).setColor(255, 255, 255);
					}
				}
			}
			
			controller.max = worldNames.size() - 1;
		}
	}
	
	public static List<GUIText> getTexts() {
		return worldNames;
	}
	
	public void update() {
		guiRender.render(guis);
		controller.checkInputs();
		textRenderer.render(texts);
		textRenderer.render(staticTexts);
		textRenderer.render(worldNames);
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}