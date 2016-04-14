package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

import io.Logger;
import main.Mift;

public class DisplayManager {
	
	private static final int downscale = 1;

	private static int WIDTH = 1920 / downscale;
	private static int HEIGHT = 1080 / downscale;
	private static final double FPS_CAP = 59.999998;
	
	public static boolean cg_fullscreened = false;
	public static boolean cg_anisotropic_filtering = false;
	public static boolean cg_antialiasing_filtering = false;
	public static boolean cg_debug_polygons = false;
	public static boolean cg_developer_status = false;
	public static boolean cs_windowsSystem = true;
	public static boolean myo_use = true;
	public static int cg_quality = 1;

	private static long lastFrameTime;
	private static float delta; // Measured in nanoseconds

	public static void createDisplay() {
		getQuality();
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try {
			setDisplayMode(WIDTH, HEIGHT);
			if (cg_antialiasing_filtering == true) {
				Display.create(new PixelFormat().withSamples(4 * cg_quality), attribs);
			} else {
				Display.create(new PixelFormat(), attribs);
			}
			Display.setTitle(Mift.NAME + " Release " + Mift.RELEASE + " is initializing");
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
		testDelta();
	}
	
	public static void testDelta() {
		lastFrameTime = getCurrentTime();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Logger.info((getCurrentTime() - lastFrameTime) + "");
	}

	public static void updateDisplay() {
		Display.sync((int) FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime);// / 1000f; // Measured in nanoseconds
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static void closeDisplay() {
		Display.destroy();
	}

	/**
	 * Get the current system time in milliseconds
	 * @return Current system time in milliseconds
	 */
	private static long getCurrentTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public static String getRes() {
		return WIDTH + "x" + HEIGHT;
	}

	private static void setDisplayMode(int width, int height) {
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == cg_fullscreened)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (cg_fullscreened) {
				DisplayMode[] modes = Display.getAvailableDisplayModes();
				int freq = 0;

				for (int i = 0; i < modes.length; i++) {
					DisplayMode current = modes[i];

					if ((current.getWidth() == width) && (current.getHeight() == height)) {
						if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
							if ((targetDisplayMode == null)
									|| (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
								targetDisplayMode = current;
								freq = targetDisplayMode.getFrequency();
							}
						}

						if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel())
								&& (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
							targetDisplayMode = current;
							break;
						}
					}
				}
			} else {
				targetDisplayMode = new DisplayMode(width, height);
			}

			if (targetDisplayMode == null) {
				Logger.error("Failed to find value mode: " + width + "x" + height + " fs=" + cg_fullscreened);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(cg_fullscreened);
			Display.setVSyncEnabled(true);

		} catch (LWJGLException e) {
			Logger.error("Unable to setup mode " + width + "x" + height + " fullscreen=" + cg_fullscreened + e);
		}
	}
	
	private static void getQuality() {
		if (cg_quality == 1) {
			if (cs_windowsSystem == true) {
				WIDTH = 1280;
				HEIGHT = 720;
			} else {
				WIDTH = 1280;
				HEIGHT = 800;
			}
		} else if (cg_quality == 2) {
			if (cs_windowsSystem == true) {
				WIDTH = 1920;
				HEIGHT = 1080;
			} else {
				WIDTH = 1280;
				HEIGHT = 800;
			}
		} else if (cg_quality == 3) {
			if (cs_windowsSystem == true) {
				WIDTH = 2560;
				HEIGHT = 1440;
			} else {
				WIDTH = 1280;
				HEIGHT = 800;
			}
		} else if (cg_quality == 4) {
			if (cs_windowsSystem == true) {
				WIDTH = 3840;
				HEIGHT = 2160;
			} else {
				WIDTH = 1280;
				HEIGHT = 800;
			}
		} else {
			cg_quality = 1;
			getQuality();
		}
	}
	
	public static void setFullscreened(boolean fs) {
		if (fs == true) {
			cg_fullscreened = true;
			setDisplayMode(WIDTH, HEIGHT);
		} else {
			cg_fullscreened = false;
			setDisplayMode(WIDTH, HEIGHT);
		}
	}
}