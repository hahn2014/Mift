package entities;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import entities.MoveType.move_factor;
import models.TexturedModel;
import paths.Path;
import paths.PathCreator;
import renderEngine.DisplayManager;
import terrains.Terrain;
import toolbox.Maths;

public class Enemy extends Entity {
	private static float RUN_SPEED = 15.0f;
	
	private move_factor _move_factor = move_factor.FACE_TOWARDS;
	private Terrain terrain;
	private PathCreator pathCreator = new PathCreator();
	private Path path1;
	private int id;
	
	public Enemy(TexturedModel model, Vector3f position, Vector3f rotation, float scale, move_factor move, Terrain terrain, int id) {
		super(model, position, rotation.getX(), rotation.getY(), rotation.getZ(), scale);
		_move_factor = move;
		this.terrain = terrain;
		this.id = id;
		if (_move_factor == move_factor.FOLLOW_PATH) {
			path1 = pathCreator.createRandomPath(Sys.getTime(), this);
		} else {
			path1 = null;
		}
	}
	
	public int getID() {
		return id;
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
		} else if (_move_factor == move_factor.FOLLOW_NOT_LOOKING) {
			followWhenNotLooking(player);
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
	
	private void followWhenNotLooking(Player player) {
		//rotate enemy to face player menacingly
		super.setRotation(Maths.getRotationFromPoint(this, player));
		
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
		super.setRotation(Maths.getRotationFromPoint(this, path1.getCurrentPoint()));
		
		Vector3f goingTo = getGoingToVec(new Vector3f(path1.getCurrentPoint().getPosition3f()), this.getPosition());
		goingTo.normalise();
		float deltaMove = (float)2 * (DisplayManager.getFrameTimeSeconds() * 1000);
		float terrainHeight = terrain.getHeightOfTerrain(super.getPosition().x, super.getPosition().z);
		Vector3f newpos = new Vector3f(goingTo.x * deltaMove, terrainHeight, goingTo.z * deltaMove);
		System.out.println("DEBUG: " + super.getPosition().toString() + path1.getCurrentPoint().getPosition3f().toString()
				+ newpos.toString() + "[" + (goingTo.x * deltaMove) + " " + (goingTo.z * deltaMove) + "]");
		
		if (Maths.distanceFormula3D(newpos, path1.getCurrentPoint().getPosition3f()) < RUN_SPEED * deltaMove) {
			path1.increaseCurrentPoint();
		} else {
			System.out.println("Moving " + getID() + " from " + super.getPosition().toString() + " to " + path1.getCurrentPoint().getPosition3f().toString());
			super.setPosition(newpos);
		}
		try {
			Thread.sleep(1000);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public Vector3f getGoingToVec(Vector3f gotoPosition, Vector3f currentPosition) {
		return new Vector3f(gotoPosition.getX() - currentPosition.getX(), gotoPosition.getY() - currentPosition.getY(), gotoPosition.getZ() - currentPosition.getZ());
	}
}