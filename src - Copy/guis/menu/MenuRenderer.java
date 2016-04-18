package guis.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import main.Mift;

public class MenuRenderer extends MenuController {
	private static List<GUIText> mainMenuTexts = new ArrayList<GUIText>();
	public static GUIText continueError;
	private MenuController controller = new MenuController();
	public static TextRenderer textRenderer = new TextRenderer();
	public static TextRenderer settingsRenderer = new TextRenderer();
	private SettingsRenderer settings = new SettingsRenderer();
	
	private static int gamescene = 0;
	private static boolean render = true;
	
	public MenuRenderer() {
		mainMenuTexts.add(new GUIText(textRenderer, Mift.NAME + " Alpha Build " + Mift.RELEASE + "." + Mift.BUILD, 2.5f, FontHolder.getCandara(), new Vector2f(0.35f, 0f), 0.35f, ALIGNMENT.LEFT));
		mainMenuTexts.get(0).setColor(100, 0, 200);
		mainMenuTexts.add(new GUIText(textRenderer, "New Game", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.35f), 0.35f, ALIGNMENT.LEFT));
		mainMenuTexts.get(1).setColor(255, 100, 100);
		mainMenuTexts.add(new GUIText(textRenderer, "Continue Game", 1f, FontHolder.getCandara(), new Vector2f(0.457f, 0.45f), 0.35f, ALIGNMENT.LEFT));
		mainMenuTexts.get(2).setColor(255, 255, 255);
		mainMenuTexts.add(new GUIText(textRenderer, "Settings", 1f, FontHolder.getCandara(), new Vector2f(0.475f, 0.55f), 0.35f, ALIGNMENT.LEFT));
		mainMenuTexts.get(3).setColor(255, 255, 255);
		mainMenuTexts.add(new GUIText(textRenderer, "Quit", 1f, FontHolder.getCandara(), new Vector2f(0.487f, 0.65f), 0.35f, ALIGNMENT.LEFT));
		mainMenuTexts.get(4).setColor(255, 255, 255);
		
		continueError = new GUIText(textRenderer, "You need to Load a New World before continuing!", 1f, FontHolder.getCandara(), new Vector2f(0.357f, 0.47f), 0.4f, ALIGNMENT.LEFT);
		continueError.setColor(255, 50, 50);
		continueError.setRenderable(false);
	}
	
	public static List<GUIText> getTexts() {
		return mainMenuTexts;
	}
	
	public void update() {
		if (gamescene == 0) { //main menu
			if (render) {
				controller.checkInputs();
				textRenderer.render(mainMenuTexts);
				textRenderer.render(continueError);
			}
		} else if (gamescene == 1) { // settings
			settings.update();
			settingsRenderer.render(SettingsRenderer.texts);
		}
	}
	
	public static void clearScreen() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL11.glClearColor(0, 0, 0, 1);
		textRenderer = new TextRenderer();
	}
	
	public static void renderable(boolean r) {
		render = r;
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
	
	public static void setUp() {
		textRenderer = new TextRenderer();
	}
	
	public static void setMenuScene(int menu) {
		gamescene = menu;
	}
}