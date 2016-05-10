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
import io.SettingHolder;
import main.Mift;

public class DisplayManager {
	
	private static final int downscale = 1;

	private static int WIDTH = 1920 / downscale;
	private static int HEIGHT = 1080 / downscale;
	private static final double FPS_CAP = 59.999998;
	private static final double MAX_FPS_CAP = 9999.999998;
	
	private static long lastFrameTime;
	private static float delta; // Measured in microseconds
	
	private static long currentFrameTime;

	public static void createDisplay() {
		Display.destroy();
		getQuality();
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try {
			setDisplayMode(WIDTH, HEIGHT);
			if (SettingHolder.get("cg_antialiasing_filtering").getValueB() == true) {
				Display.create(new PixelFormat().withSamples(1 * SettingHolder.get("cg_quality").getValueI()), attribs);
				GL11.glEnable(GL13.GL_MULTISAMPLE);
			} else {
				Display.create(new PixelFormat(), attribs);
			}
			Display.setTitle(Mift.NAME + " Release " + Mift.RELEASE + " is initializing");
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, (int)(WIDTH * Display.getPixelScaleFactor()), 
	             (int)(HEIGHT * Display.getPixelScaleFactor()));
		lastFrameTime = getCurrentTime();
	}
	
	public static void updateDisplay() {
		Display.sync((int) (SettingHolder.get("cg_vsync").getValueB() ? (FPS_CAP) : (MAX_FPS_CAP)));
		Display.update();
		currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime); // Measured in nanoseconds
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
				&& (Display.isFullscreen() == SettingHolder.get("cg_fullscreened").getValueB())) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (SettingHolder.get("cg_fullscreened").getValueB()) {
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
				Logger.error("Failed to find value mode: [" + width + "x" + height + "] fullscreened " + SettingHolder.get("cg_fullscreened").getValueB());
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(SettingHolder.get("cg_fullscreened").getValueB());
			Display.setVSyncEnabled(SettingHolder.get("cg_vsync").getValueB());
		} catch (LWJGLException e) {
			Logger.error("Unable to setup mode " + width + "x" + height + " fullscreen=" + SettingHolder.get("cg_fullscreened").getValueB() + e);
		}
	}
	
	private static void getQuality() {
		if (SettingHolder.get("cg_quality").getValueI() == 1) {
			WIDTH = 1280;
			HEIGHT = 720;
		} else if (SettingHolder.get("cg_quality").getValueI() == 2) {
			WIDTH = 1920;
			HEIGHT = 1080;
		} else if (SettingHolder.get("cg_quality").getValueI() == 3) {
			WIDTH = 2560;
			HEIGHT = 1440;
		} else if (SettingHolder.get("cg_quality").getValueI() == 4) {
			WIDTH = 3840;
			HEIGHT = 2160;
		} else {
			SettingHolder.get("cg_quality").setValueI(1);
			getQuality();
		}
	}
	
	public static void setQuality(int quality, boolean ingame) {
		SettingHolder.get("cg_quality").setValueI(quality);
		getQuality();
		if (ingame) {
			createDisplay();
		}
	}
	
	public static void setFullscreened(boolean fs) {
		if (fs == true) {
			SettingHolder.get("cg_fullscreened").setValueB(true);
			setDisplayMode(WIDTH, HEIGHT);
		} else {
			SettingHolder.get("cg_fullscreened").setValueB(false);
			setDisplayMode(WIDTH, HEIGHT);
		}
	}

	public static int getWidth() {
		return WIDTH;
	}

	public static void setWidth(int wIDTH) {
		WIDTH = wIDTH;
	}

	public static int getHeight() {
		return HEIGHT;
	}

	public static void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}
}