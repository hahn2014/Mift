package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

/**
 * Third Person Camera view.
 * Use one camera class object
 * at a time or the Universe
 * will explode! :P
 * @author Bryce Hahn
 * @since 1.1
 * @see Camera1
 */
public class Camera {
	public static enum POV {
		FIRST_PERSON_POV,
		THIRD_PERSON_POV
	};
	
	private float distanceFromPlayer = 30, maxDistanceFromPlayer = 200, minDistanceFromPlayer = 20;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(20, 5, 0);
	private float pitch = 40, maxPitch = 80, minPitch = -40;
	private static float yaw;
	private float roll;
	private float horizontalDistance;
	private float verticalDistance;
	private float horizontalSensitivity = 0.3f;
	private float verticalSensitivity = 0.1f;
	
	private Player player;
	private static POV pov;

	public Camera(Player player, POV pv) {
		this.player = player;
		pov = pv;
		
		if (pov == POV.THIRD_PERSON_POV) {
			maxPitch = 60;
			minPitch = 15;
		} else if (pov == POV.FIRST_PERSON_POV) {
			maxPitch = 80;
			minPitch = -40;
		}
	}
	
	public void move() {
		if (pov == POV.THIRD_PERSON_POV) {
			calculateZoom();
		}
		calculatePitch();
		calculateAngleAroundPlayer();
		horizontalDistance = calculateHorizontalDistance();
		verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
	}

	public void rotate() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP)) { // look up
			if (pitch + 0.75f <= maxPitch) {
				pitch += 0.75f;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN)) { // look down
			if (pitch - 0.75f >= minPitch) {
				pitch -= 0.75f;
			}
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT)) { // look left
			yaw -= 0.75f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) { // look right
			yaw += 0.75f;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) { //close game for now
			Mouse.setGrabbed(false);
			System.exit(0);
		}
	}
	
	public void setGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getPitch() {
		return pitch;
	}

	public void invertPitch() {
		this.pitch = -pitch;
	}

	public float getYaw() {
		return yaw;
	}

	public float getRoll() {
		return roll;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		float theta = player.getRotY() + angleAroundPlayer;
		float offsetX = (float) (horizDistance * Math.sin(Math.toRadians(theta)));
		float offsetZ = (float) (horizDistance * Math.cos(Math.toRadians(theta)));
		if (pov == POV.THIRD_PERSON_POV) {
			position.x = player.getPosition().x - offsetX;
			position.z = player.getPosition().z - offsetZ;
			position.y = player.getPosition().y + verticDistance;
		} else {
			position.x = player.getPosition().x;
			position.z = player.getPosition().z;
			position.y = player.getPosition().y + 7;
		}
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		float zoomLevel = Mouse.getDWheel() * 0.1f;
		if (distanceFromPlayer - zoomLevel >= minDistanceFromPlayer
				&& distanceFromPlayer - zoomLevel <= maxDistanceFromPlayer) {
			distanceFromPlayer -= zoomLevel;
		}
	}

	private void calculatePitch() {
		float pitchChange = Mouse.getDY() * verticalSensitivity;
		if (pov == POV.THIRD_PERSON_POV) {
			if (Mouse.isButtonDown(1) == true) {
				if (pitch + pitchChange >= minPitch && pitch + pitchChange <= maxPitch) {
					pitch += pitchChange;
				}
			}
		} else if (pov == POV.FIRST_PERSON_POV) {
			if (pitch - pitchChange >= minPitch && pitch - pitchChange <= maxPitch) {
				pitch -= pitchChange;
			}
		}
	}

	private void calculateAngleAroundPlayer() {
		float angleChange = Mouse.getDX() * horizontalSensitivity;
		if (pov == POV.THIRD_PERSON_POV) {
			if (Mouse.isButtonDown(0)) {
				angleAroundPlayer -= angleChange;
			}
		} else if (pov == POV.FIRST_PERSON_POV) {
			player.setRotY(player.getRotY() - angleChange);
		}
	}
	
	public static void setPOV(POV pv) {
		pov = pv;
	}
	
	public static POV getPOV() {
		return pov;
	}
	
	public static String getPOVName() {
		return pov.toString();
	}
}