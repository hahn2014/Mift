package entities;

import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import attacks.Attack.AttackType;
import attacks.AttackHolder;
import entities.EntityType.entityType;
import io.Logger;
import io.SettingHolder;
import main.Mift;
import models.TexturedModel;
import particles.ParticleEmitter;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import toolbox.Maths;

public class Player extends Entity {
	private static final long serialVersionUID = 2332101620227041812L;
	
	public static final float  RUN_SPEED = (!SettingHolder.get("player_ufo").getValueB() ? 16.0f : 25.0f);
	private static final float MAX_RUN_TIME = 200.0f;
	private static final float RUN_COOLDOWN = 400f;
	private static final float ATTACK_COOLDOWN = 20f;
	private static final float GRAVITY = -50;
	private static final float JUMP_POWER = 20;
	private static int		   HEALTH = 500;

	private float currentSpeed = 0f;
	private float upwardsSpeed = 0f;
	private float currentRunTime = 0f;
	private float currentRunCooldown = RUN_COOLDOWN;
	private float attackCooldownTime = 0f;
	
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
		
		if (!collideWithEntity() && !SettingHolder.get("player_ufo").getValueB()) {
			super.increasePosition(dx, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), dz);
		} else {
			if (SettingHolder.get("player_ufo").getValueB()) {
				super.increasePosition(dx, upwardsSpeed * DisplayManager.getFrameTimeSeconds(), dz);
			}
		}
		if (attackCooldownTime > 0) {
			if (SettingHolder.get("player_fire_unlimited").getValueB() == false) {
				attackCooldown();
			} else {
				attackCooldownTime = 0;
			}
		}
		
		if (!SettingHolder.get("player_ufo").getValueB()) {
			float terrainHeight = Mift.terrain.getHeightOfTerrain(x, z);
			
			if (super.getPosition().getY() < terrainHeight) {
				upwardsSpeed = 0;
				super.getPosition().setY(terrainHeight);
				isInAir = false;
			}
		}
	}
	
	public void collideWithEnemy() {
		if (SettingHolder.get("player_god").getValueB() == false) {
			Logger.debug("Took damage");
			HEALTH -= 50;
			if (HEALTH <= 0) {
				Logger.debug("Player has died!");
				Mift.setPaused(true);
				Mift.menuIndex = 3; //dead scene
				Mift.hasMadeWorld = false;
			}
		}
	}
	
	public boolean collideWithEntity() {
		for (Entity e : Mift.entities) {
			if (e.getType() != entityType.PLAYER && e.getType() != entityType.FERN) {
				if (Maths.distanceFormula3D(getPosition(), e.getPosition()) <= 5) {
					return true;
				}
			}
		}
		return false;
	}
	
	private void attackCooldown() {
		attackCooldownTime--;
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
	
	private void checkInputs() {
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
						attack();
					}
				}
			}
		}
	}
	
	public void attack() {
		if (attackCooldownTime != 0) {
			return;
		}
		
		if (attackType == AttackType.fireball) {
			Vector3f curPos = new Vector3f(getPosition().x, getPosition().y + 9, getPosition().z);
			Vector3f gotoPos = Mift.getMousePicker(false).getTerrainPoint(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2));
			
			if (gotoPos != null) {
				Mift.attackHolder.getFireballHolder().createFireball(curPos, gotoPos);
				attackCooldownTime = ATTACK_COOLDOWN;
			} else {
				Logger.error("Unable to create fireball at location. Position calulated is " + gotoPos);
			}
		} else if (attackType == AttackType.waterball) {
			Vector3f curPos = new Vector3f(getPosition().x, getPosition().y + 9, getPosition().z);
			Vector3f gotoPos = Mift.getMousePicker(false).getTerrainPoint(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2));
			
			if (gotoPos != null) {
				Mift.attackHolder.getWaterballHolder().createWaterball(curPos, gotoPos);
				attackCooldownTime = ATTACK_COOLDOWN;
			} else {
				Logger.error("Unable to create waterball at location. Position calulated is " + gotoPos);
			}
		}
	}
	
	public boolean isOverhead() {
		return isOverhead;
	}

	public void setOverhead(boolean overhead) {
		this.isOverhead = overhead;
	}

	public int getHealth() {
		return HEALTH;
	}
	
	public void resetHealth() {
		HEALTH = 500;
	}
	
	public int getSpeed() {
		return (int) currentSpeed;
	}
	
	public void setSpeed(int speed) {
		this.currentSpeed = speed;
	}
}