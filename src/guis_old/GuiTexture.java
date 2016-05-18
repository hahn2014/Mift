package guis_old;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {
	private int textureIndex;
	private Vector2f position; // Measured from the top-left
	private Vector2f scale;
	private float rotation; // 0 is right, measured in degrees

	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.textureIndex = texture;
		this.position = position;
		this.scale = scale;
		this.rotation = 0;
	}

	public int getTexture() {
		return textureIndex;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}
	
	public float getRotation() {
		return rotation;
	}
	
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}
	
	public void setTexture(int texture) {
		this.textureIndex = texture;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
}