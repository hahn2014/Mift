package guis.hud;

import java.util.ArrayList;
import java.util.List;

import entities.Camera;
import entities.OverheadCamera;
import guis.GuiTexture;
import renderEngine.Loader;

public class HUDCreator {
	private Compass compass;
	private boolean drawEnemiesOnly = false;
	private List<GuiTexture> textures = new ArrayList<GuiTexture>();
	
	public HUDCreator(Loader loader, boolean enemiesOnly) {
		compass = new Compass(loader);
		drawEnemiesOnly = enemiesOnly;
	}
	
	public void update(Camera camera) {
		compass.update(camera, drawEnemiesOnly);
	}
	
	public void update(OverheadCamera camera) {
		compass.update(camera, drawEnemiesOnly);
	}
	
	public Compass getCompass() {
		return compass;
	}
	
	public List<GuiTexture> getTextures() {
		return textures;
	}
}