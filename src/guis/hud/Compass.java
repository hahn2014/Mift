package guis.hud;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.Camera;
import entities.Entity;
import entities.OverheadCamera;
import guis.GuiTexture;
import main.Mift;
import renderEngine.Loader;
import toolbox.Maths;

public class Compass {
	public GuiTexture compassTexture;
	
	private int updateDelay = 0;
	
	public Compass(Loader loader) {
		compassTexture = new GuiTexture(loader.loadTexture("compass"), new Vector2f(-0.75f, -0.75f), new Vector2f(0.18f, 0.30f));
		//compassTexture = new GuiTexture(loader.loadTexture("compass"), new Vector2f(-0.25f, -0.25f), new Vector2f(0.18f, 0.30f));
		//Mift.guiTextures.add(compassTexture);
	}
	
	public void update(Camera camera, boolean enemiesOnly) {
		compassTexture.setRotation(camera.getYaw());
		if (updateDelay < 120) {
			updateDelay++;
		} else {
			//look for enemies/friendlies
			if (enemiesOnly == false) {
				updateEntitiesOnCompass();
			}
			updateEnemiesOnCompass();
			System.out.println("---------------------------------------------------------");
			updateDelay = 0;
		}
	}
	
	public void update(OverheadCamera camera, boolean enemiesOnly) {
		compassTexture.setRotation(camera.getYaw());
		if (updateDelay < 120) {
			updateDelay++;
		} else {
			//look for enemies/friendlies
			if (enemiesOnly == false) {
				updateEntitiesOnCompass();
			}
			updateEnemiesOnCompass();
			System.out.println("---------------------------------------------------------");
			updateDelay = 0;
		}
	}
	
	private void updateEnemiesOnCompass() {
		List<Entity> enemies = new ArrayList<Entity>();
		for (int i = 0; i < Mift.enemies.size(); i++) {
			if (Maths.distanceFormula3D(Mift.player.getPosition(), Mift.enemies.get(i).getPosition()) <= 50) {
				enemies.add(Mift.enemies.get(i));
			}
		}
		for (int i = 0; i < enemies.size(); i++) {
			System.out.println("Enemy [" + i + "] is within 50 units from the player. Updating positiong on the compass." + enemies.get(i).getType().name());
		}
		enemies.clear();
	}
	
	private void updateEntitiesOnCompass() {
		List<Entity> entities = new ArrayList<Entity>();
		for (int i = 0; i < Mift.entities.size(); i++) {
			if (Maths.distanceFormula3D(Mift.player.getPosition(), Mift.entities.get(i).getPosition()) <= 50) {
				entities.add(Mift.entities.get(i));
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			System.out.println("Entity [" + i + "] is within 50 units from the player. Updating positiong on the compass. " + entities.get(i).getType().name());
		}
		entities.clear();
	}
}