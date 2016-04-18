package toolbox;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Enemy;
import entities.OverheadCamera;
import entities.Player;
import toolbox.Point;

public class Maths {

	public static Matrix4f createTransformationMatrix(Vector2f translation, Vector2f scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.scale(new Vector3f(scale.x, scale.y, 1f), matrix, matrix);
		return matrix;
	}

	public static Matrix4f createTransformationMatrix(Vector3f translation, float rx, float ry, float rz, float scale) {
		Matrix4f matrix = new Matrix4f();
		matrix.setIdentity();
		Matrix4f.translate(translation, matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1, 0, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0, 1, 0), matrix, matrix);
		Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0, 0, 1), matrix, matrix);
		Matrix4f.scale(new Vector3f(scale, scale, scale), matrix, matrix);
		return matrix;
	}

	public static float barryCentric(Vector3f p1, Vector3f p2, Vector3f p3, Vector2f pos) {
		float det = (p2.z - p3.z) * (p1.x - p3.x) + (p3.x - p2.x) * (p1.z - p3.z);
		float l1 = ((p2.z - p3.z) * (pos.x - p3.x) + (p3.x - p2.x) * (pos.y - p3.z)) / det;
		float l2 = ((p3.z - p1.z) * (pos.x - p3.x) + (p1.x - p3.x) * (pos.y - p3.z)) / det;
		float l3 = 1.0f - l1 - l2;
		return l1 * p1.y + l2 * p2.y + l3 * p3.y;
	}

	public static Matrix4f createViewMatrix(Camera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static float normalize(float val) {
		return (val - Math.min(val, 1) / Math.max(val, 1) - Math.min(val, 1));
	}
	
	public static Matrix4f createViewMatrix(OverheadCamera camera) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
		Vector3f cameraPos = camera.getPosition();
		Vector3f negativeCameraPos = new Vector3f(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		Matrix4f.translate(negativeCameraPos, viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static Matrix4f createProjectionMatrix(float rotation) {
		Matrix4f viewMatrix = new Matrix4f();
		viewMatrix.setIdentity();
		Matrix4f.rotate((float)(Math.toRadians(rotation)), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
		return viewMatrix;
	}
	
	public static double distanceFormula3D(Vector3f position1, Vector3f position2) {
		double section1 = Math.pow((position2.getX() - (position1.getX())), 2);
		double section2 = Math.pow((position2.getY() - (position1.getY())), 2);
		double section3 = Math.pow((position2.getZ() - (position1.getZ())), 2);
		return Math.sqrt(section1 + section2 + section3);
	}
	
	public static Vector3f getRotationFromPoint(Enemy enemy, Player player) {
		double angle = Math.atan2((player.getPosition().x - enemy.getPosition().x), (player.getPosition().z - enemy.getPosition().z)) * 180 / Math.PI;
	    if (angle < 0) {
	        return new Vector3f(0, (float)(360 + angle), 0);
	    } else {
	    	if (angle > 360) {
	    		angle -= 360;
	    	}
	        return new Vector3f(0, (float)(angle), 0);
	    }
	}
	
	public static Vector3f getRotationFromPoint(Enemy p1, Point p2) {
		double angle = Math.atan2((p2.getX() - p1.getPosition().getX()), (p2.getZ() - p1.getPosition().getZ())) * 180 / Math.PI;
	    if (angle < 0) {
	        return new Vector3f(0, (float)(360 + angle), 0);
	    } else {
	        return new Vector3f(0, (float)(angle), 0);
	    }
	}
	
	public static Vector3f getRotationFromPoint(Point p1, Point p2) {
		double angle = Math.atan2((p2.getX() - p1.getX()), (p2.getZ() - p1.getZ())) * 180 / Math.PI;
	    if (angle < 0) {
	        return new Vector3f(0, (float)(360 + angle), 0);
	    } else {
	        return new Vector3f(0, (float)(angle), 0);
	    }
	}
	
	public static Vector3f getRotationFromPointNegative(Enemy enemy, Player player) {
		double angle = Math.atan2((player.getPosition().x - enemy.getPosition().x), (player.getPosition().z - enemy.getPosition().z)) * 180 / Math.PI;
	    if (angle < 0) {
	        return new Vector3f(0, (float) (360 + angle), 0);
	    } else {
	        return new Vector3f(0, (float) (angle), 0);
	    }
	}
	
	public static boolean isWithinScreen(Vector3f view, Vector3f enemy) {
		if (view.getY() - 90 >= enemy.getY() || view.getY() + 90 <= enemy.getY()) {
			return false;
		}
		return true;
	}
	
	public static boolean intToBoolean(int value) {
		if (value == 0) {
			return false;
		} else if (value == 1) {
			return true;
		} else {
			return false;
		}
	}
}