package bloom;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import postProcessing.ImageRenderer;

public class BrightFilter {

	private ImageRenderer renderer;
	private BrightFilterShader shader;
	
	private boolean render = true;

	public BrightFilter(int width, int height) {
		shader = new BrightFilterShader();
		renderer = new ImageRenderer(width, height);
	}

	public void render(int texture) {
		if (render) {
			shader.start();
				GL13.glActiveTexture(GL13.GL_TEXTURE0);
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				renderer.renderQuad();
			shader.stop();
		}
	}

	public int getColorTexture() {
		return renderer.getOutputTexture();
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}

	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}

}
