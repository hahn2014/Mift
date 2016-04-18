package guis.hud;

import java.util.ArrayList;
import java.util.List;

import entities.Camera;
import entities.OverheadCamera;
import guis.GuiTexture;
import renderEngine.Loader;

public class HUDCreator {
	private Compass compass;
	private Target target;
	private boolean drawEnemiesOnly = false;
	private List<GuiTexture> textures = new ArrayList<GuiTexture>();
	
	public HUDCreator(Loader loader, boolean enemiesOnly) {
		//compass = new Compass(loader);
		target = new Target(loader);
		textures.add(target.texture);
		drawEnemiesOnly = enemiesOnly;
	}
	
	public void update(Camera camera) {
		//compass.update(camera, drawEnemiesOnly);
		target.update();
	}
	
	public void update(OverheadCamera camera) {
		//compass.update(camera, drawEnemiesOnly);
	}
	
//	public Compass getCompass() {
//		return compass;
//	}
	
	public List<GuiTexture> getTextures() {
		return textures;
	}
}