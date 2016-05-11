package guis;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Matrix4f;
import shaders.ShaderProgram;

import java.nio.FloatBuffer;
import java.util.Arrays;
import java.util.List;

public class GuiDrawer {
	public static final String TEXTURE_FIELD = "Texture";
	public static final String PROJ_VIEW_FIELD = "ProjView";

	public static final String COLOR_FIELD = "Color";
	public static final String POSITION_FIELD = "Position";

	public static final String DEFAULT_VERT_SHADER = "";
	public static final String DEFAULT_FRAG_SHADER = "";

	public static final List<VertexAttrib> ATTRIBUTES =
			Arrays.asList(
					new VertexAttrib(0, POSITION_FIELD, 2),
					new VertexAttrib(1, COLOR_FIELD, 4)
			);

	static ShaderProgram defaultShader;

	protected FloatBuffer buffer;
	protected Matrix4f projMatrix = new Matrix4f();
	protected Matrix4f viewMatrix = new Matrix4f();
	protected Matrix4f transpositionPool = new Matrix4f();
	private Matrix4f projViewMatrix = new Matrix4f();

	protected VertexData data;

	private int inx;
	private int maxIndex;

	private Color color = new Color();

	public static ShaderProgram getDefaultShader() {
		return defaultShader == null ? (defaultShader = new Sha)
	}
}
