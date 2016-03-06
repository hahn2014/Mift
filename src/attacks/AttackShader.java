package attacks;

import org.lwjgl.util.vector.Matrix4f;

import shaders.ShaderProgram;

public class AttackShader extends ShaderProgram {
	private static final String VERTEX_FILE = "src/attacks/attackVertexShader.glsl";
	private static final String FRAGMENT_FILE = "src/attacks/attackFragmentShader.glsl";
	
	private int location_numberOfRows;
	private int location_projectionMatrix;
	
	public AttackShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "modelViewMatrix");
		super.bindAttribute(5, "textureOffsets");
	}
	
	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
}