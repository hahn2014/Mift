package guis.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import main.Mift;

public class SettingsRenderer {
	public static List<GUIText> texts = new ArrayList<GUIText>();
	SettingsController controller = new SettingsController();
	private static boolean render = false;
	
	public SettingsRenderer() {
		texts.add(new GUIText(MenuRenderer.settingsRenderer, Mift.NAME + " Alpha Build " + Mift.RELEASE + "." + Mift.BUILD + " Settings", 2.5f, FontHolder.getCandara(), new Vector2f(0.35f, 0f), 0.35f, ALIGNMENT.LEFT));
		texts.get(0).setColor(100, 0, 200);
		texts.add(new GUIText(MenuRenderer.settingsRenderer, "test 1", 1f, FontHolder.getCandara(), new Vector2f(0.47f, 0.35f), 0.35f, ALIGNMENT.LEFT));
		texts.get(1).setColor(255, 255, 255);
		texts.add(new GUIText(MenuRenderer.settingsRenderer, "test 2", 1f, FontHolder.getCandara(), new Vector2f(0.457f, 0.45f), 0.35f, ALIGNMENT.LEFT));
		texts.get(2).setColor(255, 100, 100);
		texts.add(new GUIText(MenuRenderer.settingsRenderer, "test 3", 1f, FontHolder.getCandara(), new Vector2f(0.475f, 0.55f), 0.35f, ALIGNMENT.LEFT));
		texts.get(3).setColor(255, 255, 255);
		texts.add(new GUIText(MenuRenderer.settingsRenderer, "test 4", 1f, FontHolder.getCandara(), new Vector2f(0.487f, 0.65f), 0.35f, ALIGNMENT.LEFT));
		texts.get(4).setColor(255, 255, 255);
	}
	
	public static void renderable(boolean r) {
		render = r;
	}
	
	public void update() {
		if (render) {
			controller.checkInputs();
		}
	}
}