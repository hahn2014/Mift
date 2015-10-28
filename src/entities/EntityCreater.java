package entities;

import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class EntityCreater {
	TexturedModel texturedModel;
	RawModel rawModel;
	ModelTexture modelTexture;
	Entity entity;

	private int light = 256;
	private int moderate = 512;
	private int ultra = 1024;

	public EntityCreater() {
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Creates a textured model with a normal map addition instead of a plain
	 * surface.
	 * 
	 * @param loader
	 * @param objName
	 * @param objNormal
	 * @param shineDamper
	 * @param reflectivity
	 * @return TexturedModel
	 * @since 1.31
	 */
	public TexturedModel createNormalTexturedModel(Loader loader, String objName, String objNormal, float shineDamper,
			float reflectivity) {
		texturedModel = new TexturedModel(NormalMappedObjLoader.loadOBJ(objName, loader),
				new ModelTexture(loader.loadTexture(objName)));
		texturedModel.getTexture().setNormalMap(loader.loadTexture("normals/" + objNormal));
		texturedModel.getTexture().setShineDamper(shineDamper);
		texturedModel.getTexture().setReflectivity(reflectivity);
		return texturedModel;
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Creates a textured model with a plain surface.
	 * 
	 * @param loader
	 * @param objName
	 * @param shineDamper
	 * @param reflectivity
	 * @return TexturedModel
	 */
	public TexturedModel createTexturedModel(Loader loader, String objName, float shineDamper, float reflectivity) {
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName, loader),
				new ModelTexture(loader.loadTexture(objName + getRes())));
		texturedModel.getTexture().setShineDamper(shineDamper);
		texturedModel.getTexture().setReflectivity(reflectivity);
		return texturedModel;
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Creates a textured model using atlas technologies to grab the texture
	 * from a corner of an image.
	 * 
	 * @param loader
	 * @param objName
	 * @param atlasRows
	 * @return TexturedModel
	 */
	public TexturedModel createAtlasTexturedModel(Loader loader, String objName, int atlasRows) {
		modelTexture = new ModelTexture(loader.loadTexture(objName));
		modelTexture.setNumberOfRows(atlasRows);
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName, loader), modelTexture);
		return texturedModel;
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Creates a player entity to be returned and used as either the standard
	 * player or multi-player player!
	 * 
	 * @param loader
	 * @param objName
	 * @param textureName
	 * @param location
	 * @param rotation
	 * @param scale
	 * @return TexturedModel
	 */
	public Player createPlayer(Loader loader, String objName, String textureName, Vector3f location, Vector3f rotation,
			float scale) {
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + objName, loader),
				new ModelTexture(loader.loadTexture(textureName)));
		return new Player(texturedModel, location, rotation.getX(), rotation.getY(), rotation.getZ(), scale);
	}
	
	public int getRes() {
		if (DisplayManager.quality == DisplayManager.QUALITY.LIGHT) {
			return light;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.MODERATE) {
			return moderate;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.ULTRA) {
			return ultra;
		}
		return light;
	}
	
	public List<Entity> generateObjects(Loader loader, List<Entity> entities, Terrain terrain, int generationLuck) {
		TexturedModel fern = createAtlasTexturedModel(loader, "fern", 2);
		TexturedModel bobble = createTexturedModel(loader, "pine", 0f, 0f);
		TexturedModel rocks = createTexturedModel(loader, "rocks", 10f, 0.5f);
		bobble.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		
		Random random = new Random(5666778);
		for (int i = 0; i < generationLuck; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * -150;
				if ((x > 50 && x < 100) || (z < -50 && z > -100)) {
				} else {
					float y = terrain.getHeightOfTerrain(x, z);

					entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
				}
			}
			if (i % 2 == 0) {

				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * -150;
				if ((x > 50 && x < 100) || (z < -50 && z > -100)) {

				} else {
					float y = terrain.getHeightOfTerrain(x, z);
					entities.add(new Entity(bobble, 1, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
							random.nextFloat() * 0.6f + 0.8f));
				}
			}
		}
		entities.add(new Entity(rocks, new Vector3f(75, 4.6f, -75), 0, 0, 0, 75));
		return entities;
	}
}