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
	private List<GuiTexture> textures = new ArrayList<GuiTexture>();
	
	public HUDCreator(Loader loader, boolean enemiesOnly) {
		compass = new Compass(loader);
		target = new Target(loader);
		textures.add(target.texture);
	}
	
	public void update(Camera camera) {
		compass.update(camera);
		target.update();
	}
	
	public void update(OverheadCamera camera) {
		compass.update(camera);
	}
	
	public Compass getCompass() {
		return compass;
	}
	
	public List<GuiTexture> getTextures() {
		return textures;
	}
}