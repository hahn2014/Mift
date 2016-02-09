package guis.hud;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class HUDShader extends ShaderProgram {
	
	private static final String VERTEX_FILE = "src/guis/hud/hudVertexShader.txt";
	private static final String FRAGMENT_FILE = "src/guis/hud/hudFragmentShader.txt";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	
	public HUDShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
		
	}
	
	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}
	
	public void loadProjectionMatrix(Matrix4f matrix) {
		super.loadMatrix(location_projectionMatrix, matrix);
	}
	
	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
}