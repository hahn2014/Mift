package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.PixelFormat;

import engineTester.MainGameLoop;

public class DisplayManager {

	private static final int WIDTH = 1280; //(int)(1280 / 1.8);
	private static final int HEIGHT = 720; //(int)(720 / 1.2);
	private static final int FPS_CAP = 30;
	
	public static enum QUALITY {
		LIGHT,
		MODERATE,
		ULTRA
	};
	
	public static QUALITY quality;
	public static boolean debugPolys = false;
	
	private static long lastFrameTime;
	private static float delta;

	public static void createDisplay(QUALITY q) {
		quality = q;
		ContextAttribs attribs = new ContextAttribs(3, 2).withForwardCompatible(true).withProfileCore(true);
		try {
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.create(new PixelFormat(), attribs);
			Display.setTitle(MainGameLoop.NAME + " Release " + MainGameLoop.RELEASE + " is initializing");
			GL11.glEnable(GL13.GL_MULTISAMPLE);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

		GL11.glViewport(0, 0, WIDTH, HEIGHT);
		lastFrameTime = getCurrentTime();
	}

	public static void updateDisplay() {
		Display.sync(FPS_CAP);
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

}
