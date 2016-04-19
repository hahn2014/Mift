package guis.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import io.SettingHolder;
import renderEngine.DisplayManager;

public class SettingsController {
	private int selected = 2;
	private final int maxSelect = 4;
	private final int minSelect = 1;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { //pressed
			} else { //released
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					if (selected == 1) {
						System.out.println("option 1");
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					SettingsRenderer.renderable(false);
					MenuRenderer.renderable(true);
					MenuRenderer.clearScreen();
					MenuRenderer.setMenuScene(0);
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					MenuRenderer.continueError.setRenderable(false);
					if (selected - 1 >= minSelect) {
						selected--;
					} else {
						selected = maxSelect;
					}
					for (int i = minSelect; i < MenuRenderer.getTexts().size(); i++) {
						if (i == selected) {
							MenuRenderer.getTexts().get(i).setColor(255, 100, 100);
						} else {
							MenuRenderer.getTexts().get(i).setColor(255, 255, 255);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					MenuRenderer.continueError.setRenderable(false);
					if (selected + 1 <= maxSelect) {
						selected++;
					} else {
						selected = minSelect;
					}
					for (int i = minSelect; i < MenuRenderer.getTexts().size(); i++) {
						if (i == selected) {
							MenuRenderer.getTexts().get(i).setColor(255, 100, 100);
						} else {
							MenuRenderer.getTexts().get(i).setColor(255, 255, 255);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					DisplayManager.setFullscreened(!SettingHolder.get("cg_fullscreened").getValueB());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
					Mouse.setGrabbed(!Mouse.isGrabbed());
				}
			}
		}
	}
}