package fontRender;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import shaders.ShaderProgram;

public class FontShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/fontRender/fontVertex.txt";
	private static final String FRAGMENT_FILE = "src/fontRender/fontFragment.txt";
	
	private int location_color;
	private int location_translation;
	private int location_width;
	private int location_edge;
	private int location_borderWidth;
	private int location_borderEdge;
	private int location_offset;
	private int location_outlineColor;
	
	public FontShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void getAllUniformLocations() {
		location_color = super.getUniformLocation("color");
		location_translation = super.getUniformLocation("translation");
		location_width = super.getUniformLocation("location_width");
		location_edge = super.getUniformLocation("location_edge");
		location_borderWidth = super.getUniformLocation("location_borderWidth");
		location_borderEdge = super.getUniformLocation("location_borderEdge");
		location_offset = super.getUniformLocation("location_offset");
		location_outlineColor = super.getUniformLocation("location_outlineColor");
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}
	
	protected void loadColor(Vector3f color) {
		super.load3DVector(location_color, color);
	}
	
	protected void loadTranslation(Vector2f translation) {
		super.load2DVector(location_translation, translation);
	}
	
	protected void loadDistanceField(float width, float edge) {
		super.loadFloat(location_width, width);
		super.loadFloat(location_edge, edge);
	}
	
	protected void loadBorderDistanceField(float width, float edge) {
		super.loadFloat(location_borderWidth, width);
		super.loadFloat(location_borderEdge, edge);
	}
	
	protected void loadShadowOffset(Vector2f offset) {
		super.load2DVector(location_offset, offset);
	}
	
	protected void loadOutlineColor(Vector3f color) {
		super.load3DVector(location_outlineColor, color);
	}
}