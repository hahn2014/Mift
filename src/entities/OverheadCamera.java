package entities;

import java.io.Serializable;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector3f;

import entities.EntityType.entityType;
import entities.MoveType.move_factor;
import io.SettingHolder;
import main.Mift;
import renderEngine.DisplayManager;

/**
 * Third Person Camera view.
 * Use one camera class object
 * at a time or the Universe
 * will explode! :P
 * @author Bryce Hahn
 * @since 1.1
 */
public class OverheadCamera implements Serializable {
	private static final long serialVersionUID = -7302478949741494643L;
	private float angleAroundPlayer = 0;

	private Vector3f position = new Vector3f(0, 0, 0);
	private float pitch = 90, yaw;
	private float horizontalDistance;
	private float verticalDistance;
	private Random random = new Random();
	private Player player;
	private EntityTypeHolder eth = new EntityTypeHolder();
	private MoveTypeHolder mth = new MoveTypeHolder();
	public entityType placerType = entityType.PLAYER;
	public move_factor move_type = move_factor.NOTHING;
	
	public OverheadCamera() {
		Mouse.setGrabbed(true);
		random.setSeed(Sys.getTime());
	}
	
	public void setPlayer(Player player) {
		this.player = player;
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
			Mift.setPaused(true);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_F1)) {
			DisplayManager.setFullscreened(!SettingHolder.get("cg_fullscreened").getValueB());
		}
	}
	
	public void getClicks() {
		while (Mouse.next()){
		    if (Mouse.getEventButtonState()) { //pressed
		    } else { //released
		        if (Mouse.getEventButton() == 0) {
		            //LEFT BUTTON RELEASED
		        	random.setSeed(Sys.getTime());
		        	EntityCreator e = new EntityCreator(); //spawn a new model of the current model selection
		        	Mift.addEnemy(e.createEnemy(placerType, Mift.getMousePicker(true).getCurrentTerrainPoint(), 
		        			player.getRotation(), move_type, random.nextInt(99999999), 1000));
		        }
		        if (Mouse.getEventButton() == 1) {
		            //RIGHT BUTTON RELEASED
		        	//we need to check if we are pressing over an entity,
		        	//and if we are we are going delete it from the list
		        	Vector3f point = Mift.getMousePicker(true).getCurrentTerrainPoint();
		        	Entity toRemove = null;
		        	for (Entity entity : Mift.entities) {
		        		if (entity.rayOverEntity(point) && !entity.getType().equals(entityType.PLAYER)) {
		        			//we clicked on an entity, delete it
		        			toRemove = entity;
		        			break;
		        		}
		        	}
		        	if (toRemove != null) {
			        	Mift.entities.remove(toRemove);
			    		Mift.instance_count--;
		        	}
		        }
		    }
		}
	}
	
	public void calculateZoom() {
		float change = Mouse.getDWheel() / 140;
		Mift.camera.distanceFromPlayer -=change;
		if (Mift.camera.distanceFromPlayer < Mift.camera.maxDistFromPlayer) {
			Mift.camera.distanceFromPlayer = Mift.camera.maxDistFromPlayer - 1;
			player.setOverhead(false);
			Mouse.setGrabbed(true);
		} else {
			Mift.camera.distanceFromPlayer = Mift.camera.maxDistFromPlayer;
		}
	}
	
	public void setGrabbed(boolean grabbed) {
		Mouse.setGrabbed(grabbed);
	}

	public Vector3f getPosition() {
		return position;
	}
	
	public String getPositionDebug() {
		return "[" + (int)position.x + ", " + (int)position.y + ", " + (int)position.z + "]";
	}
	
	public Vector3f getView() {
		return new Vector3f(pitch, yaw, 0);
	}
	
	public String getViewDebug() {
		return "[" + (int)pitch + ", " + (int)yaw + ", 0]";
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
	
	public EntityTypeHolder getETH() {
		return eth;
	}
	
	public MoveTypeHolder getMTH() {
		return mth;
	}

	private void calculateCameraPosition(float horizDistance, float verticDistance) {
		position.x = player.getPosition().x - 90;
		position.z = player.getPosition().z;
		position.y = player.getPosition().y + Mift.camera.distanceFromPlayer + 7;
	}
	
	@SuppressWarnings("unused")
	@Deprecated
	private void calculateAngleAroundPlayer() {
		float angleChange = Mouse.getDX() * 0.3f;
		player.setRotY(player.getRotY() - angleChange);
	}
	
	private float calculateHorizontalDistance() {
		return (float) (Mift.camera.distanceFromPlayer * Math.cos(Math.toRadians(pitch)));
	}

	private float calculateVerticalDistance() {
		return (float) (Mift.camera.distanceFromPlayer * Math.sin(Math.toRadians(pitch)));
	}
}