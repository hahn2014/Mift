package guis.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis.GuiRenderer;
import guis.GuiTexture;
import main.Mift;

public class DeadRenderer extends MenuController {
	private static List<GUIText> texts = new ArrayList<GUIText>();
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	public static WorldLoadController controller = new WorldLoadController();
	public static TextRenderer textRenderer = new TextRenderer();
	
	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	public DeadRenderer() {
		texts.add(new GUIText(textRenderer, Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD, 2.5f, FontHolder.getCandara(), new Vector2f(0.20f, 0f), 0.70f, ALIGNMENT.LEFT));
		texts.get(0).setColor(100, 0, 200);
		
		staticTexts.add(new GUIText(textRenderer, "OGPC v9 Participant", 1f, FontHolder.getCandara(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText(textRenderer, "YOU DIED!", 2.5f, FontHolder.getCandara(), new Vector2f(0.4f, 0.4f), 0.5f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(255, 50, 50);
		staticTexts.add(new GUIText(textRenderer, "[ESC] Return To Main Menu", 1f, FontHolder.getCandara(), new Vector2f(.13f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(2).setColor(160, 0, 200);
		
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/menuBG"), new Vector2f(0f, -1f), new Vector2f(1.20f, 2f)));
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/ogpc-logo"), new Vector2f(-0.9f, -0.82f), new Vector2f(0.08f, 0.1f)));
	}
	
	public static List<GUIText> getTexts() {
		return texts;
	}
	
	public void update() {
		guiRender.render(guis);
		checkInputs();
		textRenderer.render(texts);
		textRenderer.render(staticTexts);
	}
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (!Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					Mouse.setGrabbed(!Mouse.isGrabbed());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					Mift.menuIndex = 0;
					MenuRenderer.selected = 1;
				}
			}
		}
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}