package entities;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import attacks.Attack.AttackType;
import attacks.AttackHolder;
import entities.EntityType.entityType;
import main.Mift;
import models.TexturedModel;
import particles.ParticleEmitter;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import terrains.Terrain;

public class Player extends Entity {

	private static final float RUN_SPEED = 15.0f;
	private static final float MAX_RUN_TIME = 200.0f;
	private static final float RUN_COOLDOWN = 400f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;

	private float currentSpeed = 0f;
	private float upwardsSpeed = 0f;
	private float currentRunTime = 0f;
	private float currentRunCooldown = RUN_COOLDOWN;

	private boolean isInAir = false;
	private boolean isOverhead = false;
	private boolean isRunning = false;
	private boolean isCrouched = false;
	private boolean isProned = false;
	private boolean isRunCooldown = false;
	
	public AttackType attackType = AttackType.fireball;
	public AttackHolder at;
	private Random random;
	private Camera camera;
	public ParticleEmitter particleEmitter;

	public Player(Loader loader, TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale, entityType.PLAYER);
		at = Mift.attackHolder;
		random = new Random();
		random.setSeed(Sys.getTime());
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("particles/star"), 1, false);
		particleEmitter = new ParticleEmitter(particleTexture, 100, 25, 0.35f, 4, 0.2f);
	}

	public void move(Terrain terrain) {
		camera = Mift.getCamera();
		checkInputs();
		if (isRunCooldown) {
			checkRunCooldown();
		}
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
	
	private void checkRunCooldown() {
		if (currentRunCooldown >= RUN_COOLDOWN) {
			this.isRunning = true;
			currentRunTime = 0f;
			this.isRunCooldown = false;
		} else {
			this.isRunCooldown = true;
			currentRunCooldown++;
		}
	}
	
	private void checkInputs() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (this.isRunning == false) {
				this.currentSpeed = RUN_SPEED; //not running
			} else {
				if (currentRunTime >= MAX_RUN_TIME) { //weve ran for the max time, we need to cool down
					this.currentSpeed = RUN_SPEED;
					this.isRunning = false;
					this.isRunCooldown = true;
					currentRunCooldown = 0f;
				} else {
					this.currentSpeed = RUN_SPEED * 2; //running
					currentRunTime++;
				}
			}
		} else if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
			this.currentSpeed = -RUN_SPEED;
		} else {
			this.currentSpeed = 0;
		}
		
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			if (!isRunning) {
				checkRunCooldown();
			}
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
		while (Keyboard.next()) {
			if (Keyboard.isKeyDown(Keyboard.KEY_C)) {
				if (Keyboard.getEventKeyState() == false) {} else {
					if (!isProned) {
						if (isCrouched) {
							isCrouched = false;
						} else {
							isCrouched = true;
						}
						isProned = false;
					} else {
						isProned = false;
						isCrouched = true;
					}
				}
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
				if (Keyboard.getEventKeyState() == false) {} else {
					if (isProned) {
						isProned = false;
						isCrouched = true;
					} else {
						isProned = true;
						isCrouched = false;
					}
				}
			}
			if (camera.isFPS()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_E)) {
					if (Keyboard.getEventKeyState() == false) {} else {
				    	//go back one attack place
						attackType = at.rotate(attackType);
				    }
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_Q)) {
					if (Keyboard.getEventKeyState() == false) {} else {
				    	//go back one attack place
						attackType = at.rotateReverse(attackType);
				    }
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_RETURN)) {
					if (Keyboard.getEventKeyState() == false) {} else {
						if (attackType == AttackType.fireball) {
							for (int i = 0; i < 400; i ++) {
								particleEmitter.generateParticles(super.getPosition());
							}
							
							//Mift.fireballHolder.createFireball(super.getPosition(), super.getPosition(), 0);
						}
					}
				}
			}
		}
	}
	
	public boolean isOverhead() {
		return isOverhead;
	}

	public void setOverhead(boolean overhead) {
		this.isOverhead = overhead;
	}
}