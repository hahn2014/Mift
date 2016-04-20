package attacks.fireball;

import org.lwjgl.util.vector.Vector3f;

import entities.Entity;
import entities.EntityType.entityType;
import entities.MoveType.move_factor;
import main.Mift;
import models.TexturedModel;
import objConverter.OBJFileLoader;
import textures.ModelTexture;

public class Fireball extends Entity {
	private Vector3f gotoPosition;
	private final float speed = 2.0f;
	private boolean isRenderable = true;
	private float timeAlive = 0;
	private final float maxTimeAlive = 600;
	
	private TexturedModel model;
	
	public Fireball(TexturedModel model, Vector3f position, Vector3f gotoPosition, Vector3f rotation, float scale, move_factor move) {
		super(model, position, rotation.getX(), rotation.getY(), rotation.getZ(), scale, entityType.ENEMY);
		this.isRenderable = true;
		this.gotoPosition = gotoPosition;
		this.model = new TexturedModel(OBJFileLoader.loadOBJ("attackSphere"), new ModelTexture(Mift.loader.loadTexture("fireball")));
	}
	
	public void update() {
		if (timeAlive + 1 <= maxTimeAlive) {
			Vector3f pos = new Vector3f(super.getPosition().x, super.getPosition().y, super.getPosition().z);
			moveTowardsPoint(pos, gotoPosition, (maxTimeAlive / timeAlive));
			timeAlive++;
		} else {
			this.isRenderable = false;
		}
	}
	
	private void moveTowardsPoint(Vector3f curPos, Vector3f endPos, float time) {
		float xDist = endPos.x - curPos.x;
		float yDist = endPos.y - curPos.y;
		float zDist = endPos.z - curPos.z;

		float xMove = xDist / time;
		float yMove = yDist / time;
		float zMove = zDist / time;
		
		super.increasePosition(xMove, yMove, zMove);
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

	public Vector3f getGotoPosition() {
		return gotoPosition;
	}

	public void setGotoPosition(Vector3f gotoPosition) {
		this.gotoPosition = gotoPosition;
	}

	public float getSpeed() {
		return speed;
	}
}