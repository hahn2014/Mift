package attacks.fireball;

import org.lwjgl.util.vector.Vector3f;

import main.Mift;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import textures.ModelTexture;

public class Fireball {
	private Vector3f rotation = new Vector3f(0, 0, 0);
	private Vector3f currentPosition;
	private Vector3f gotoPosition;
	private Vector3f goingTo;
	private final float speed = 2.0f;
	private boolean isRenderable = true;
	private float timeAlive = 0;
	private final float maxTimeAlive = 100;
	
	private TexturedModel model;
	
	public Fireball(Vector3f pos) {
		this.isRenderable = true;
		this.currentPosition = pos;
		this.model = new TexturedModel(OBJFileLoader.loadOBJ("sphere", Mift.loader),
				new ModelTexture(Mift.loader.loadTexture("sphere")));
	}
	
	public void update() {
		if (timeAlive + 1 <= maxTimeAlive) {
			this.increasePosition();
			timeAlive++;
		} else {
			//lived long enough, time to die ball
			this.isRenderable = false;
			System.out.println("fireball has died");
		}
	}
	
	public boolean isRenderable() {
		return isRenderable;
	}

	public void setRenderable(boolean isRenderable) {
		this.isRenderable = isRenderable;
	}

	public TexturedModel getTexturedModel() {
		return model;
	}

	public Vector3f getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(Vector3f currentPosition) {
		this.currentPosition = currentPosition;
	}
	
	public void increasePosition() {
		this.setCurrentPosition(new Vector3f(this.currentPosition.getX() + 1, this.currentPosition.getY(), this.currentPosition.getZ() + 1));
	}

	public Vector3f getGotoPosition() {
		return gotoPosition;
	}

	public void setGotoPosition(Vector3f gotoPosition) {
		this.gotoPosition = gotoPosition;
	}

	public Vector3f getGoingTo() {
		return goingTo;
	}

	public void setGoingTo(Vector3f goingTo) {
		this.goingTo = goingTo;
	}

	public float getSpeed() {
		return speed;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}
	
	public float getRotX() {
		return rotation.getX();
	}
	
	public float getRotY() {
		return rotation.getY();
	}
	
	public float getRotZ() {
		return rotation.getZ();
	}
}