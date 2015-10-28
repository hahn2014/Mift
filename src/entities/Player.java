package entities;

import models.TexturedModel;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import engineTester.MainGameLoop;
import renderEngine.DisplayManager;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 50.0f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;

	private float currentSpeed = 0f;
	private float upwardsSpeed = 0f;

	private boolean isInAir = false;
	private Camera camera;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale);

	}

	public void move(Terrain terrain) {
		camera = MainGameLoop.getCamera();
		checkInputs();
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(distance * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds();
		super.increasePosition(0, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), 0);

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
			float distance = (RUN_SPEED * DisplayManager.getFrameTimeSeconds());
			float yaw = camera.getYaw();
			super.increasePosition(-(distance * (float)Math.sin(Math.toRadians(yaw - 90))), 0, (distance * (float)Math.cos(Math.toRadians(yaw - 90))));
		} else if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
			float distance = (RUN_SPEED * DisplayManager.getFrameTimeSeconds());
			float yaw = camera.getYaw();
			super.increasePosition(-(distance * (float)Math.sin(Math.toRadians(yaw + 90))), 0, (distance * (float)Math.cos(Math.toRadians(yaw + 90))));
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
			jump();
		}
		if (Keyboard.getEventKey() == Keyboard.KEY_P) {
			System.out.println("hello");
			if (Keyboard.getEventKeyState()) {
				if (Camera.getPOV() == Camera.POV.FIRST_PERSON_POV) {
					Camera.setPOV(Camera.POV.THIRD_PERSON_POV);
				} else {
					Camera.setPOV(Camera.POV.FIRST_PERSON_POV);
				}
			}
		}
	}
}