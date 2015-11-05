package menu;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import guis.GuiShader;
import main.Mift;
import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class MainMenu {

	private InputHandler inputHandler;
	private final RawModel quad;
	private GuiShader shader;

	public MainMenu(Loader loader) {
		inputHandler = new InputHandler();
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = loader.loadToVAO(positions, 2);
		shader = new GuiShader();
	}

	public void update() {
		inputHandler.getKeyboardInput(Mift.GAMESTATE.MENU);
		inputHandler.getMouseClick(Mift.GAMESTATE.MENU);
	}

	public void render() {
		//GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		for (int i = 0; i < 1; i++) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, 1);
			Matrix4f matrix = Maths.createTransformationMatrix(new Vector2f(1, 1), new Vector2f(0.5f, 0.5f));
			shader.loadTransformation(matrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
}