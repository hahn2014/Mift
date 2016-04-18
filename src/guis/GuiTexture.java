package guis;

import org.lwjgl.util.vector.Vector2f;

public class GuiTexture {

	private int texture;
	private Vector2f position;
	private Vector2f scale;
	private float rotation;

	public GuiTexture(int texture, Vector2f position, Vector2f scale) {
		this.texture = texture;
		this.position = position;
		this.scale = scale;
		this.rotation = 0;
	}

	public int getTexture() {
		return texture;
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
		this.texture = texture;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}
}