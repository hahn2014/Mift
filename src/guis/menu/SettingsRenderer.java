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
	private static List<GUIText> defaultTexts = new ArrayList<GUIText>();
	private static List<GUIText> postProcTexts = new ArrayList<GUIText>();
	private static List<GUIText> devTexts = new ArrayList<GUIText>();
	
	private static List<GUIText> staticTexts = new ArrayList<GUIText>();
	
	public static SettingsController controller = new SettingsController();
	private static TextRenderer textRenderer = new TextRenderer();

	private GuiRenderer guiRender = new GuiRenderer();
	private List<GuiTexture> guis = new ArrayList<GuiTexture>();
	
	private static final float COLUMN1 = 0.17f; //default settings
	private static final float COLUMN2 = 0.4f;  //post processing settings
	private static final float COLUMN3 = 0.63f; //developer settings
	
	private static final float ROW1 = 0.15f;    //first setting space
	private static final float ROW_SPACE = 0.05f;//gap size between settings
	
	public SettingsRenderer() {
		staticTexts.add(new GUIText(Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD + " Settings", 2.5f, FontHolder.getFreestyle(), new Vector2f(0.25f, 0f), 0.55f, ALIGNMENT.LEFT));
		staticTexts.get(0).setColor(100, 0, 200);
		
		/********************
		 * DEFAULT SETTINGS *
		 * DEFAULT SETTINGS *
		 * DEFAULT SETTINGS *
		 * DEFAULT SETTINGS *
		 ********************/
		defaultTexts.add(new GUIText(SettingHolder.get("cg_fullscreened").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(0).setColor(255, 25, 25);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_anisotropic_filtering").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 1)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(1).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_antialiasing_filtering").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 2)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(2).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_fps").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 3)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(3).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_theatrical").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 4)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(4).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cp_myo_enabled").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 5)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(5).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_quality").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 6)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(6).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_resolution_w").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 7)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(7).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_resolution_h").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 8)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(8).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_vsync").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 9)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(9).setColor(255, 255, 255);
		defaultTexts.add(new GUIText(SettingHolder.get("cg_fov").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 + (ROW_SPACE * 10)), 0.35f, ALIGNMENT.LEFT));
				defaultTexts.get(10).setColor(255, 255, 255);
		/*******************
		 * POST PROCESSING *
		 * POST PROCESSING *
		 * POST PROCESSING *
		 * POST PROCESSING *
		 *******************/
		postProcTexts.add(new GUIText(SettingHolder.get("cg_gaussian_blur").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN2, ROW1), 0.35f, ALIGNMENT.LEFT));
				postProcTexts.get(0).setColor(255, 150, 150);
		postProcTexts.add(new GUIText(SettingHolder.get("cg_contrast_adjust").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN2, ROW1 + (ROW_SPACE * 1)), 0.35f, ALIGNMENT.LEFT));
				postProcTexts.get(1).setColor(255, 255, 255);
		postProcTexts.add(new GUIText(SettingHolder.get("cg_bloom").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN2, ROW1 + (ROW_SPACE * 2)), 0.35f, ALIGNMENT.LEFT));
				postProcTexts.get(2).setColor(255, 255, 255);
		postProcTexts.add(new GUIText(SettingHolder.get("cg_depth_of_field").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN2, ROW1 + (ROW_SPACE * 3)), 0.35f, ALIGNMENT.LEFT));
				postProcTexts.get(3).setColor(255, 255, 255);
		/**********************
		 * DEVELOPER SETTINGS *
		 * DEVELOPER SETTINGS *
		 * DEVELOPER SETTINGS *
		 * DEVELOPER SETTINGS *
		 **********************/
		devTexts.add(new GUIText(SettingHolder.get("cg_developer").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(0).setColor(255, 150, 150);
		devTexts.add(new GUIText(SettingHolder.get("cg_animate_day").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 1)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(1).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("cg_debug_polygons").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 2)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(2).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("player_sprint_unlimited").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 3)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(3).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("player_ufo").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 4)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(4).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("player_god").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 5)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(5).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("player_fire_unlimited").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 6)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(6).setColor(255, 255, 255);
		devTexts.add(new GUIText(SettingHolder.get("player_undetected").getDebugNameAndValue(),
				1f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 + (ROW_SPACE * 7)), 0.35f, ALIGNMENT.LEFT));
				devTexts.get(7).setColor(255, 255, 255);
		/***************
		 * STATIC TEXT *
		 * STATIC TEXT *
		 * STATIC TEXT *
		 * STATIC TEXT *
		 ***************/
		staticTexts.add(new GUIText("OGPC v9 Participant", 1f, FontHolder.getFreestyle(), new Vector2f(0f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(1).setColor(255, 170, 67);
		staticTexts.add(new GUIText("[F] Return to Main Menu And Save", 1f, FontHolder.getFreestyle(), new Vector2f(.78f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(2).setColor(160, 0, 200);
		staticTexts.add(new GUIText("[ESC] Return To Main Menu Without Saving", 1f, FontHolder.getFreestyle(), new Vector2f(.15f, .97f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(3).setColor(160, 0, 200);
		staticTexts.add(new GUIText(SettingHolder.getDefinition(0, 0), 1f, FontHolder.getFreestyle(), new Vector2f(0f, 0.94f), 1f, ALIGNMENT.CENTER));
		staticTexts.get(4).setColor(255, 180, 200);
		staticTexts.add(new GUIText("Default Settings", 1.2f, FontHolder.getFreestyle(), new Vector2f(COLUMN1, ROW1 - 0.05f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(5).setColor(200, 200, 255);
		staticTexts.add(new GUIText("Post-Processing Settings", 1.2f, FontHolder.getFreestyle(), new Vector2f(COLUMN2, ROW1 - 0.05f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(6).setColor(200, 255, 200);
		staticTexts.add(new GUIText("Developer Settings Effects", 1.2f, FontHolder.getFreestyle(), new Vector2f(COLUMN3, ROW1 - 0.05f), 0.35f, ALIGNMENT.LEFT));
		staticTexts.get(7).setColor(255, 200, 200);
		staticTexts.add(new GUIText("You Must Be A Developer To Edit Developer Settings", 1.2f, FontHolder.getFreestyle(), new Vector2f(COLUMN3 + .02f, ROW1 + (ROW_SPACE * 3)), 0.15f, ALIGNMENT.CENTER));
		staticTexts.get(8).setColor(255, 70, 70);
		staticTexts.add(new GUIText("* - Mift Setting Requires Full Game Restart Post Save In Order For Changes To Take Place", 0.7f, FontHolder.getFreestyle(), new Vector2f(0f, 0f), 0.15f, ALIGNMENT.LEFT));
		staticTexts.get(9).setColor(70, 70, 70);
		
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/menuBG"), new Vector2f(0f, -1f), new Vector2f(1.20f, 2f)));
		guis.add(new GuiTexture(Mift.loader.loadTexture("menuguis/ogpc-logo"), new Vector2f(-0.9f, -0.82f), new Vector2f(0.08f, 0.1f)));
	}
	
	public void update() {
		guiRender.render(guis);
		controller.checkInputs();
		textRenderer.render(defaultTexts);
		textRenderer.render(postProcTexts);
		if (SettingHolder.get("cg_developer").getValueB()) {
			textRenderer.render(devTexts);
			staticTexts.get(8).setText("");
		} else {
			staticTexts.get(8).setText("You Must Be A Developer To Edit Developer Settings");
		}
		textRenderer.render(staticTexts);
	}
	
	public static List<GUIText> getDefaultTexts() {
		return defaultTexts;
	}
	
	public static List<GUIText> getPostProcessingTexts() {
		return postProcTexts;
	}
	
	public static List<GUIText> getDeveloperTexts() {
		return devTexts;
	}
	
	public static List<GUIText> getStaticTexts() {
		return staticTexts;
	}
	
	public static void cleanUp() {
		textRenderer.cleanUp();
	}
}