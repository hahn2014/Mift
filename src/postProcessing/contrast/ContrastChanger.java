package postProcessing.contrast;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import postProcessing.ImageRenderer;

public class ContrastChanger {
	private ImageRenderer renderer;
	private ContrastShader shader;
	
	private boolean render = false;
	
	public ContrastChanger(ImageRenderer renderer) {
		shader = new ContrastShader();
		this.renderer = renderer;
	}
	
	public void render(int texture, boolean render) {
		this.render = render;
		shader.start();
			if (render) {
				shader.loadContrast(0.25f);
			}
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
			renderer.renderQuad();
		shader.stop();
	}
	
	public void cleanUp() {
		renderer.cleanUp();
		shader.cleanUp();
	}
	
	public int getColorTexture() {
		return renderer.getOutputTexture();
	}
	
	public boolean getRender() {
		return render;
	}
	
	public void setRender(boolean render) {
		this.render = render;
	}
}