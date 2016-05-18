package guis_old;

import org.lwjgl.util.vector.Vector2f;

public class GuiComponent {	
	Vector2f pos; // Measured from the top left
	Vector2f dimensions; // Measured in pixels. x is the width, y is the height
	Vector2f scale; // 1: Normal sized, 0: nothing
	float rotation; // Measured in degrees. 0 is to the right
	int textureIndex;
	
	public GuiComponent(Vector2f pos, Vector2f dim) {
		this.pos = pos;
		this.dimensions = dim;
		rotation = 0;
		scale = new Vector2f(1.0f, 1.0f);
	}
	
	public GuiComponent(Vector2f pos, Vector2f dim, Vector2f scale) {
		this.pos = pos;
		this.dimensions = dim;
		this.scale = scale;
	}
	
	public GuiComponent(Vector2f pos, Vector2f dim, float rot) {
		this.pos = pos;
		this.dimensions = dim;
		this.rotation = rot;
		scale = new Vector2f(1.0f, 1.0f);
	}
	
	/**
	 * Update this component. Usually done before a redraw
	 */
	public void update() {}
	
	/**
	 * Get the position of this component. This is only the (x, y) coords on the screen, measured from the top-left
	 * @return Position of this component
	 */
	public Vector2f getPosition() {
		return pos;
	}
	
	/**
	 * Set the position of this component. This is measured from the top-left
	 * @param x X position of the component
	 * @param y Y coord
	 * The coords can be offscreen
	 */
	public void setPosition(float x, float y) {
		pos.x = x;
		pos.y = y;
	}
	
	/**
	 * 
	 * @param pos
	 */
	public void setPosition(Vector2f pos) {
		this.pos = pos;
	}
	

	/**
	 * 
	 * @return
	 */
	public Vector2f getDimensions() {
		return dimensions;
	}

	/**
	 * 
	 * @param dimensions
	 */
	public void setDimensions(Vector2f dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * 
	 * @return
	 */
	public float getRotation() {
		return rotation;
	}

	/**
	 * 
	 * @param rotation
	 */
	public void setRotation(float rotation) {
		this.rotation = rotation;
	}

	/**
	 * 
	 * @return
	 */
	public Vector2f getScale() {
		return scale;
	}

	/**
	 * 
	 * @param scale
	 */
	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	/**
	 * 
	 * @return
	 */
	public int getTextureIndex() {
		return textureIndex;
	}

	/**
	 * 
	 * @param textureIndex
	 */
	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}
}
