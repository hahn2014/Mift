package guis_old;

import java.nio.FloatBuffer;
import java.util.List;

import models.RawModel;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import io.Logger;
import main.Mift;
import toolbox.Maths;

public class GuiRenderer {
	private final RawModel quad;
	private GuiShader shader;
	private GuiShader shaper;

	public GuiRenderer() {
		float[] positions = { -1, 1, -1, -1, 1, 1, 1, -1 };
		quad = Mift.loader.loadToVAO(positions, 2);
		shader = new GuiShader();
		shaper = new GuiShader("/guis_old/guiQuadShader.glsl", "/guis_old/guiQuadFragmentShader.glsl");
	}
	
	public GuiShader getShader() {
		return shader;
	}

	public void render(List<GuiTexture> guis) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		Matrix4f matrix = null;
		for (GuiTexture gui : guis) {
			GL13.glActiveTexture(GL13.GL_TEXTURE0);
			GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
			matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
			shader.loadTransformation(matrix);
			//shader.loadRotation(gui.getRotation());
			GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		}
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void render(GuiTexture gui) {
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, gui.getTexture());
		Matrix4f matrix = Maths.createTransformationMatrix(gui.getPosition(), gui.getScale());
		shader.loadTransformation(matrix);
		//shader.loadRotation(gui.getRotation());
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, quad.getVertexCount());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}

	public void cleanUp() {
		shader.cleanUp();
	}
	
	/**
	 * Draw a line on the screen
	 * @param x1 Must be between -1.0 and 1.0. 
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	public void drawLine(float x1, float y1, float x2, float y2) {
		shader.stop();
		shaper.start();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		int vao = GL30.glGenVertexArrays();
		GL30.glBindVertexArray(vao);
				
		int vbo = GL15.glGenBuffers();
		
		GL11.glDisableClientState(GL15.GL_ARRAY_BUFFER);
				
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ARRAY_BUFFER_ARB, 0);
		ARBVertexBufferObject.glBindBufferARB(ARBVertexBufferObject.GL_ELEMENT_ARRAY_BUFFER_ARB, 0);
		
		FloatBuffer fbuff = BufferUtils.createFloatBuffer(4);
		fbuff.put(new float[] {
				x1, y1,
				x2, y2
		});

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fbuff, GL15.GL_STATIC_DRAW);
		
		GL20.glVertexAttribPointer(shaper.getAttribIndex("position"), 4, false, 0, fbuff);
		GL20.glEnableVertexAttribArray(shaper.getAttribIndex("position"));
		
		GL30.glBindVertexArray(vao);
		GL11.glDrawArrays(GL11.GL_LINE, 0, 4);

		
		/*
		Matrix4f matrix = Maths.createTransformationMatrix(new Vector2f(x1, y1), new Vector2f(1.0f, 1.0f));
		shader.loadTransformation(matrix);
		GL11.glDrawArrays(GL11.GL_LINES, 0, quad.getVertexCount());
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		*/
		shaper.stop();
		shader.start();
	}
}
