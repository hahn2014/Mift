package gui;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import guis_old.GuiShader;
import io.Logger;
import main.Mift;
import models.RawModel;
import renderEngine.DisplayManager;

public class GuiDrawer {
	private static RawModel screen;
	
	private static boolean started; // 
	private static GuiShader shader;
	
	public static void loadShader(GuiShader sshader) {
		shader = sshader;
		screen = Mift.loader.loadToVAO(new float[]{-1, 1, -1, -1, 1, 1, 1, -1}, 2);
	}
	
	public static void start() {
		shader.start();
		GL30.glBindVertexArray(screen.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		started = true;
	}
	
	public static void end() {
		shader.stop();
		started = false;
	}

	/**
	 * Draw a line on the screen
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public static void drawLine(float x1, float y1, float x2, float y2) {
		if (!started) {
			Logger.error("Tried to draw a polygon when not started");
			return;
		}
		
		GL11.glBegin(GL11.GL_LINES);
			GL11.glVertex2f(x1, y1);
			GL11.glVertex2d(x2, y2);
		GL11.glEnd();
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public static void drawRect(float x, float y, float width, float height) {
		checkState();
		
		// Draw the points from top left -> top right -> bottom right -> bottom left
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(x, y);
			GL11.glVertex2f(x + width, y);
			GL11.glVertex2f(x + width, y + height);
			GL11.glVertex2f(x, y + height);
		GL11.glEnd();
	}
	
	public static void setDrawColor(float r, float g, float b, float a) {
		GL11.glColor4f(r, b, g, a);
	}
	
	private static void checkState() {
		if (!started) {
			throw new IllegalStateException("Cannot draw gui shapes with out being start()ed");
		}
	}
	
}