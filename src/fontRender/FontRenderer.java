package fontRender;

import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import fontCreator.FontType;
import fontCreator.GUIText;

public class FontRenderer {

	private FontShader shader;

	public FontRenderer() {
		shader = new FontShader();
	}
	
	/**
	 * Only to be accessed within the Text class,
	 * this method will take in the GUIText objects
	 * that are queued to be rendered.
	 * @param texts
	 */
	public void render(Map<FontType, List<GUIText>> texts) {
		prepare();
		for (FontType font : texts.keySet()) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, font.getTextureAtlas());
			for (GUIText text : texts.get(font)) {
				renderText(text);
			}
		}
		endRendering();
	}

	/**
	 * Used to clean up the data and memory
	 * used while rendering text data to screen
	 * this method is a must use at the end
	 * of each render call.
	 */
	public void cleanUp() {
		//clean up the shader program
		//dump unused memory allocations
		//reset variables to be re-written
		shader.cleanUp();
	}
	
	private void prepare() { 
		//enable texture blending
		GL11.glEnable(GL11.GL_BLEND);
		//specify which GL blend function should be used
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		//disable depth testing to prevent 3D positions on text
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//begin text shader program
		shader.start();
	}

	private void renderText(GUIText text) {
		//enable vertex binding to the mesh data
		//of the text object
		GL30.glBindVertexArray(text.getMesh());
		//enable both allocated positions (0&1)
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		//load the color vector into the shader program
		//allows to change color mid render
		shader.loadColor(text.getColor());
		//load the location vector into the shader program
		//allows to change location mid render
		shader.loadTranslation(text.getPosition());
		//draw the vertices to the screen,
		//in a fuck ton of triangles
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, text.getVertexCount());
		//clear up and disable both allocated 
		//system memory slots to be used elsewhere
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		//disable vertex bindings (position 0);
		GL30.glBindVertexArray(0);
	}

	private void endRendering() {
		//finish shader program
		shader.stop();
		//disable texture blending
		GL11.glDisable(GL11.GL_BLEND);
		//re-enable depth testing to
		//allow for 3D rendering
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
}