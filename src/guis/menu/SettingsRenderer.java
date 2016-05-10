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
import io.SettingHolder;
import main.Mift;

public class SettingsRenderer {
	private static List<GUIText> texts = new ArrayList<GUIText>();
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	public static SettingsController controller = new SettingsController();
	private static TextRenderer textRenderer = new TextRenderer();

	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	public SettingsRenderer() {
		texts.add(new GUIText(textRenderer, Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD + " Settings", 2.5f, FontHolder.getCandara(), new Vector2f(0.25f, 0f), 0.55f, ALIGNMENT.LEFT));
		texts.get(0).setColor(100, 0, 200);
		
		texts.add(new GUIText(textRenderer, "Fullscreen [" + SettingHolder.get("cg_fullscreened").getValueDebug()+ "]",
				1f, FontHolder.getCandara(), new Vector2f(0.27f, 0.15f), 0.35f, ALIGNMENT.LEFT));
				texts.get(1).setColor(255, 100, 100);
		texts.add(new GUIText(textRenderer, "Anisotropic Filtering [" + SettingHolder.get("cg_anisotropic_filtering").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.27f, 0.25f), 0.35f, ALIGNMENT.LEFT));
				texts.get(2).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Antialiasing Filtering [" + SettingHolder.get("cg_antialiasing_filtering").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.27f, 0.35f), 0.35f, ALIGNMENT.LEFT));
				texts.get(3).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Draw FPS [" + SettingHolder.get("cg_fps").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.27f, 0.45f), 0.35f, ALIGNMENT.LEFT));
				texts.get(4).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Theater Mode [" + SettingHolder.get("cg_theatrical").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.27f, 0.55f), 0.35f, ALIGNMENT.LEFT));
				texts.get(5).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Post Processing Effects [" + SettingHolder.get("cg_post_processing").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.5f, 0.15f), 0.35f, ALIGNMENT.LEFT));
				texts.get(6).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Myo Armband Connection [" + SettingHolder.get("cp_myo_enabled").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.5f, 0.25f), 0.35f, ALIGNMENT.LEFT));
				texts.get(7).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "Quality [" + SettingHolder.get("cg_quality").getQualityDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.5f, 0.35f), 0.35f, ALIGNMENT.LEFT));
				texts.get(8).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "VSync [" + SettingHolder.get("cg_vsync").getValueDebug() + "]",
				1f, FontHolder.getCandara(), new Vector2f(0.5f, 0.45f), 0.35f, ALIGNMENT.LEFT));
				texts.get(9).setColor(255, 255, 255);
		
		staticTexts.add(new GUIText(textRenderer, "OGPC v9 Participant", 1f, FontHolder.getCandara(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(255, 170, 67);
		staticTexts.add(new GUIText(textRenderer, "[F] Return to Main Menu And Save", 1f, FontHolder.getCandara(), new Vector2f(.78f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(160, 0, 200);
		staticTexts.add(new GUIText(textRenderer, "[ESC] Return To Main Menu Without Saving", 1f, FontHolder.getCandara(), new Vector2f(.13f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(2).setColor(160, 0, 200);

		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/menuBG"), new Vector2f(0f, -1f), new Vector2f(1.20f, 2f)));
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/ogpc-logo"), new Vector2f(-0.9f, -0.82f), new Vector2f(0.08f, 0.1f)));
	}
	
	public void update() {
		guiRender.render(guis);
		controller.checkInputs();
		textRenderer.render(texts);
		textRenderer.render(staticTexts);
	}
	
	public static List<GUIText> getTexts() {
		return texts;
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}