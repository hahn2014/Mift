package guis_old;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class GuiShader extends ShaderProgram {

	private static final String VERTEX_FILE = "/guis_old/guiVertexShader.glsl";
	private static final String FRAGMENT_FILE = "/guis_old/guiFragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_rotation;

	public GuiShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	public GuiShader(String vertexFile, String fragFile) {
		super(vertexFile, fragFile);
	}

	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadRotation(Vector2f rotation) {
		super.load2DVector(location_rotation, rotation);
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}

	@Override
	protected void bindAttributes() {
		bindAttribute(0, "position");
	}
}