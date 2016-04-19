package main;

import java.io.File;
import java.util.Locale;

import io.Logger;

public class ImportLibs {
	private static final String OS_NAME = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

	public ImportLibs() {
		System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
		System.setProperty("org.lwjgl.opengl.Display.enableHighDPI", "true");
		Logger.info("Succesfully linked lwjgl natives to " + OS_NAME + " system path.");
	}
}