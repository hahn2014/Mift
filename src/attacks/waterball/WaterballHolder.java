package attacks.waterball;

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

public class WaterballHolder {
	List<Waterball> waterballs = new ArrayList<Waterball>();
	private EntityTypeHolder eth = new EntityTypeHolder();
	
	public List<Waterball> getAll() {
		return waterballs;
	}
	
	public Waterball get(int index) {
		return waterballs.get(index);
	}
	
	public void createWaterball(Vector3f pos, Vector3f gotoPos) {
		EntityType e = eth.get(entityType.ATK_WATERBALL);
		TexturedModel texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName()),
				new ModelTexture(Mift.loader.loadTexture(e.getTextureName())));
		Mift.instance_count++;
		Waterball w = new Waterball(texturedModel, pos, gotoPos, e.getScale());
		w.setRenderable(true);
		waterballs.add(w);
		Mift.entities.add(w);
	}
	
	public void remove(Waterball ball) {
		waterballs.remove(ball);
		Mift.entities.remove(ball);
		Mift.instance_count--;
	}
}