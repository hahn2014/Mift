package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.Enemy.move_factor;
import entities.EntityType.entityType;
import main.Mift;

/**
 * Third Person Camera view.
 * Use one camera class object
 * at a time or the Universe
 * will explode! :P
 * @author Bryce Hahn
 * @since 1.1
 */
public class OverheadCamera {
	
	public float distanceFromPlayer = 50;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 90, yaw;
	private float horizontalDistance;
	private float verticalDistance;
	
	private Player player;
	private EntityTypeHolder eth = new EntityTypeHolder();
	public entityType placerType = entityType.PLAYER;
	
	public OverheadCamera(Player player) {
		this.player = player;
		Mouse.setGrabbed(true);
	}
	
	public void move() {
		calculateZoom();
		horizontalDistance = calculateHorizontalDistance();
		verticalDistance = calculateVerticalDistance();
		calculateCameraPosition(horizontalDistance, verticalDistance);
		yaw = 180 - (player.getRotY() + angleAroundPlayer);
		position.setX(player.getPosition().x);
		position.setZ(player.getPosition().z);
	}

	public void rotate() {
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) { //close game for now
			Mouse.setGrabbed(false);
			System.exit(0);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) { //unlock the mouse from the screen
			Mouse.setGrabbed(false);
		}
		while (Keyboard.next()) {
			if (Keyboard.getEventKey() == Keyboard.KEY_1) {
			    if (Keyboard.getEventKeyState()) {} else {
					placerType = eth.rotate(placerType);
			    }
			}
		}
	}
	
	public void getClicks() {
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) {} else {
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON RELEASED
		        	EntityCreator e = new EntityCreator();
		        	Mift.entities.add(e.createEnemy(placerType, Mift.getMousePicker(true).getCurrentTerrainPoint(), 
		        			new Vector3f(0, 0, 0), move_factor.MOVE_CIRCLES));
		        }
		    }
		}
	}
	
	public void calculateZoom() {
		float change = Mouse.getDWheel() / 140;
		distanceFromPlayer -= change;
		if (distanceFromPlayer < 50) {
			distanceFromPlayer = 49;
			Mift.getCamera().distanceFromPlayer = 49;
			player.setOverhead(false);
			Mouse.setGrabbed(true);
		} else {
			distanceFromPlayer = 50;
		}
	}
	
	public void setGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public Vector3f getView() {
		return new Vector3f(pitch, yaw, 0);
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

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		position.x = player.getPosition().x - 90;
		position.z = player.getPosition().z;
		position.y = player.getPosition().y + distanceFromPlayer + 7;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void calculateAngleAroundPlayer() {
		float angleChange = Mouse.getDX() * 0.3f;
		player.setRotY(player.getRotY() - angleChange);
	}
	
	private float calculateHorizontalDistance() {
		return (float) (distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
}