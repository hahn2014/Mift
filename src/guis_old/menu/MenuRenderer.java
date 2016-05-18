package guis_old.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis_old.GuiRenderer;
import guis_old.GuiTexture;
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
		texts.add(new GUIText(textRenderer, Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD, 2.5f, FontHolder.getCandara(), new Vector2f(0.33f, 0f), 0.40f, ALIGNMENT.LEFT));
		texts.get(0).setColor(100, 0, 200);
		texts.add(new GUIText(textRenderer, "New Game", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.25f), 0.35f, ALIGNMENT.LEFT));
		texts.get(1).setColor(255, 100, 100);
		texts.add(new GUIText(textRenderer, "Load World", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.35f), 0.35f, ALIGNMENT.LEFT));
		texts.get(2).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Continue Game", 1f, FontHolder.getCandara(), new Vector2f(0.457f, 0.45f), 0.35f, ALIGNMENT.LEFT));
		texts.get(3).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Settings", 1f, FontHolder.getCandara(), new Vector2f(0.475f, 0.55f), 0.35f, ALIGNMENT.LEFT));
		texts.get(4).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Quit", 1f, FontHolder.getCandara(), new Vector2f(0.487f, 0.65f), 0.35f, ALIGNMENT.LEFT));
		texts.get(5).setColor(255, 255, 255);
		
		staticTexts.add(new GUIText(textRenderer, "OGPC v9 Participant", 1f, FontHolder.getCandara(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText(textRenderer, "[C] To View Credits", 1f, FontHolder.getCandara(), new Vector2f(.78f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(160, 0, 200);
		
		continueError = new GUIText(textRenderer, "You need to Load a New World before continuing!", 1f, FontHolder.getCandara(), new Vector2f(0.357f, 0.47f), 0.4f, ALIGNMENT.LEFT);
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