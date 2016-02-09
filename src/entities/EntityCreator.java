package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.util.vector.Vector3f;

import entities.EntityType.entityType;
import entities.MoveType.move_factor;
import main.Mift;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.Loader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;

public class EntityCreator {
	TexturedModel texturedModel;
	RawModel rawModel;
	ModelTexture modelTexture;
	Entity entity;
	EntityTypeHolder eth;

	private Random random = new Random();
	List<Terrain> terrains = new ArrayList<Terrain>();

	public EntityCreator(List<Terrain> terrains) {
		this.terrains = terrains;
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
				new ModelTexture(Mift.getLoader().loadTexture(objName)));
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
	public Player createPlayer(Vector3f location, Vector3f rotation, Loader loader) {
		EntityType p = eth.get(entityType.PLAYER);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + p.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(p.getTextureName())));
		Mift.instance_count++;
		return new Player(loader, texturedModel, location, rotation.getX(), rotation.getY(), rotation.getZ(),
				p.getScale());
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Created an enemy to be returned and used as either the an enemy or
	 * another player online!
	 * 
	 * @param location
	 * @param rotation
	 * @param runSpeed
	 * @param move
	 * @return TexturedModel
	 */
	public Enemy createEnemy(Vector3f location, Vector3f rotation, move_factor move, int id) {
		EntityType e = eth.get(entityType.ENEMY);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		Mift.instance_count++;
		return new Enemy(texturedModel, location, rotation, e.getScale(), move, Mift.getTerrain(), id);
	}

	public Enemy createEnemy(entityType type, Vector3f location, Vector3f rotation, move_factor move, int id) {
		EntityType e = eth.get(type);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		Mift.instance_count++;
		return new Enemy(texturedModel, location, rotation, e.getScale(), move, Mift.getTerrain(), id);
	}

	/**
	 * Create an entity to be used in game efficiently and in one line of code!
	 * Created an enemy to be returned and used as either the an enemy or
	 * another player online! This method is the exact same as it's predecessor,
	 * but instead of setting parameters for the enemy location and rotation, it
	 * is all randomly chosen within the call method. No need for all the pesky
	 * random calls.
	 * 
	 * @param loader
	 * @param objName
	 * @param textureName
	 * @param scale
	 * @param runSpeed
	 * @param move
	 * @return Textured Model
	 */
	public Enemy createRandomEnemy(move_factor move, int id) {
		EntityType e = eth.get(entityType.ENEMY);
		random.setSeed(Sys.getTime());
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		int x = random.nextInt(1000);
		int z = random.nextInt(1000);
		Mift.instance_count++;
		return new Enemy(texturedModel, new Vector3f(x, terrains.get(0).getHeightOfTerrain(x, z), z),
				new Vector3f(0, random.nextInt(360), 0), e.getScale(), move, Mift.getTerrain(), id);
	}

	public Enemy createRandomEnemy(int id) {
		EntityType e = eth.get(entityType.ENEMY);
		random.setSeed(Sys.getTime());
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName(), Mift.getLoader()),
				new ModelTexture(Mift.getLoader().loadTexture(e.getTextureName())));
		int x = random.nextInt(1000);
		int z = random.nextInt(1000);
		Mift.instance_count++;
		return new Enemy(texturedModel, new Vector3f(x, terrains.get(0).getHeightOfTerrain(x, z), z),
				new Vector3f(0, random.nextInt(360), 0), e.getScale(), MoveType.randomEnum(move_factor.class), Mift.getTerrain(), id);
	}

	public List<Entity> generateObjects(List<Entity> entities, int generationLuck) {
		TexturedModel fern = createAtlasTexturedModel("fern", 2);
		TexturedModel pine = createTexturedModel("pine", 0f, 0f);
		pine.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		for (int i = 0; i < generationLuck; i++) {
			if (i % 3 == 0) {
				float x = random.nextInt(1000);
				float z = random.nextInt(1000);
				float y = terrains.get(0).getHeightOfTerrain(x, z);
				if (y > -9f) {
					Entity e = new Entity(fern, 3, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f, entityType.FERN);
					e.getModel().getTexture().setHasTransparency(false);
					entities.add(e);
					Mift.instance_count++;
				} else {
				}
			}
			if (i % 2 == 0) {
				float x = random.nextInt(1000);
				float z = random.nextInt(1000);
				float y = terrains.get(0).getHeightOfTerrain(x, z);
				if (y >= -10f) {
					entities.add(new Entity(pine, 1, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
							random.nextFloat() * 0.6f + 0.8f, entityType.PINE));
					Mift.instance_count++;
				} else {
				}
			}
		}
		return entities;
	}
}