package guis.hud;

import org.lwjgl.util.vector.Vector2f;

import guis.GuiTexture;
import main.Mift;
import myo.MyoManager;
import renderEngine.Loader;

public class Target {
	private float x;
	private float y;
	
	public GuiTexture texture = null;
	
	public Target() {
		this(Mift.loader);
	}
	
	public Target(Loader loader) {
		x = y = 0;
		texture = new GuiTexture(loader.loadTexture("../sprites/pewpew"), new Vector2f(-0.99f, 1.0f), new Vector2f(0.025f, 0.025f));
	}
	
	public void update() {
		x += MyoManager.getAttackMyo().getYawDiff() / 10.0f;
		y -= MyoManager.getAttackMyo().getPitchDiff() / 10.0f;
		
		texture.setPosition(new Vector2f(x, y));
	}
}
