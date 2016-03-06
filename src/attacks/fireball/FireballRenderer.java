package attacks.fireball;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import attacks.AttackShader;
import models.RawModel;
import models.TexturedModel;
import renderEngine.MasterRenderer;
import textures.ModelTexture;
import toolbox.Maths;

public class FireballRenderer {
	private AttackShader shader;
	Matrix4f projectionMatrix = MasterRenderer.createProjectionMatrix();

	public FireballRenderer(AttackShader shader) {
		this.shader = shader;
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Fireball fireball) {
		TexturedModel model;
		model = fireball.getTexturedModel();
		prepareTexturedModel(model);
		if (fireball.isRenderable()) {
			prepareInstance(fireball.getCurrentPosition(), fireball.getRotation());
			GL11.glDrawElements(GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		}
		unbindTexturedModel();
		fireball.update();
	}

	private void prepareTexturedModel(TexturedModel model) {
		RawModel rawModel = model.getRawModel();
		GL30.glBindVertexArray(rawModel.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		GL20.glEnableVertexAttribArray(2);
		ModelTexture texture = model.getTexture();
		shader.loadNumberOfRows(texture.getNumberOfRows());
		if (texture.isHasTransparency()) {
			MasterRenderer.disableCulling();
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, model.getTexture().getID());
	}

	private void unbindTexturedModel() {
		MasterRenderer.enableCulling();
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL20.glDisableVertexAttribArray(2);
		GL30.glBindVertexArray(0);
	}

	private void prepareInstance(Vector3f curPos, Vector3f rotation) {
		Matrix4f transformationMatrix = Maths.createTransformationMatrix(curPos, rotation.getX(),
				rotation.getY(), rotation.getZ(), 1);
		shader.loadProjectionMatrix(transformationMatrix);
	}
}