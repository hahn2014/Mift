package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

import main.Mift;

public class DisplayManager {

	private static int WIDTH = 1920;
	private static int HEIGHT = 1080;
	private static final double FPS_CAP = 59.999998;
	public static boolean isFullscreen = true;
	public static boolean anisotropicFiltering = true;
	public static boolean antialiasingFiltering = true;

	public static boolean debugPolys = false;

	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay(int quality) {
		getQuality(quality);
		ContextAttribs attribs = new ContextAttribs(3, 3).withForwardCompatible(true).withProfileCore(true);
		try {
			setDisplayMode(WIDTH, HEIGHT);
			if (antialiasingFiltering == true) {
				Display.create(new PixelFormat().withSamples(4 * quality), attribs);
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
	}

	public static void updateDisplay() {
		Display.sync((int) FPS_CAP);
		Display.update();
		long currentFrameTime = getCurrentTime();
		delta = (currentFrameTime - lastFrameTime) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds() {
		return delta;
	}

	public static void closeDisplay() {
		Display.destroy();
	}

	private static long getCurrentTime() {
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}
	
	public static String getRes() {
		return "[" + WIDTH + ", " + HEIGHT + "]";
	}

	private static void setDisplayMode(int width, int height) {
		if ((Display.getDisplayMode().getWidth() == width) && (Display.getDisplayMode().getHeight() == height)
				&& (Display.isFullscreen() == isFullscreen)) {
			return;
		}

		try {
			DisplayMode targetDisplayMode = null;

			if (isFullscreen) {
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
				System.out.println("Failed to find value mode: " + width + "x" + height + " fs=" + isFullscreen);
				return;
			}

			Display.setDisplayMode(targetDisplayMode);
			Display.setFullscreen(isFullscreen);
			Display.setVSyncEnabled(true);

		} catch (LWJGLException e) {
			System.out.println("Unable to setup mode " + width + "x" + height + " fullscreen=" + isFullscreen + e);
		}
	}
	
	private static void getQuality(int quality) {
		if (quality == 1) {
			WIDTH = 1280;
			HEIGHT = 720;
		} else if (quality == 2) {
			WIDTH = 1920;
			HEIGHT = 1080;
		} else if (quality == 3) {
			WIDTH = 2560;
			HEIGHT = 1440;
		} else if (quality == 4) {
			WIDTH = 3840;
			HEIGHT = 2160;
		} else {
			getQuality(1);
		}
	}
}