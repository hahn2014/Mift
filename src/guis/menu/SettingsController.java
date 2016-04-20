package guis.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import io.Logger;
import io.SettingHolder;
import io.Settings;
import main.Mift;
import myo.MyoSetup;
import renderEngine.DisplayManager;

public class SettingsController {
	private int selected = 1;
	private int min = 1;
	private int max = 8;
	
	public static boolean changed = false;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { //pressed
			} else { //released
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					changed = true;
					if (selected == 1) {//fullscreen
						DisplayManager.setFullscreened(!SettingHolder.get("cg_fullscreened").getValueB());
						SettingsRenderer.getTexts().get(1).setText("Fullscreen [" + SettingHolder.get("cg_fullscreened").getValueDebug()+ "]");
					} else if (selected == 2) {//anisotropic filtering
						SettingHolder.get("cg_anisotropic_filtering").setValueB(!SettingHolder.get("cg_anisotropic_filtering").getValueB());
						SettingsRenderer.getTexts().get(2).setText("Anisotropic Filtering [" + SettingHolder.get("cg_anisotropic_filtering").getValueDebug()+ "]");
					} else if (selected == 3) {//antialiasing filtering
						SettingHolder.get("cg_antialiasing_filtering").setValueB(!SettingHolder.get("cg_antialiasing_filtering").getValueB());
						SettingsRenderer.getTexts().get(3).setText("Antialiasing Filtering [" + SettingHolder.get("cg_antialiasing_filtering").getValueDebug()+ "]");
					} else if (selected == 4) {//fps rendering
						SettingHolder.get("cg_fps").setValueB(!SettingHolder.get("cg_fps").getValueB());
						SettingsRenderer.getTexts().get(4).setText("Draw FPS [" + SettingHolder.get("cg_fps").getValueDebug()+ "]");
					} else if (selected == 5) {//theatrical view
						SettingHolder.get("cg_theatrical").setValueB(!SettingHolder.get("cg_theatrical").getValueB());
						SettingsRenderer.getTexts().get(5).setText("Theater Mode [" + SettingHolder.get("cg_theatrical").getValueDebug()+ "]");
					} else if (selected == 6) {//post processing
						//SettingHolder.get("cg_post_processing").setValueB(!SettingHolder.get("cg_post_processing").getValueB());
						Logger.error("This option has been dissabled due to multiple improperly implemented post processing effects.");
						SettingsRenderer.getTexts().get(6).setText("Post Processing Effects [" + SettingHolder.get("cg_post_processing").getValueDebug()+ "]");
					} else if (selected == 7) {//myo armband
						SettingHolder.get("cp_myo_enabled").setValueB(!SettingHolder.get("cp_myo_enabled").getValueB());
						SettingsRenderer.getTexts().get(7).setText("Myo Armband Connection [" + SettingHolder.get("cp_myo_enabled").getValueDebug()+ "]");
						Logger.info("Attempting to create connection with Myo Armband");
						MyoSetup.init(SettingHolder.get("cp_myo_enabled").getValueB());
					} else if (selected == 8) {//quality options
						//DisplayManager.setQuality(SettingHolder.get("cg_quality").getValueI() + 1, true);
						Logger.error("This option has been dissabled due to an improperly implemented quality changing system.");
						SettingsRenderer.getTexts().get(8).setText("Quality [" + SettingHolder.get("cg_quality").getQualityDebug() + "]");
					} else {
						Logger.error("User attempted to select unknown option of " + selected);
						selected = 0;
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					Mift.menuIndex = 0;
					MenuController.selected = 4;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					if (selected - 1 >= min) {
						selected--;
					} else {
						selected = max;
					}
					for (int i = 1; i < SettingsRenderer.getTexts().size(); i++) {
						if (i == selected) {
							SettingsRenderer.getTexts().get(i).setColor(255, 100, 100);
						} else {
							SettingsRenderer.getTexts().get(i).setColor(255, 255, 255);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					if (selected + 1 <= max) {
						selected++;
					} else {
						selected = min;
					}
					for (int i = 1; i < SettingsRenderer.getTexts().size(); i++) {
						if (i == selected) {
							SettingsRenderer.getTexts().get(i).setColor(255, 100, 100);
						} else {
							SettingsRenderer.getTexts().get(i).setColor(255, 255, 255);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					DisplayManager.setFullscreened(!SettingHolder.get("cg_fullscreened").getValueB());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F2) {
					Mouse.setGrabbed(!Mouse.isGrabbed());
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F3) {
					Display.destroy();
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_F) {
					if (changed) {
						//apply changes and save then go back to main menu
						Settings.saveSettings();
						Mift.menuIndex = 0;
						MenuController.selected = 4;
					}
				}
			}
		}
	}
}