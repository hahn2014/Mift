package entities;

import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import entities.Enemy.move_factor;
import entities.EntityType.entityType;
import main.Mift;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.DisplayManager;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class EntityCreator {
	TexturedModel texturedModel;
	RawModel rawModel;
	ModelTexture modelTexture;
	Entity entity;
	EntityTypeHolder eth;

	private int light = 256;
	private int moderate = 512;
	private int ultra = 1024;
	private Random random = new Random();

	public EntityCreator() {
		eth = new EntityTypeHolder();
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
	public TexturedModel createNormalTexturedModel(String objName, String objNormal, float shineDamper,
			float reflectivity) {
		texturedModel = new TexturedModel(NormalMappedObjLoader.loadOBJ(objName, Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(objName)));
		texturedModel.getTexture().setNormalMap(Mift.getLoader().loadTexture("normals/" + objNormal));
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
	public TexturedModel createTexturedModel(String objName, float shineDamper, float reflectivity) {
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName, Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(objName + getRes())));
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
	public TexturedModel createAtlasTexturedModel(String objName, int atlasRows) {
		modelTexture = new ModelTexture(Mift.getLoader().loadTexture(objName));
		modelTexture.setNumberOfRows(atlasRows);
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName, Mift.getLoader()), modelTexture);
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
	public Player createPlayer(Vector3f location, Vector3f rotation) {
		EntityType p = eth.get(entityType.PLAYER);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + p.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(p.getTextureName())));
		return new Player(texturedModel, location, rotation.getX(), rotation.getY(), rotation.getZ(), p.getScale());
	}
	
	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Created an enemy to be returned and used as either the an enemy or
	 * another player online!
	 * @param location
	 * @param rotation
	 * @param runSpeed
	 * @param move
	 * @return TexturedModel
	 */
	public Enemy createEnemy(Vector3f location, Vector3f rotation, move_factor move) {
		EntityType e = eth.get(entityType.ENEMY);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		return new Enemy(texturedModel, location, rotation, e.getScale(), move, Mift.getTerrain());
	}
	
	public Enemy createEnemy(entityType type, Vector3f location, Vector3f rotation, move_factor move) {
		EntityType e = eth.get(type);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		return new Enemy(texturedModel, location, rotation, e.getScale(), move, Mift.getTerrain());
	}
	
	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Created an enemy to be returned and used as either the an enemy or
	 * another player online! This method is the exact same as it's predecessor,
	 * but instead of setting parameters for the enemy location and rotation,
	 * it is all randomly chosen within the call method.
	 * No need for all the pesky random calls.
	 * @param loader
	 * @param objName
	 * @param textureName
	 * @param scale
	 * @param runSpeed
	 * @param move
	 * @return Textured Model
	 */
	public Enemy createRandomEnemy(move_factor move) {
		EntityType e = eth.get(entityType.ENEMY);
		random.setSeed(Sys.getTime());
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		return new Enemy(texturedModel, new Vector3f(random.nextInt(120) + 30, random.nextInt(5) + 2, random.nextInt(100) - 100),
				new Vector3f(0, random.nextInt(360), 0), e.getScale(), move, Mift.getTerrain());
	}
	
	private int getRes() {
		if (DisplayManager.quality == DisplayManager.QUALITY.LIGHT) {
			return light;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.MODERATE) {
			return moderate;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.ULTRA) {
			return ultra;
		}
		return light;
	}
	
	public List<Entity> generateObjects(List<Entity> entities, Terrain terrain, int generationLuck) {
		TexturedModel fern = createAtlasTexturedModel("fern", 2);
		TexturedModel bobble = createTexturedModel("pine", 0f, 0f);
		TexturedModel rocks = createTexturedModel("rocks", 10f, 0.5f);
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