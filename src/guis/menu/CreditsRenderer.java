package guis.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis.GuiRenderer;
import guis.GuiTexture;
import main.Mift;

public class CreditsRenderer extends MenuController {
	private static List<GUIText> texts = new ArrayList<GUIText>();
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	public static TextRenderer textRenderer = new TextRenderer();
	
	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	public CreditsRenderer() {
		texts.add(new GUIText(textRenderer, Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD + " Credits", 2.5f, FontHolder.getCandara(), new Vector2f(0.32f, 0f), 0.70f, ALIGNMENT.LEFT));
		texts.get(0).setColor(100, 0, 200);
		
		staticTexts.add(new GUIText(textRenderer, "OGPC v9 Participant", 1f, FontHolder.getCandara(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText(textRenderer, "Thanks For Playing!", 2.5f, FontHolder.getCandara(), new Vector2f(0.4f, 0.1f), 0.5f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(255, 255, 255);
		
		staticTexts.add(new GUIText(textRenderer, "Lead Developer  ~Bryce Hahn", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.25f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(2).setColor(255, 255, 255);
		staticTexts.add(new GUIText(textRenderer, "Co-Developer / Myo Architect  ~Mason Cluff", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.35f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(3).setColor(255, 255, 255);
		staticTexts.add(new GUIText(textRenderer, "Arts and Assets  ~OpenGameArt.org", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.45f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(4).setColor(255, 255, 255);
		staticTexts.add(new GUIText(textRenderer, "External Libraries Used  ~LWJGL  ~Slick Util  ~PNG Decoder", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.55f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(5).setColor(255, 255, 255);
		staticTexts.add(new GUIText(textRenderer, "Mentor  ~Jason Galbraith", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.75f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(6).setColor(255, 255, 255);
		staticTexts.add(new GUIText(textRenderer, "Special Thank You To All Our Alpha Testers!", 1f, FontHolder.getCandara(), new Vector2f(0.40f, 0.85f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(7).setColor(255, 100, 255);
		
		
		staticTexts.add(new GUIText(textRenderer, "[ESC] Return To Main Menu", 1f, FontHolder.getCandara(), new Vector2f(.13f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(8).setColor(160, 0, 200);
		
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
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				//return to main menu
				Mift.menuIndex = 0;
			}
		}
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}