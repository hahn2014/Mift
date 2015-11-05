package main;

import java.io.File;
import java.util.Locale;

public class ImportLibs {
	private static final String OS_NAME = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

	public ImportLibs() {
		System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
		System.out.println("Succesfully linked lwjgl natives to " + OS_NAME + " system path.");
	}
}