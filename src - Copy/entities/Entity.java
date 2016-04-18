package entities;

import org.lwjgl.util.vector.Vector3f;

import entities.EntityType.entityType;
import models.TexturedModel;

public class Entity {

	private TexturedModel model;
	private Vector3f position;
	private float rotX, rotY, rotZ;
	private float scale;
	private entityType type = entityType.DEFAULT;
	private boolean renderable = true;
	private static final float timeDivider = 1000f;

	private int textureIndex = 0;

	public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale, entityType type) {
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.type = type;
	}

	public Entity(TexturedModel model, int index, Vector3f position, float rotX, float rotY, float rotZ, float scale, entityType type) {
		this.textureIndex = index;
		this.model = model;
		this.position = position;
		this.rotX = rotX;
		this.rotY = rotY;
		this.rotZ = rotZ;
		this.scale = scale;
		this.type = type;
	}

	public Entity() {}

	public float getTextureXOffset() {
		int column = textureIndex % model.getTexture().getNumberOfRows();
		return (float) column / (float) model.getTexture().getNumberOfRows();
	}

	public float getTextureYOffset() {
		int row = textureIndex / model.getTexture().getNumberOfRows();
		return (float) row / (float) model.getTexture().getNumberOfRows();
	}

	public void increasePosition(float dx, float dy, float dz) {
		this.position.x += dx / timeDivider;
		this.position.y += dy / timeDivider;
		this.position.z += dz / timeDivider;
	}

	public void increaseRotation(float dx, float dy, float dz) {
		this.rotX += dx / timeDivider;
		this.rotY += dy / timeDivider;
		this.rotZ += dz / timeDivider;
	}
	
	public void increaseRotation(Vector3f rotation) {
		increaseRotation(rotation.x, rotation.y, rotation.z);
	}

	public TexturedModel getModel() {
		return model;
	}

	public void setRenderable(boolean r) {
		this.renderable = r;
	}
	
	public boolean isRenderable() {
		return renderable;
	}
	
	public void setModel(TexturedModel model) {
		this.model = model;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(Vector3f position) {
		this.position = position;
	}

	public float getRotX() {
		return rotX;
	}

	public void setRotX(float rotX) {
		this.rotX = rotX;
	}

	public float getRotY() {
		return rotY;
	}

	public void setRotY(float rotY) {
		while (rotY < 0 || rotY > 360) {
			if (rotY < 0) {
				rotY = 359 + rotY;
			} else if (rotY > 360) {
				rotY = rotY - 360;
			}
		}
		this.rotY = rotY;
	}

	public float getRotZ() {
		return rotZ;
	}

	public void setRotZ(float rotZ) {
		this.rotZ = rotZ;
	}
	
	public void setRotation(Vector3f direction) {
		this.rotX = direction.getX() / timeDivider;
		this.rotY = direction.getY()  / timeDivider;
		this.rotZ = direction.getZ()  / timeDivider;
	}
	
	public Vector3f getRotation() {
		return new Vector3f(this.rotX, this.rotY, this.rotZ);
	}
	
	public int getRotationDebug() {
		return (int)this.rotY;
	}
	
	public void setXPos(float x) {
		this.position.x = x;
	}
	
	public void setYPos(float y) {
		this.position.y = y;
	}
	
	public void setZPos(float z) {
		this.position.z = z;
	}
	
	public String getPositionDebug() {
		return "[" + (int)this.getPosition().getX() + ", " + (int)this.getPosition().getY() + ", " + (int)this.getPosition().getZ() + "]";
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}
	
	public boolean rayOverEntity(Vector3f ray) {
		float buffer = 3 * scale;
		Vector3f bufferStart = new Vector3f(this.getPosition().getX() - buffer, this.getPosition().getY() - buffer, this.getPosition().getZ() - buffer);
		Vector3f bufferEnd = new Vector3f(this.getPosition().getX() + buffer, this.getPosition().getY() + buffer, this.getPosition().getZ() + buffer);
		
		if (ray.getX() >= bufferStart.getX() && ray.getX() <= bufferEnd.getX()) {
			if (ray.getY() >= bufferStart.getY() && ray.getY() <= bufferEnd.getY()) {
				if (ray.getZ() >= bufferStart.getZ() && ray.getZ() <= bufferEnd.getZ()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public entityType getType() {
		return type;
	}
}