package entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.util.vector.Vector3f;

import entities.EntityType.entityType;
import entities.MoveType.move_factor;
import main.Mift;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.OBJFileLoader;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import toolbox.Maths;
import water.WaterTile;

public class EntityCreator {
	TexturedModel texturedModel;
	RawModel rawModel;
	ModelTexture modelTexture;
	Entity entity;
	EntityTypeHolder eth;

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
		texturedModel = new TexturedModel(NormalMappedObjLoader.loadOBJ(objName, Mift.loader),
				new ModelTexture(Mift.loader.loadTexture(objName)));
		texturedModel.getTexture().setNormalMap(Mift.loader.loadTexture("normals/" + objNormal));
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
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName),
				new ModelTexture(Mift.loader.loadTexture(objName)));
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
		modelTexture = new ModelTexture(Mift.loader.loadTexture(objName));
		modelTexture.setNumberOfRows(atlasRows);
		texturedModel = new TexturedModel(OBJFileLoader.loadOBJ(objName), modelTexture);
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
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + p.getObjName()),
				new ModelTexture(Mift.loader.loadTexture(p.getTextureName())));
		Mift.instance_count++;
		return new Player(texturedModel, location, rotation.getX(), rotation.getY() + 180, rotation.getZ(),
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
	public Enemy createEnemy(entityType type, Vector3f location, Vector3f rotation, move_factor move, int id, int health) {
		EntityType e = eth.get(type);
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName()),
				new ModelTexture(Mift.loader.loadTexture(e.getTextureName())));
		Mift.instance_count++;
		return new Enemy(texturedModel, location, rotation, e.getScale(), move, id, health);
	}

	public List<Enemy> createRandomEnemy(move_factor move, int quantity) {
		List<Enemy> enemies = new ArrayList<Enemy>();
		EntityType e = eth.get(entityType.ENEMY);
		random.setSeed(System.currentTimeMillis());
		texturedModel = new TexturedModel(OBJLoader.loadObjModel("models/" + e.getObjName()),
				new ModelTexture(Mift.loader.loadTexture(e.getTextureName())));
		for (int i = 0; i < quantity; i++) {
			int x = random.nextInt(1000);
			int z = random.nextInt(1000);
			if (Maths.distanceFormula3D(new Vector3f(x, 0, z), Mift.player.getPosition()) > 100) {
				Mift.instance_count++;
				enemies.add(new Enemy(texturedModel, new Vector3f(x, Mift.terrain.getHeightOfTerrain(x, z), z),
						new Vector3f(0, random.nextInt(360), 0), e.getScale(), move, i, 1000));
			}
		}
		return enemies;
	}
	
	public List<Entity> generateObjects(List<Entity> entities, int generationLuck, Terrain terrain, int gridX, int gridZ) {
		TexturedModel fern = createAtlasTexturedModel("fern", 2);
		TexturedModel pine = createTexturedModel("pine", 0f, 0f);
		TexturedModel barrel = createNormalTexturedModel("barrel", "barrelNormal", 10f, 0.6f);
		TexturedModel lamp = createTexturedModel("lantern", 10f, 0.6f);
		
		pine.getTexture().setHasTransparency(true);
		fern.getTexture().setHasTransparency(true);
		barrel.getTexture().setHasTransparency(false);
		barrel.getTexture().setSpecularMap(Mift.loader.loadTexture("speculars/barrelSpecular"));
		lamp.getTexture().setHasTransparency(true);
		lamp.getTexture().setSpecularMap(Mift.loader.loadTexture("speculars/lanternSpecular"));

		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		for (int i = 0; i < generationLuck; i++) {
			/********
			 * FERN *
			 * FERN *
			 * FERN *
			 * FERN *
			 ********/
			if (i % 3 == 0) {
				float x = random.nextInt(1000) + gridX;
				float z = random.nextInt(1000) + gridZ;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y > WaterTile.height) {
					Entity e = new Entity(fern, 3, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
							random.nextFloat() * 0.6f + 0.8f, entityType.FERN);
					e.getModel().getTexture().setHasTransparency(false);
					if (e != null) {
						entities.add(e);
						Mift.instance_count++;
					}
				}
			}
			/********
			 * PINE *
			 * PINE *
			 * PINE *
			 * PINE *
			 ********/
			if (i % 2 == 0) {
				float x = random.nextInt(1000) + gridX;
				float z = random.nextInt(1000) + gridZ;
				float y = terrain.getHeightOfTerrain(x, z);
				if (y >= WaterTile.height) {
					Entity e = new Entity(pine, 1, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
							random.nextFloat() * 0.6f + 0.8f, entityType.PINE);
					if (e != null) {
						entities.add(e);
						Mift.instance_count++;
					}
				}
			}
			/**********
			 * BARREL *
			 * BARREL *
			 * BARREL *
			 * BARREL *
			 **********/
			if (i % 2 == 0) {
				float x = random.nextInt(1000) + gridX;
				float z = random.nextInt(1000) + gridZ;
				float y = terrain.getHeightOfTerrain(x, z);
				Entity e = new Entity(barrel, 4, new Vector3f(x, y + 4, z), 0, random.nextFloat() * 360, 0,
						random.nextFloat() * 0.6f + 0.4f, entityType.BARREL);
				if (e != null) {
					entities.add(e);
					Mift.instance_count++;
				}
			}
			/***********
			 * LANTERN *
			 * LANTERN *
			 * LANTERN *
			 * LANTERN *
			 ***********/
			if (i % 4 == 0) {
				float x = random.nextInt(1000) + gridX;
				float z = random.nextInt(1000) + gridZ;
				float y = terrain.getHeightOfTerrain(x, z);
				Entity e = new Entity(lamp, 4, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0,
						random.nextFloat() * 0.6f + 0.4f, entityType.LAMP);
				if (e != null) {
					entities.add(e);
					Mift.instance_count++;
				}
			}
		}
		return entities;
	}
}