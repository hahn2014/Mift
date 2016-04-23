package terrains;

import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import entities.Enemy;
import entities.Entity;
import entities.EntityCreator;
import entities.MoveType.move_factor;
import entities.Player;
import main.Mift;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class TerrainCreator {
	Terrain terrain;
	TerrainTexture backgroundTex;
	TerrainTexture tex1;
	TerrainTexture tex2;
	TerrainTexture tex3;
	TerrainTexture blendmapTex;
	TerrainTexturePack texturePack;
	
	public TerrainCreator(int gridX, int gridY, String background, String r, String g, String b, String blend) {
		backgroundTex = new TerrainTexture(Mift.loader.loadTexture("terrain/" + background));
		tex1 = new TerrainTexture(Mift.loader.loadTexture("terrain/" + r));
		tex2 = new TerrainTexture(Mift.loader.loadTexture("terrain/" + g));
		tex3 = new TerrainTexture(Mift.loader.loadTexture("terrain/" + b));
		blendmapTex = new TerrainTexture(Mift.loader.loadTexture(blend));
		texturePack = new TerrainTexturePack(backgroundTex, tex1, tex2, tex3);
		terrain = new Terrain(0, 0, texturePack, blendmapTex);
		Mift.instance_count++;
	}
	
	public static void newWorld() {
		Mift.terrain = new TerrainCreator(0, 0, "deadGrass1", "grassy2", "path", "path", "blendMap2").getTerrain(); //create the terrain
		Mift.entities.clear();
		Mift.enemies.clear();
		Mift.entities = Mift.terrain.generateEntities(Mift.entities); //spawn the entities on it
		Mift.entities = Mift.terrain.generateEnemies(move_factor.MOVE_TOWARDS_WHEN_CLOSE, Mift.entities); // spawn the enemies on it
		Mift.player = new EntityCreator().createPlayer(new Vector3f(500, Mift.terrain.getHeightOfTerrain(500,  500), 500), new Vector3f(0, 90, 0));
		Mift.entities.add(Mift.player);
		Mift.camera.setPlayer(Mift.player);
		Mift.overheadCamera.setPlayer(Mift.player);
		Mift.setPaused(false);
		Mift.hasMadeWorld = true;
		Mift.sunLight.resetWorldTime();
	}
	
	public static void loadWorld(Terrain terrain, List<Entity> entities, List<Enemy> enemies, Player player, int day, int time) {
		Mift.terrain = terrain;
		Mift.entities = entities;
		Mift.enemies = enemies;
		Mift.player = player;
		Mift.entities.add(Mift.player);
		Mift.camera.setPlayer(Mift.player);
		Mift.overheadCamera.setPlayer(Mift.player);
		Mift.setPaused(false);
		Mift.hasMadeWorld = true;
		Mift.sunLight.setDay(day);
		Mift.sunLight.setDayTime(time);
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}