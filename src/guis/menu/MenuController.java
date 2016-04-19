package guis.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import io.SettingHolder;
import main.Mift;
import renderEngine.DisplayManager;
import terrains.TerrainCreator;

public class MenuController {
	private int selected = 1;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { //pressed
			} else { //released
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					if (selected == 1) {
						//new world
						TerrainCreator.newWorld();
					} else if (selected == 2) {
						//continue
						if (Mift.hasMadeWorld) {
							Mift.setPaused(false);
						} else {
							//show they're retarded
							MenuRenderer.continueError.setRenderable(true);
						}
					} else if (selected == 3) {
						//settings
						MenuRenderer.renderable(false);
//						SettingsRenderer.renderable(true);
						MenuRenderer.clearScreen();
//						MenuRenderer.setMenuScene(1);
					} else if (selected == 4) {
						//quit
						System.exit(0);
					} else {
						//call 0
						selected = 0;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					MenuRenderer.continueError.setRenderable(false);
					if (selected - 1 >= 1) {
						selected--;
					} else {
						selected = 4;
					}
					for (int i = 1; i < MenuRenderer.getTexts().size(); i++) {
						if (i == selected) {
							MenuRenderer.getTexts().get(i).setColor(255, 100, 100);
						} else {
							MenuRenderer.getTexts().get(i).setColor(255, 255, 255);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					MenuRenderer.continueError.setRenderable(false);
					if (selected + 1 <= 4) {
						selected++;
					} else {
						selected = 1;
					}
					for (int i = 1; i < MenuRenderer.getTexts().size(); i++) {
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