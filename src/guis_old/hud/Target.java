package guis_old.hud;

import org.lwjgl.util.vector.Vector2f;

import guis_old.GuiTexture;
import main.Mift;
import myo.MyoManager;
import renderEngine.DisplayManager;
import renderEngine.Loader;

public class Target {
	public float x;
	public float y;
	public float new_x;
	public float new_y;
	
	public GuiTexture texture = null;
	
	public Target() {
		this(Mift.loader);
	}
	
	public Target(Loader loader) {
		x = y = 0;
		new_x = new_y = 0;
		texture = new GuiTexture(loader.loadTexture("../sprites/pewpew"), new Vector2f(-0.99f, 1.0f), new Vector2f(0.04f, 0.04f));
	}
	
	public void update() {
		new_x += MyoManager.getAttackMyo().getYawDiff() / 10.0f;
		new_y -= MyoManager.getAttackMyo().getPitchDiff() / 10.0f;
		
		if (new_x > 0 || new_x < DisplayManager.getWidth()) {
			x = new_x;
		}
		if (new_y > 0 || new_y < DisplayManager.getHeight()) {
			y = new_y;
		}
		
		texture.setPosition(new Vector2f(x, y));
	}
}
