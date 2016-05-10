package postProcessing;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import gaussianBlur.BlurRenderer;
import main.Mift;
import models.RawModel;
import postProcessing.contrast.ContrastChanger;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static RawModel quad;
	private static ImageRenderer renderer = new ImageRenderer();
	
	//post processing effects
	private static ContrastChanger contrastChanger;
	private static BlurRenderer blur;

	public static void init(boolean renderBlur, boolean renderContrast) {
		quad = Mift.loader.loadToVAO(POSITIONS, 2);
		blur = new BlurRenderer(Display.getWidth(), Display.getHeight(), renderBlur);
		contrastChanger = new ContrastChanger(renderer, renderContrast);
	}

	public static void doPostProcessing(int colorTexture) {
		start(); //start post processing effect rendering
			blur.render(colorTexture, blur.DOUBLEPASS);
			contrastChanger.render(blur.getColorTexture());
		end(); //stop rendering post processing
	}

	public static void cleanUp() {
		contrastChanger.cleanUp();
		blur.cleanUp();
	}

	private static void start() {
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
	}

	private static void end() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
	}
	
	
	public static BlurRenderer getBlurRenderer() {
		return blur;
	}
	
	public static ContrastChanger getContrastChanger() {
		return contrastChanger;
	}
	
	public static void setRenderBlur(boolean r) {
		blur.setRender(r);
	}

	public static void setRenderContrast(boolean r) {
		contrastChanger.setRender(r);
	}
}