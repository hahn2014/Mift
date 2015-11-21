package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import main.Mift;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 15.0f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;

	private float currentSpeed = 0f;
	private float upwardsSpeed = 0f;

	private boolean isInAir = false;
	private boolean isOverhead = false;
	
	private Camera camera;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);
	}

	public void move(Terrain terrain) {
		camera = Mift.getCamera();
		checkInputs();
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(dx, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), dz);

		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);

		if (super.getPosition().y < terrainHeight) {
			upwardsSpeed = 0;
			super.getPosition().y = terrainHeight;
			isInAir = false;
		}
	}

	private void jump() {
		if (!isInAir) {
			this.upwardsSpeed = JUMP_POWER;
			isInAir = true;
		}
	}

	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				this.currentSpeed = RUN_SPEED;
			} else {
				this.currentSpeed = RUN_SPEED * 2;
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
			if (isOverhead == false) {
				//strafe
				float distance = (RUN_SPEED * DisplayManager.getFrameTimeSeconds());
				float yaw = camera.getYaw();
				super.increasePosition(-(distance * (float)Math.sin(Math.toRadians(yaw - 90))), 0, (distance * (float)Math.cos(Math.toRadians(yaw - 90))));
			} else {
				//rotate
				super.increaseRotation(0, -1.2f, 0);
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			if (isOverhead == false) {
				//strafe
				float distance = (RUN_SPEED * DisplayManager.getFrameTimeSeconds());
				float yaw = camera.getYaw();
				super.increasePosition(-(distance * (float)Math.sin(Math.toRadians(yaw + 90))), 0, (distance * (float)Math.cos(Math.toRadians(yaw + 90))));
			} else {
				//rotate
				super.increaseRotation(0, 1.2f, 0);
			}
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
	}
	
	public boolean isOverhead() {
		return isOverhead;
	}

	public void setOverhead(boolean overhead) {
		this.isOverhead = overhead;
	}
}