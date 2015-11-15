package entities;

import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Maths;

public class Enemy extends Entity {
	private static float RUN_SPEED = 15.0f;
	
	public static enum move_factor {
		MOVE_TOWARDS,
		FACE_TOWARDS,
		FACE_AWAY,
		MOVE_CIRCLES
	}
	private move_factor _move_factor = move_factor.FACE_TOWARDS;
	private Terrain terrain;
	
	public Enemy(TexturedModel model, Vector3f position, Vector3f rotation, float scale, float runSpeed, move_factor move, Terrain terrain) {
		super(model, position, rotation.getX(), rotation.getY(), rotation.getZ(), scale);
		RUN_SPEED = runSpeed;
		_move_factor = move;
		this.terrain = terrain;
	}
	
	public void move(Player player) {
		if (_move_factor == move_factor.MOVE_TOWARDS) {
			moveTowardsPlayer(player);
		} else if (_move_factor == move_factor.FACE_TOWARDS) {
			faceTowardsPlayer(player);
		} else if (_move_factor == move_factor.FACE_AWAY) {
			faceAwayPlayer(player);
		} else if (_move_factor == move_factor.MOVE_CIRCLES) {
			moveInCircles(60);
		}
	}
	
	private void moveTowardsPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, player));
		
		float speed = RUN_SPEED * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);

		if (super.getPosition().y < terrainHeight) {
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void faceTowardsPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, player));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);

		if (super.getPosition().y < terrainHeight) {
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void faceAwayPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPointNegative(this, player));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);

		if (super.getPosition().y < terrainHeight) {
			super.getPosition().y = terrainHeight;
		}
	}
	
	private void moveInCircles(float radius) {
		float increaseAngle = (360 / radius) / 5;
		super.increaseRotation(0, increaseAngle, 0);
		
		float speed = RUN_SPEED * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight + 0.1f;
	}
}