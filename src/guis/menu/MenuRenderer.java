package guis.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis.GuiRenderer;
import guis.GuiTexture;
import main.Mift;

public class MenuRenderer extends MenuController {
	private static List<GUIText> texts = new ArrayList<GUIText>();
	public static GUIText continueError;
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	public static MenuController controller = new MenuController();
	public static TextRenderer textRenderer = new TextRenderer();
	
	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	public MenuRenderer() {
		texts.add(new GUIText(Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD, 2.5f, FontHolder.getBerlinSans(), new Vector2f(0f, 0f), 1f, ALIGNMENT.CENTER));
		texts.get(0).setColor(100, 0, 200);
		texts.add(new GUIText("New Game", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.25f), 1f, ALIGNMENT.CENTER));
		texts.get(1).setColor(255, 100, 100);
		texts.add(new GUIText("Load World", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.35f), 1f, ALIGNMENT.CENTER));
		texts.get(2).setColor(255, 255, 255);
		texts.add(new GUIText("Continue Game", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.45f), 1f, ALIGNMENT.CENTER));
		texts.get(3).setColor(255, 255, 255);
		texts.add(new GUIText("Settings", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.55f), 1f, ALIGNMENT.CENTER));
		texts.get(4).setColor(255, 255, 255);
		texts.add(new GUIText("Quit", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.65f), 1f, ALIGNMENT.CENTER));
		texts.get(5).setColor(255, 255, 255);
		
		staticTexts.add(new GUIText("OGPC v9 Participant", 1f, FontHolder.getFreestyle(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText("[C] To View Credits", 1f, FontHolder.getFreestyle(), new Vector2f(.875f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(160, 0, 200);
		
		continueError = new GUIText("You need to Load a New World before continuing!", 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.47f), 1f, ALIGNMENT.CENTER);
		continueError.setColor(255, 50, 50);
		continueError.setRenderable(false);
		
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/menuBG"), new Vector2f(0f, -1f), new Vector2f(1.20f, 2f)));
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/ogpc-logo"), new Vector2f(-0.9f, -0.82f), new Vector2f(0.08f, 0.1f)));
	}
	
	public static List<GUIText> getTexts() {
		return texts;
	}
	
	public void update() {
		guiRender.render(guis);
		controller.checkInputs();
		textRenderer.render(texts);
		textRenderer.render(continueError);
		textRenderer.render(staticTexts);
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}