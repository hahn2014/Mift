package guis.menu;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import io.SettingHolder;
import io.Settings;
import io.Setting.SettingType;
import main.Mift;

public class SettingsController {
	private int last1 = 0, last2 = 0, last3 = 0;
	
	private int selected = 0;
	private int index = 0;
	
	private static boolean onIntegerChange = false;
	private static int integerChangeNewVal = 0;
	public static boolean changed = false;
	
	public void checkInputs() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { //pressed
			} else { //released
				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					changed = true;
					/********************
					 * DEFAULT SETTINGS *
					 * DEFAULT SETTINGS *
					 * DEFAULT SETTINGS *
					 * DEFAULT SETTINGS *
					 ********************/
					if (SettingHolder.get(index, selected).getType() == SettingType.bool) {
						SettingHolder.get(index, selected).setValueB(!SettingHolder.get(index, selected).getValueB());
						
						if (index == 0) {
							SettingsRenderer.getDefaultTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
						} else if (index == 1) {
							SettingsRenderer.getPostProcessingTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
						} else if (index == 2) {
							SettingsRenderer.getDeveloperTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
						}
						if (index == 2 && selected == 0) { //we are on enable/dissable 
							if (SettingHolder.get("cg_developer").getValueB() == false) { //we know it was just dissabled
								//jump to index 0
								last3 = selected;
								index = 0;
								selected = last1;
								SettingsRenderer.getStaticTexts().get(4).setText(SettingHolder.getDefinition(index, selected));
								SettingsRenderer.getDeveloperTexts().get(last3).setColor(255, 150, 150);
								SettingsRenderer.getDefaultTexts().get(selected).setColor(255, 25, 25);
							}
						}
					} else if (SettingHolder.get(index, selected).getType() == SettingType.integer) {
						if (onIntegerChange == false) {
							onIntegerChange = true;
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI();
						} else {
							onIntegerChange = false;
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
						}
						setChangedIntName((integerChangeNewVal > SettingHolder.get(index, selected).getMin() ? false : true), (integerChangeNewVal < SettingHolder.get(index, selected).getMax() ? false : true));
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_ESCAPE) {
					Mift.menuIndex = 0;
					MenuController.selected = 4;
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_UP) {
					if (onIntegerChange) {
						onIntegerChange = false;
						setChangedIntName(false, false);
					}
					if (index == 0) {
						if (selected - 1 >= 0) {
							selected--;
						} else {
							selected = SettingsRenderer.getDefaultTexts().size() - 1;
						}
						for (int i = 0; i < SettingsRenderer.getDefaultTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getDefaultTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getDefaultTexts().get(i).setColor(255, 255, 255);
							}
						}
					} else if (index == 1) {
						if (selected - 1 >= 0) {
							selected--;
						} else {
							selected = SettingsRenderer.getPostProcessingTexts().size() - 1;
						}
						for (int i = 0; i < SettingsRenderer.getPostProcessingTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getPostProcessingTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getPostProcessingTexts().get(i).setColor(255, 255, 255);
							}
						}
					} else if (index == 2) {
						if (selected - 1 >= 0) {
							selected--;
						} else {
							selected = SettingsRenderer.getDeveloperTexts().size() - 1;
						}
						for (int i = 0; i < SettingsRenderer.getDeveloperTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getDeveloperTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getDeveloperTexts().get(i).setColor(255, 255, 255);
							}
						}
					}
					SettingsRenderer.getStaticTexts().get(4).setText(SettingHolder.getDefinition(index, selected));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					if (onIntegerChange) {
						onIntegerChange = false;
						setChangedIntName(false, false);
					}
					if (index == 0) {
						if (selected + 1 <= SettingsRenderer.getDefaultTexts().size() - 1) {
							selected++;
						} else {
							selected = 0;
						}
						for (int i = 0; i < SettingsRenderer.getDefaultTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getDefaultTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getDefaultTexts().get(i).setColor(255, 255, 255);
							}
						}
					} else if (index == 1) {
						if (selected + 1 <= SettingsRenderer.getPostProcessingTexts().size() - 1) {
							selected++;
						} else {
							selected = 0;
						}
						for (int i = 0; i < SettingsRenderer.getPostProcessingTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getPostProcessingTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getPostProcessingTexts().get(i).setColor(255, 255, 255);
							}
						}
					} else if (index == 2) {
						if (selected + 1 <= SettingsRenderer.getDeveloperTexts().size() - 1) {
							selected++;
						} else {
							selected = 0;
						}
						for (int i = 0; i < SettingsRenderer.getDeveloperTexts().size(); i++) {
							if (i == selected) {
								SettingsRenderer.getDeveloperTexts().get(i).setColor(255, 25, 25);
							} else {
								SettingsRenderer.getDeveloperTexts().get(i).setColor(255, 255, 255);
							}
						}
					}
					SettingsRenderer.getStaticTexts().get(4).setText(SettingHolder.getDefinition(index, selected));
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					if (onIntegerChange == false) {
						if (index == 0) {
							last1 = selected;
							index = 1;
							selected = last2;
							SettingsRenderer.getDefaultTexts().get(last1).setColor(255, 150, 150);
							SettingsRenderer.getPostProcessingTexts().get(selected).setColor(255, 25, 25);
						} else if (index == 1) {
							last2 = selected;
							SettingsRenderer.getPostProcessingTexts().get(last2).setColor(255, 150, 150);
							if (SettingHolder.get("cg_developer").getValueB()) { //only go to index 2 if is developer
								index = 2;
								selected = last3;
								SettingsRenderer.getDeveloperTexts().get(selected).setColor(255, 25, 25);
							} else {
								index = 0;
								selected = last1;
								SettingsRenderer.getDefaultTexts().get(selected).setColor(255, 25, 25);
							}
						} else if (index == 2) {
							last3 = selected;
							index = 0;
							selected = last1;
							SettingsRenderer.getDeveloperTexts().get(last3).setColor(255, 150, 150);
							SettingsRenderer.getDefaultTexts().get(selected).setColor(255, 25, 25);
						}
						SettingsRenderer.getStaticTexts().get(4).setText(SettingHolder.getDefinition(index, selected));
					} else {
						if (integerChangeNewVal + 1 < SettingHolder.get(index, selected).getMax()) {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI() + 1;
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(false, false);
						} else if (integerChangeNewVal + 1 == SettingHolder.get(index, selected).getMax()) {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI() + 1;
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(false, true);
						} else {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI();
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(false, true);
						}
					}
				}
				if (Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					if (onIntegerChange == false) {
						if (index == 0) {
							last1 = selected;
							SettingsRenderer.getDefaultTexts().get(last1).setColor(255, 150, 150);
							if (SettingHolder.get("cg_developer").getValueB()) {
								index = 2;
								selected = last3;
								SettingsRenderer.getDeveloperTexts().get(selected).setColor(255, 25, 25);
							} else {
								index = 1;
								selected = last2;
								SettingsRenderer.getPostProcessingTexts().get(selected).setColor(255, 25, 25);
							}
						} else if (index == 1) {
							last2 = selected;
							index = 0;
							selected = last1;
							SettingsRenderer.getPostProcessingTexts().get(last2).setColor(255, 150, 150);
							SettingsRenderer.getDefaultTexts().get(selected).setColor(255, 25, 25);
						} else if (index == 2) {
							last3 = selected;
							index = 1;
							selected = last2;
							SettingsRenderer.getDeveloperTexts().get(last3).setColor(255, 150, 150);
							SettingsRenderer.getPostProcessingTexts().get(selected).setColor(255, 25, 25);
						}
						SettingsRenderer.getStaticTexts().get(4).setText(SettingHolder.getDefinition(index, selected));
					} else {
						if (integerChangeNewVal - 1 > SettingHolder.get(index, selected).getMin()) {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI() - 1;
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(false, false);
						} else if (integerChangeNewVal - 1 == SettingHolder.get(index, selected).getMin()) {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI() - 1;
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(true, false);
						} else {
							integerChangeNewVal = SettingHolder.get(index, selected).getValueI();
							SettingHolder.get(index, selected).setValueI(integerChangeNewVal);
							setChangedIntName(true, false);
						}
					}
				}
				
				if (Keyboard.getEventKey() == Keyboard.KEY_F1) {
					Mouse.setGrabbed(!Mouse.isGrabbed());
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
	
	private void setChangedIntName(boolean isCapedMin, boolean isCapedMax) {
		if (onIntegerChange) { //set text to changing int text
			
			String prevVal;
			String curVal;
			String nextVal;
			if (isCapedMin == false) {
				prevVal = (integerChangeNewVal - 1) + " ";
			} else {
				prevVal = " ";
			}
			if (isCapedMax == false) {
				nextVal = " " + (integerChangeNewVal + 1);
			} else {
				nextVal = " ";
			}
			curVal = "<- [" + integerChangeNewVal + "] ->";
	
			if (index == 0) {
				SettingsRenderer.getDefaultTexts().get(selected).setText(prevVal + curVal + nextVal);
			} else if (index == 1) {
				SettingsRenderer.getPostProcessingTexts().get(selected).setText(prevVal + curVal + nextVal);
			} else if (index == 2) {
				SettingsRenderer.getDeveloperTexts().get(selected).setText(prevVal + curVal + nextVal);
			}
		} else { //set it back to default
			if (index == 0) {
				SettingsRenderer.getDefaultTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
			} else if (index == 1) {
				SettingsRenderer.getPostProcessingTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
			} else if (index == 2) {
				SettingsRenderer.getDeveloperTexts().get(selected).setText(SettingHolder.get(index, selected).getDebugNameAndValue());
			}
		}
	}
}