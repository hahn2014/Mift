package guis_old.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import loader.WorldLoader;
import loader.WorldSaver;
import main.Mift;

public class WorldLoadController {
	public static int selected = 0;
	private int min = 1;
	private int max = 5;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (!Keyboard.getEventKeyState()) {
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					//load world
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
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
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					Mift.menuIndex = 0;
					MenuRenderer.selected = 2;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_S) {
					WorldSaver.saveCurrentWorld();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_L) {
					WorldLoader.loadWorld("249975");
				}
			}
		}
	}
}