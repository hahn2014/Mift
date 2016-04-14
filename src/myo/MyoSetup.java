package myo;

import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;

import io.Logger;
import renderEngine.DisplayManager;

public class MyoSetup {
	private static Hub hub;
	private static MyoManager myoManager;
	
	public static void init(boolean initialize) {
		if (initialize == true) {
			hub = new Hub("");
			Myo myo = hub.waitForMyo(10000);
			DisplayManager.myo_use = (myo != null);
			if (DisplayManager.myo_use == true) {
				Logger.info("Using Myo for input");
			}
			
			myoManager = new MyoManager();
			hub.addListener(myoManager);
		}
	}
	
	public static Hub getHub() {
		return hub;
	}
	
	public static void update(double diff) {
		hub.run(10);
	}
}