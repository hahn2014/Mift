package particles;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;

import shaders.ShaderProgram;

public class ParticleShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/particles/particleVShader.txt";
	private static final String FRAGMENT_FILE = "src/particles/particleFShader.txt";

	private int location_modelViewMatrix;
	private int location_projectionMatrix;
	private int location_textureOffset1;
	private int location_textureOffset2;
	private int location_textureCoordInfo;

	public ParticleShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_modelViewMatrix = super.getUniformLocation("modelViewMatrix");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_textureOffset1 = super.getUniformLocation("textureOffset1");
		location_textureOffset2 = super.getUniformLocation("textureOffset2");
		location_textureCoordInfo = super.getUniformLocation("textureCoordInfo");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
	}
	
	protected void loadTextureCoordInfo(Vector2f offset1, Vector2f offset2, float numberOfRows, float blend) {
		super.load2DVector(location_textureOffset1, offset1);
		super.load2DVector(location_textureOffset2, offset2);
		super.load2DVector(location_textureCoordInfo, new Vector2f(numberOfRows, blend));
	}

	protected void loadModelViewMatrix(Matrix4f modelView) {
		super.loadMatrix(location_modelViewMatrix, modelView);
	}

	protected void loadProjectionMatrix(Matrix4f projectionMatrix) {
		super.loadMatrix(location_projectionMatrix, projectionMatrix);
	}
}