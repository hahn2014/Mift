package postProcessing;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import models.RawModel;
import postProcessing.DOF.DOFChanger;
import postProcessing.contrast.ContrastChanger;
import renderEngine.Loader;

public class PostProcessing {

	private static final float[] POSITIONS = { -1, 1, -1, -1, 1, 1, 1, -1 };
	private static RawModel quad;
	
	//post processing effects
	private static ContrastChanger contrastChanger;
	private static DOFChanger depthOfFieldChanger;

	public static void init(Loader loader) {
		quad = loader.loadToVAO(POSITIONS, 2);
		contrastChanger = new ContrastChanger();
		depthOfFieldChanger = new DOFChanger();
	}

	public static void doPostProcessing(int colorTexture) {
		start();
		contrastChanger.render(colorTexture);
		depthOfFieldChanger.render(colorTexture);
		end();
	}

	public static void cleanUp() {
		contrastChanger.cleanUp();
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
}