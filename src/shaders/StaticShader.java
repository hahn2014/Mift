package shaders;

import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Light;
import entities.OverheadCamera;
import toolbox.Maths;

public class StaticShader extends ShaderProgram {

	private static final int MAX_LIGHTS = 4;

	private static final String VERTEX_FILE = "/shaders/vertexShader.glsl";
	private static final String FRAGMENT_FILE = "/shaders/fragmentShader.glsl";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition[];
	private int location_lightColour[];
	private int location_attenuation[];
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_numberOfRows;
	private int location_offset;
	private int location_plane;
	private int location_toShadowMapSpace;
	private int location_shadowMap;
	private int location_specularMap;
	private int location_usesSpecularMap;
	private int location_modelTexture;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoordinates");
		super.bindAttribute(2, "normal");
	}

	@Override
	protected void getAllUniformLocations() {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_specularMap = super.getUniformLocation("specularMap");
		location_usesSpecularMap = super.getUniformLocation("usesSpecularMap");
		location_modelTexture = super.getUniformLocation("modelTexture");
		location_projectionMatrix = super.getUniformLocation("projectionMatrix");
		location_viewMatrix = super.getUniformLocation("viewMatrix");
		location_shineDamper = super.getUniformLocation("shineDamper");
		location_reflectivity = super.getUniformLocation("reflectivity");
		location_skyColor = super.getUniformLocation("skyColour");
		location_numberOfRows = super.getUniformLocation("numberOfRows");
		location_offset = super.getUniformLocation("offset");
		location_plane = super.getUniformLocation("plane");
		location_toShadowMapSpace = super.getUniformLocation("toShadowMapSpace");
		location_shadowMap = super.getUniformLocation("shadowMap");

		location_lightPosition = new int[MAX_LIGHTS];
		location_lightColour = new int[MAX_LIGHTS];
		location_attenuation = new int[MAX_LIGHTS];
		for (int i = 0; i < MAX_LIGHTS; i++) {
			location_lightPosition[i] = super.getUniformLocation("lightPosition[" + i + "]");
			location_lightColour[i] = super.getUniformLocation("lightColour[" + i + "]");
			location_attenuation[i] = super.getUniformLocation("attenuation[" + i + "]");
		}
	}
	
	public void connectTextureUnits() {
		super.loadInt(location_shadowMap, 5);
		super.loadInt(location_modelTexture, 0);
		super.loadInt(location_specularMap, 1);
	}
	
	public void loadUseSpecularMap(boolean usesMap) {
		super.loadBoolean(location_usesSpecularMap, usesMap);
	}
	
	private void loadShadowMap() {
		super.loadInt(location_shadowMap, 5);
	}
	
	public void loadToShadowSpaceMatrix(Matrix4f matrix) {
		super.loadMatrix(location_toShadowMapSpace, matrix);
		loadShadowMap();
	}

	public void loadClipPlane(Vector4f plane) {
		super.load4DVector(location_plane, plane);
	}

	public void loadNumberOfRows(int numberOfRows) {
		super.loadFloat(location_numberOfRows, numberOfRows);
	}

	public void loadOffset(float x, float y) {
		super.load2DVector(location_offset, new Vector2f(x, y));
	}

	public void loadSkyColor(float r, float g, float b) {
		super.load3DVector(location_skyColor, new Vector3f(r, g, b));
	}
	
	public void loadSkyColor(int r, int g, int b) {
		super.load3DVector(location_skyColor, new Vector3f(r /255.0f, g / 255.0f, b / 255.0f));
	}

	public void loadShineVariables(float damper, float reflectivity) {
		super.loadFloat(location_shineDamper, damper);
		super.loadFloat(location_reflectivity, reflectivity);
	}

	public void loadTransformationMatrix(Matrix4f matrix) {
		super.loadMatrix(location_transformationMatrix, matrix);
	}

	public void loadLights(List<Light> lights) {
		for (int i = 0; i < MAX_LIGHTS; i++) {
			if (i < lights.size()) {
				super.load3DVector(location_lightPosition[i], lights.get(i).getPosition());
				super.load3DVector(location_lightColour[i], lights.get(i).getColor());
				super.load3DVector(location_attenuation[i], lights.get(i).getAttenuation());
			} else {
				super.load3DVector(location_lightPosition[i], new Vector3f(0, 0, 0));
				super.load3DVector(location_lightColour[i], new Vector3f(0, 0, 0));
				super.load3DVector(location_attenuation[i], new Vector3f(1, 0, 0));
			}
		}
	}
	
	public void loadViewMatrix(Camera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}

	public void loadViewMatrix(OverheadCamera camera) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix(location_viewMatrix, viewMatrix);
	}
	
	public void loadProjectionMatrix(Matrix4f projection) {
		super.loadMatrix(location_projectionMatrix, projection);
	}
}