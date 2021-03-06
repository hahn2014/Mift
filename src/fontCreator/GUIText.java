package fontCreator;

import java.io.File;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import main.Mift;

public class GUIText {
	
	public static enum ALIGNMENT {
		LEFT,
		CENTER,
		RIGHT
	}

	private String textString;
	private float fontSize;

	private int textMeshVao;
	private int vertexCount;
	private Vector3f color = new Vector3f(0f, 0f, 0f);

	private Vector2f position;
	private float lineMaxSize;
	private int numberOfLines;
	
	private boolean renderable = true;

	private static FontType font;

	private ALIGNMENT alignment = ALIGNMENT.CENTER;

	public GUIText() {
		this.textString = "";
		this.fontSize = 1.0f;
		font = new FontType(Mift.loader.loadFontTexture("sans"), new File("res/fonts/sans.fnt"));
		this.position = new Vector2f(0.0f, 0.0f);
		this.lineMaxSize = 1.0f;
		this.alignment = ALIGNMENT.CENTER;
		prepText();
	}
	
	public GUIText(String text, float fontSize, FontType Font, Vector2f position, float maxLineLength,
			ALIGNMENT align) {
		this.textString = text;
		this.fontSize = fontSize;
		font = Font;
		setPosition(position);
		this.lineMaxSize = maxLineLength;
		this.alignment = align;
		prepText();
	}
	
	public boolean isRenderable() {
		return renderable;
	}
	
	public void setRenderable(boolean renderable) {
		this.renderable = renderable;
	}
	
	public void setPosition(Vector2f pos) {
		this.position = pos;
	}

	public FontType getFont() {
		return font;
	}
	
	public void setColor(float r, float g, float b) {
		color.set(r, g, b);
	}
	
	public void setColor(int r, int g, int b) {
		if (r > 255) {
			r = 255;
		} else if (r < 0) {
			r = 0;
		}
		if (g > 255) {
			g = 255;
		} else if (g < 0) {
			g = 0;
		}
		if (b > 255) {
			b = 255;
		} else if (b < 0) {
			b = 0;
		}
		color.set((float)r / 255.0f, (float)g / 255.0f, (float)b / 255.0f);
	}
	
	private void prepText() {
		TextData data = font.loadText(this);
		int vao = Mift.loader.loadToVAO(data.getVertexPositions(), data.getTextureCoords());
		setMeshInfo(vao, data.getVertexCount());
	}
	
	public Vector3f getColor() {
		return color;
	}
	
	public void setText(String text) {
		this.textString = text;
		prepText();
	}
	
	public String getText() {
		return this.textString;
	}

	public int getNumberOfLines() {
		return numberOfLines;
	}

	public Vector2f getPosition() {
		return position;
	}

	public int getMesh() {
		return textMeshVao;
	}

	public void setMeshInfo(int vao, int verticesCount) {
		this.textMeshVao = vao;
		this.vertexCount = verticesCount;
	}

	public int getVertexCount() {
		return this.vertexCount;
	}

	public float getFontSize() {
		return fontSize;
	}

	protected void setNumberOfLines(int number) {
		this.numberOfLines = number;
	}
	
	protected ALIGNMENT getAlignment() {
		return alignment;
	}
	
	protected float getMaxLineSize() {
		return lineMaxSize;
	}

	protected String getTextString() {
		return textString;
	}
}