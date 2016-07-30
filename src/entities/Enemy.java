package entities;

import org.lwjgl.util.vector.Vector3f;

import entities.EntityType.entityType;
import entities.MoveType.move_factor;
import io.SettingHolder;
import main.Mift;
import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Maths;

public class Enemy extends Entity {
	private static final long serialVersionUID = -3799383640463312689L;

	private static float RUN_SPEED = 10.0f;
	
	private static int attackCooldown = 100;
	private static int cooldownTime = 0;
	
	private int health = 0;
	private move_factor _move_factor = move_factor.FACE_TOWARDS;
	private Terrain terrain;
	private int id;
	
	public Enemy(TexturedModel model, Vector3f position, Vector3f rotation, float scale, move_factor move, int id, int health) {
		super(model, position, rotation.getX(), rotation.getY(), rotation.getZ(), scale, entityType.ENEMY);
		_move_factor = move;
		this.health = health;
		this.terrain = Mift.terrain;
		this.id = id;
	}
	
	public Enemy() {}
	
	public int getID() {
		return id;
	}
	
	public void move(Player player) {
		if (_move_factor == move_factor.MOVE_TOWARDS) {
			if (SettingHolder.get("player_undetected").getValueB() == false) {
				moveTowardsPlayer(player);
			}
		} else if (_move_factor == move_factor.FACE_TOWARDS) {
			if (SettingHolder.get("player_undetected").getValueB() == false) {
				faceTowardsPlayer(player);
			}
		} else if (_move_factor == move_factor.FACE_AWAY) {
			if (SettingHolder.get("player_undetected").getValueB() == false) {
				faceAwayPlayer(player);
			}
		} else if (_move_factor == move_factor.MOVE_CIRCLES) {
			moveInCircles(60);
		} else if (_move_factor == move_factor.FOLLOW_NOT_LOOKING) {
			if (SettingHolder.get("player_undetected").getValueB() == false) {
				followWhenNotLooking(player);
			}
		} else if (_move_factor == move_factor.MOVE_TOWARDS_WHEN_CLOSE) {
			if (SettingHolder.get("player_undetected").getValueB() == false) {
				moveTowardsPlayerWhenClose(player);
			}
		}
		if (Maths.distanceFormula3D(getPosition(), player.getPosition()) < 15) {
			if (cooldownTime == 0) {
				player.collideWithEnemy();
				cooldownTime = 1;
			}					
		}
		if (cooldownTime > 0) {
			if (cooldownTime < attackCooldown) {
				cooldownTime++;
			} else {
				cooldownTime = 0;
			}
		}
	}
	
	public void attackCollision(int damage) {
		health -= damage;
		if (health <= 0) {
			Mift.enemies.remove(this);
		}
	}
	
	private void moveTowardsPlayer(Player player) {  //fixed
		//rotate enemy to face player menacingly
		Vector3f rot = Maths.getRotationFromPoint(this, player);
		super.setRotX(rot.x);
		super.setRotY(rot.y);
		super.setRotZ(rot.z);
		
		float speed = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void moveTowardsPlayerWhenClose(Player player) {  //fixed
		double distanceToPlayer = Maths.distanceFormula3D(
				new Vector3f(player.getPosition().x, player.getPosition().y, player.getPosition().z),
				new Vector3f(getPosition().x, getPosition().y, getPosition().z));
		if (distanceToPlayer <= 300 && distanceToPlayer > 100) {
			faceTowardsPlayer(player);
		} else if (distanceToPlayer <= 100) {
			moveTowardsPlayer(player);
		}
	}
	
	private void followWhenNotLooking(Player player) {  //fixed
		//rotate enemy to face player menacingly
		Vector3f rot = Maths.getRotationFromPoint(this, player);
		super.setRotX(rot.x);
		super.setRotY(rot.y);
		super.setRotZ(rot.z);
		
		//check if player rotation is away from entity
		if (Maths.isWithinScreen(player.getRotation(), this.getRotation())) {
			float speed = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
			float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
			float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
			super.increasePosition(dx, 0, dz);
		}
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void faceTowardsPlayer(Player player) {  //fixed
		//rotate enemy to face player menacingly
		Vector3f rot = Maths.getRotationFromPoint(this, player);
		super.setRotX(rot.x);
		super.setRotY(rot.y);
		super.setRotZ(rot.z);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void faceAwayPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPointNegative(this, player));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void moveInCircles(float radius) {  //fixed
		float increaseAngle = (360 / radius) / 5;
		super.setRotY(getRotY() + increaseAngle);
		
		float speed = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight + 0.1f;
	}
	
	public Vector3f getGoingToVec(Vector3f gotoPosition, Vector3f currentPosition) {
		return new Vector3f(gotoPosition.getX() - currentPosition.getX(), gotoPosition.getY() - currentPosition.getY(), gotoPosition.getZ() - currentPosition.getZ());
	}
}