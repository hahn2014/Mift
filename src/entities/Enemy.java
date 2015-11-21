package entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import models.TexturedModel;
import paths.Path;
import paths.PathCreator;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Maths;

public class Enemy extends Entity {
	private static float RUN_SPEED = 20.0f;
	
	public static enum move_factor {
		MOVE_TOWARDS,
		FACE_TOWARDS,
		FACE_AWAY,
		MOVE_CIRCLES,
		FOLLOW_PATH
	}
	private move_factor _move_factor = move_factor.FACE_TOWARDS;
	private Terrain terrain;
	private PathCreator pathCreator = new PathCreator();
	private Path path1;
	
	public Enemy(TexturedModel model, Vector3f position, Vector3f rotation, float scale, move_factor move, Terrain terrain) {
		super(model, position, rotation.getX(), rotation.getY(), rotation.getZ(), scale);
		_move_factor = move;
		this.terrain = terrain;
		if (_move_factor == move_factor.FOLLOW_PATH) {
			path1 = pathCreator.createRandomPath(Sys.getTime());
		} else {
			path1 = null;
		}
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
		} else if (_move_factor == move_factor.FOLLOW_PATH) {
			followPath(path1);
		}
	}
	
	private void moveTowardsPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, player));
		
		float speed = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void faceTowardsPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, player));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void faceAwayPlayer(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPointNegative(this, player));
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
	
	private void moveInCircles(float radius) {
		float increaseAngle = (360 / radius) / 5;
		super.increaseRotation(0, increaseAngle, 0);
		
		float speed = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
		float dx = (float) 	(speed * Math.sin(Math.toRadians(super.getRotY())));
		float dz = (float) 	(speed * Math.cos(Math.toRadians(super.getRotY())));
		super.increasePosition(dx, 0, dz);
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight + 0.1f;
	}
	
	private void followPath(Path path) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, path1.getPoint(path1.getCurrentPoint())));
		
		float deltaMove = (RUN_SPEED) * DisplayManager.getFrameTimeSeconds();
		
		if (this.getPosition().getX() < path1.getX()) {
			super.increasePosition(deltaMove, 0, 0);
		} else if (this.getPosition().getZ() < path1.getZ()) {
			super.increasePosition(0, 0, deltaMove);
		} else if (this.getPosition().getX() >= path1.getX() && this.getPosition().getZ() >= path1.getZ()) {
			if (path1.increaseCurrentPoint() == false) {
				System.out.println("end of path " + path1.getID() + " :P");
			}
		}
		
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		super.getPosition().y = terrainHeight;
	}
}