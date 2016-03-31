package guis.hud;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import guis.GuiTexture;
import main.Mift;
import models.RawModel;
import toolbox.Maths;

public class HUDRenderer {
	private final RawModel quad;
	private HUDShader shader;
	
	public HUDRenderer() {
		float[] positions = {-1, 1, -1, -1, 1, 1, 1, -1};
		quad = Mift.loader.loadToVAO(positions, 2);
		shader = new HUDShader();
	}
	
	public void render(List<GuiTexture> textures, Compass comp) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		//call rendering
		for (GuiTexture gui : textures) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			Matrix4f Tmatrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformationMatrix(Tmatrix);
			Matrix4f Pmatrix = Maths.createProjectionMatrix(comp.compassTexture.getRotation());
			shader.loadProjectionMatrix(Pmatrix);
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, comp.compassTexture.getTexture());
		Matrix4f matrix = Maths.createTransformationMatrix(comp.compassTexture.getPosition(), comp.compassTexture.getScale());
		shader.loadTransformationMatrix(matrix);
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		//disable and clean everything up
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void cleanUp() {
		shader.cleanUp();
	}
}