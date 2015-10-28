package engineTester;

import java.io.File;
import java.util.Locale;

public class ImportLibs {
	private static final String OS_NAME = System.getProperty("os.name", "generic").toLowerCase(Locale.ENGLISH);

	public ImportLibs() {
		if (isWindows()) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
			System.out.println("Succesfully linked lwjgl natives to the Windows system path.");
		} else if (isMac()) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
			System.out.println("Succesfully linked lwjgl natives to the OS X system path.");
		} else if (isUnix()) {
			System.setProperty("org.lwjgl.librarypath", new File("lib/lwjgl-2.9.3/native/").getAbsolutePath());
			System.out.println("Succesfully linked lwjgl natives to the Linux system path.");
		} else {
			System.out.println(OS_NAME + " -- ");
			System.err.println("The system os is not currently supported by "
					+ MainGameLoop.NAME + " " + MainGameLoop.RELEASE_TITLE +  MainGameLoop.RELEASE + "." + MainGameLoop.BUILD);
			System.exit(-1);
		}
	}

	private static boolean isWindows() {
		return (OS_NAME.indexOf("win") >= 0);
	}

	private static boolean isMac() {
		return (OS_NAME.indexOf("mac") >= 0);
	}

	private static boolean isUnix() {
		return (OS_NAME.indexOf("nux") >= 0);
	}
}