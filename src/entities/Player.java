package entities;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import com.thalmic.myo.enums.PoseType;

import attacks.Attack.AttackType;
import attacks.AttackHolder;
import entities.EntityType.entityType;
import io.Logger;
import io.SettingHolder;
import main.Mift;
import models.TexturedModel;
import myo.MoveMyo;
import myo.MyoManager;
import particles.ParticleEmitter;
import particles.ParticleTexture;
import renderEngine.DisplayManager;

public class Player extends Entity {

	private static final float RUN_SPEED = (SettingHolder.get("player_ufo").getValueB() ? 20.0f : 50.0f);
	private static final float MAX_RUN_TIME = 200.0f;
	private static final float RUN_COOLDOWN = 400f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;

	private float currentSpeed = 0f;
	private float upwardsSpeed = 0f;
	private float currentRunTime = 0f;
	private float currentRunCooldown = RUN_COOLDOWN;
	
	private float x;
	private float z;

	private boolean isInAir = false;
	private boolean isOverhead = false;
	private boolean isRunning = false;
	private boolean isRunCooldown = false;
	
	public AttackType attackType = AttackType.fireball;
	public AttackHolder at;
	private Random random;
	private Camera camera;
	private OverheadCamera overheadCamera;
	public ParticleEmitter particleEmitter;

	public Player(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
		super(model, position, rotX, rotY, rotZ, scale, entityType.PLAYER);
		at = Mift.attackHolder;
		random = new Random();
		random.setSeed(Sys.getTime());
		ParticleTexture particleTexture = new ParticleTexture(Mift.loader.loadTexture("particles/star"), 1, false);
		particleEmitter = new ParticleEmitter(particleTexture, 100, 25, 0.35f, 4, 0.2f);
	}

	public void move() {
		camera = Mift.camera;
		overheadCamera = Mift.overheadCamera;
		this.x = super.getPosition().getX();
		this.z = super.getPosition().getZ();
		checkInputs();
		if (isRunCooldown) {
			checkRunCooldown();
		}
		float distance = currentSpeed * DisplayManager.getFrameTimeSeconds();
		float dx = (float) (distance * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) (distance * Math.cos(Math.toRadians(super.getRotY())));
		if (!SettingHolder.get("player_ufo").getValueB()) {
			upwardsSpeed += GRAVITY * DisplayManager.getFrameTimeSeconds() / 1000;
		}
		super.increasePosition(dx, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), dz);
		
		if (!SettingHolder.get("player_ufo").getValueB()) {
			float terrainHeight = Mift.terrain.getHeightOfTerrain(x, z);
			
			if (super.getPosition().getY() < terrainHeight) {
				upwardsSpeed = 0;
				super.getPosition().setY(terrainHeight);
				isInAir = false;
			}
		}
	}
	
	private void jump() {
		if (!SettingHolder.get("player_ufo").getValueB()) {
			if (!isInAir) {
				this.upwardsSpeed = JUMP_POWER;
				isInAir = true;
			}
		} else {
			super.increasePosition(0, 10 * DisplayManager.getFrameTimeSeconds(), 0);
		}
	}
	
	private void checkRunCooldown() {
		if (SettingHolder.get("player_sprint_unlimited").getValueB() == false) {
			if (currentRunCooldown >= RUN_COOLDOWN) {
				this.isRunning = true;
				currentRunTime = 0f;
				this.isRunCooldown = false;
			} else {
				this.isRunCooldown = true;
				currentRunCooldown++;
			}
		} else {
			this.isRunning = true;
			currentRunTime = 0f;
		}
	}
	
	/**
	 * Set the current speed measured in Bryce Units
	 * @param speed Speed
	 */
	public void setSpeed(int speed) {
		this.currentSpeed = speed;
	}
	
	public void updateMyo() {
		MoveMyo myo = MyoManager.getMoveMyo();
		if (myo.getPose() == PoseType.FIST) {
			isRunning = !isRunning;
			if (isRunning) {
				currentSpeed = RUN_SPEED;
			} else {
				currentSpeed = 0;
			}
		}
	}
	
	private void checkInputs() {
		if (SettingHolder.get("cp_myo_enabled").getValueB()) {
			updateMyo();
			return;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
			if (this.isRunning == false) {
				this.currentSpeed = RUN_SPEED; //not running
			} else {
				if (currentRunTime >= MAX_RUN_TIME) { //we've ran for the max time, we need to cool down
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
		if (Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
			if (SettingHolder.get("player_ufo").getValueB()) {
				super.increasePosition(0, -10 * DisplayManager.getFrameTimeSeconds(), 0);
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
			if (Keyboard.getEventKeyState() == false) { //release
				if (isOverhead == true) {
					if (Keyboard.getEventKey() == Keyboard.KEY_1) {
				    	//go back one model place
						overheadCamera.placerType = overheadCamera.getETH().rotateReverse(overheadCamera.placerType);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_2) {
						//go forward one model place
				    	overheadCamera.placerType = overheadCamera.getETH().rotate(overheadCamera.placerType);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_3) {
						//go back one move type place
				    	overheadCamera.move_type = overheadCamera.getMTH().rotateReverse(overheadCamera.move_type);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_4) {
						//go forward one move type place
				    	overheadCamera.move_type = overheadCamera.getMTH().rotate(overheadCamera.move_type);
					}
				}
				if (camera.isFPS()) {
					if (Keyboard.getEventKey() == Keyboard.KEY_E) {
				    	//go back one attack place
						attackType = at.rotate(attackType);
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_Q) {
				    	//go back one attack place
						attackType = at.rotateReverse(attackType);
					}
					if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
						if (attackType == AttackType.fireball) {
							Mift.fireballHolder.createFireball(super.getPosition());
							Logger.debug("Firing a fireball!");
						} else if (attackType == AttackType.waterball) {
							Mift.waterballHolder.createWaterball(super.getPosition());
							Logger.debug("Firing a waterball!");
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