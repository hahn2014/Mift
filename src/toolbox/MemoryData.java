package toolbox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import renderEngine.DisplayManager;

public class MemoryData {
	private static int mb = 1024 * 1024;
	private static Runtime runtime = Runtime.getRuntime();
	private static String GRAPHICS_CARD = null;
	private static String NATIVE_RES = null;
	private static String GRAPHICS_MEMORY = null;

	/**
	 * Get the amount of used memory in the stack
	 * @return Long
	 */
	public static long getUsedMemory() {
		return ((runtime.totalMemory() - runtime.freeMemory()) / mb);
	}

	/**
	 * Get the amount of unused memory in the stack
	 * @return Long
	 */
	public static long getFreeMemory() {
		return runtime.freeMemory() / mb;
	}

	/**
	 * Get the total memory implemented into system.
	 * @return Long
	 */
	public static long getTotalMemory() {
		return runtime.totalMemory() / mb;
	}

	/**
	 * Get the maximum allocated memory within the stack
	 * @return Long
	 */
	public static long getMaxMemory() {
		return runtime.maxMemory() / mb;
	}
	
	/**
	 * Get the name of the system's graphics
	 * Card. Or returns null if inapplicable
	 * @return Graphics Card name
	 */
	public static String getGraphicsCard() {
		return GRAPHICS_CARD;
	}
	
	/**
	 * Get the native screen resolution of
	 * the current systems monitor or screen
	 * @return Resolution, Architecture, Refresh Rate
	 */
	public static String getNativeResolution() {
		return NATIVE_RES;
	}

	/**
	 * Get the total memory the current system's
	 * graphics card can support. returns as a string
	 * @return Graphics Card Total Memory
	 */
	public static String getGraphicsMemory() {
		return GRAPHICS_MEMORY;
	}
	/**
	 * This method will run DXDiag process and write the
	 * output to a file to be read from and accessed when need be.
	 */
	public static void sendGraphicsInformationToFile() {
		String filePath = "graphicsDetails.sysinf";
		if (DisplayManager.debugPolys) {
			//make sure the file doesn't already exist
			if (new File("graphicsDetails.sysinf").exists() == false) {
				try {
					System.out.println("We did not find the file " + filePath + ". We will attempt to create it");
					// Use "dxdiag /t" variant to redirect output to a given file
					ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "dxdiag", "/t", filePath);
					System.out.println("Opening DXDiag for system information");
					Process p = pb.start();
					p.waitFor();
		
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(filePath));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.trim().startsWith("Card name:")) {
							GRAPHICS_CARD = line.trim().substring(10, line.trim().length());
						} else if (line.trim().startsWith("Current Mode:")) {
							NATIVE_RES = line.trim().substring(13, line.trim().length());
						} else if (line.trim().startsWith("Display Memory:")) {
							GRAPHICS_MEMORY = line.trim().substring(15, line.trim().length());
						}
					}
				} catch (IOException | InterruptedException ex) {
					ex.printStackTrace();
				}
			} else {
				System.out.println("We have found an already existant copy of " + filePath + ". We will attempt to load from the file");
				try {
					@SuppressWarnings("resource")
					BufferedReader br = new BufferedReader(new FileReader(filePath));
					String line;
					while ((line = br.readLine()) != null) {
						if (line.trim().startsWith("Card name:")) {
							GRAPHICS_CARD = line.trim().substring(10, line.trim().length());
						} else if (line.trim().startsWith("Current Mode:")) {
							NATIVE_RES = line.trim().substring(13, line.trim().length());
						} else if (line.trim().startsWith("Display Memory:")) {
							GRAPHICS_MEMORY = line.trim().substring(15, line.trim().length());
						}
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}