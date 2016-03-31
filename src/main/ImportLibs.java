package main;

import java.io.File;
import java.util.Locale;

import io.Logger;
import renderEngine.DisplayManager;

public class ImportLibs {
	private static final String OS_NAME = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

	public ImportLibs() {
		System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
		Logger.info("Succesfully linked lwjgl natives to " + OS_NAME + " system path.");
		if (OS_NAME.startsWith("windows")) {
			DisplayManager.cs_windowsSystem = true;
		} else if (OS_NAME.startsWith("mac")) {
			DisplayManager.cs_windowsSystem = false;
		}
	}
}