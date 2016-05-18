package guis_old.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import main.Mift;
import terrains.TerrainCreator;

public class MenuController {
	public static int selected = 1;
	private int min = 1;
	private int max = 5;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { //pressed
			} else { //released
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					if (selected == 1) { //new world
						TerrainCreator.newWorld();
					} else if (selected == 2) { //load world
						Mift.menuIndex = 2;
					} else if (selected == 3) { //continue world
						if (Mift.hasMadeWorld) { //they can continue loaded world
							Mift.setPaused(false);
						} else { //show they're retarded
							MenuRenderer.continueError.setRenderable(true);
						}
					} else if (selected == 4) { //settings
						Mift.menuIndex = 1;
						SettingsController.changed = false;
					} else if (selected == 5) { //quit
						System.exit(0);
					} else { //call 0
						selected = 0;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					MenuRenderer.continueError.setRenderable(false);
					if (selected - 1 >= min) {
						selected--;
					} else {
						selected = max;
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
					if (selected + 1 <= max) {
						selected++;
					} else {
						selected = min;
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
					Mouse.setGrabbed(!Mouse.isGrabbed());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_C) {
					Mift.menuIndex = 4;
				}
			}
		}
	}
}