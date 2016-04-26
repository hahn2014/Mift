package attacks.fireball;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.EntityType;
import entities.EntityType.entityType;
import entities.EntityTypeHolder;
import main.Mift;
import models.TexturedModel;
import renderEngine.OBJLoader;
import textures.ModelTexture;

public class FireballHolder {
	List<Fireball> fireballs = new ArrayList<Fireball>();
	private EntityTypeHolder eth = new EntityTypeHolder();
	
	
	public List<Fireball> getAll() {
		return fireballs;
	}
	
	public Fireball get(int index) {
		return fireballs.get(index);
	}
	
	public void createFireball(Vector3f pos, Vector3f gotoPos) {
		EntityType e = eth.get(entityType.ATK_FIREBALL);
		TexturedModel texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName()),
				new ModelTexture(Mift.loader.loadTexture(e.getTextureName())));
		Mift.instance_count++;
		Fireball f = new Fireball(texturedModel, pos, gotoPos, e.getScale());
		f.setRenderable(true);
		fireballs.add(f);
		Mift.entities.add(f);
	}
	
	public void remove(Fireball ball) {
		fireballs.remove(ball);
		Mift.entities.remove(ball);
		Mift.instance_count--;
	}
}