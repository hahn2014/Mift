package postProcessing.DOF;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import postProcessing.ImageRenderer;

public class DOFChanger {
	private ImageRenderer renderer;
	private DOFShader shader;
	
	public DOFChanger() {
		shader = new DOFShader();
		renderer = new ImageRenderer();
	}
	
	public void render(int texture) {
		shader.start();
		shader.loadDepthDistance(200f);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}
}