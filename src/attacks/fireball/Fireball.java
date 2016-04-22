package attacks.fireball;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.EntityType.entityType;
import main.Mift;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import textures.ModelTexture;

public class Fireball extends Entity {
	private final int speed = 8;
	private boolean isRenderable = true;
	private float timeAlive = 0;
	private final float maxTimeAlive = 300;
	private Vector3f gotoPos;
	
	private TexturedModel model;
	
	public Fireball(TexturedModel model, Vector3f curPos, Vector3f gotoPos, float scale) {
		super(model, curPos, 0, 0, 0, scale, entityType.ATK_FIREBALL);
		this.isRenderable = true;
		this.gotoPos = gotoPos;
		this.model = new TexturedModel(OBJFileLoader.loadOBJ("attackSphere"), new ModelTexture(Mift.loader.loadTexture("fireball")));
	}
	
	public void update() {
		if (timeAlive + 1 <= maxTimeAlive) {
			move();
			timeAlive++;
		} else {
			this.isRenderable = false;
		}
	}
	
	private void move() {
		Vector3f distance = new Vector3f(getPosition().x - gotoPos.x, getPosition().y - gotoPos.y, getPosition().z - gotoPos.z);
		Vector3f toTravel = new Vector3f(distance.x / (maxTimeAlive - timeAlive) * speed, distance.y / (maxTimeAlive - timeAlive) * speed, distance.z / (maxTimeAlive - timeAlive) * speed);
		
		if ((distance.x < 1 && distance.x > -1) && (distance.y < 1 && distance.y > -1) && (distance.z < 1 && distance.z > -1)) {
			timeAlive = maxTimeAlive;
		} else {
			super.increaseXPos(-toTravel.x);
			super.increaseYPos(-toTravel.y);
			super.increaseZPos(-toTravel.z);
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
		return super.getPosition();
	}

	public void setCurrentPosition(Vector3f currentPosition) {
		super.setPosition(currentPosition);
	}
	
	public void increasePosition() {
		this.setCurrentPosition(new Vector3f(super.getPosition().getX() + 1, super.getPosition().getY(), super.getPosition().getZ() + 1));
	}
}