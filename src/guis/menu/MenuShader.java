package guis.menu;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class MenuShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/guis/menu/menuVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/guis/menu/menuFragmentShader.txt";
	
	private int location_transformationMatrix;
	private int location_rotation;
	
	public MenuShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
	}
	
	public void loadTransformation(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadRotation(Vector2f rotation) {
		super.load2DVector(location_rotation, rotation);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}