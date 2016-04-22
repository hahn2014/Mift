package myo;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

import guis.menu.SettingsRenderer;
import io.Logger;
import io.SettingHolder;

public class MyoSetup {
	private static Hub hub;
	private static MyoManager myoManager;
	
	public static void init(boolean initialize) {
		if (initialize) {
			try {
				hub = new Hub("");
				Myo myo = hub.waitForMyo(10000);
				SettingHolder.get("cp_myo_enabled").setValueB(myo != null);
				if (SettingHolder.get("cp_myo_enabled").getValueB()) {
					Logger.info("Using Myo for input");
				}
				
				myoManager = new MyoManager();
				hub.addListener(myoManager);
			} catch (Exception e) { //myo init failsafe will disable use myo if unable to access
				Logger.error("Unable to initialize Myo Armband. -> ");
				e.printStackTrace();
				SettingHolder.get("cp_myo_enabled").setValueB(false);
				SettingsRenderer.getTexts().get(7).setText("Myo Armband Connection [Off]");
			}
		}
	}
	
	public static Hub getHub() {
		return hub;
	}
	
	public static void update(double diff) {
		hub.run(10);
	}
}