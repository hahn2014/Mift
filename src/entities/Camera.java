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
 */
public class Camera {
	
	public float distanceFromPlayer = 40, maxDistFromPlayer = 100;
	public int zoomFactor = 120;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(20, 5, 0);
	private float pitch = 40, maxPitch, minPitch;
	private static float yaw;
	private float roll;
	private float horizontalDistance;
	private float verticalDistance;
	private float horizontalSensitivity = 0.3f;
	private float verticalSensitivity = 0.1f;
	
	private Player player;

	public Camera(Player player) {
		this.player = player;
		Mouse.setGrabbed(true);
		maxPitch = 80;
		minPitch = -40;
	}
	
	public void move() {
		if (Mouse.isGrabbed()) {
			calculateZoom();
			calculatePitch();
			calculateAngleAroundPlayer();
			horizontalDistance = calculateHorizontalDistance();
			verticalDistance = calculateVerticalDistance();
			calculateCameraPosition(horizontalDistance, verticalDistance);
			yaw = 180 - (player.getRotY() + angleAroundPlayer);
		}
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
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			Mouse.setGrabbed(false);
		}
	}
	
	public void getClicks() {
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON PRESSED
		        }
		    } else {
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON RELEASED
		        	if (Mouse.isGrabbed() == false) {
		        		Mouse.setGrabbed(true);
		        	}
		        }
		    }
		}
	}
	
	public void setGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getView() {
		return new Vector3f(pitch, yaw, roll);
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
		position.x = player.getPosition().x - offsetX;
		position.z = player.getPosition().z - offsetZ;
		position.y = player.getPosition().y + verticDistance + 7;
	}

	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}

	private void calculateZoom() {
		float change = Mouse.getDWheel() / zoomFactor;
		distanceFromPlayer -= change;
		if (distanceFromPlayer < 0) {
			distanceFromPlayer = 0;
		} else if (distanceFromPlayer > maxDistFromPlayer) {
			distanceFromPlayer = maxDistFromPlayer;
		}
	}

	private void calculatePitch() {
		float pitchChange = Mouse.getDY() * verticalSensitivity;
		if (pitch - pitchChange >= minPitch && pitch - pitchChange <= maxPitch) {
			pitch -= pitchChange;
		}
	}

	private void calculateAngleAroundPlayer() {
		float angleChange = Mouse.getDX() * horizontalSensitivity;
		player.setRotY(player.getRotY() - angleChange);
	}

	public boolean isFPS() {
		return (distanceFromPlayer < 1);
	}
}