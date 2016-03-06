package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import attacks.AttackHolder;
import attacks.AttacksRenderer;
import attacks.fireball.FireballHolder;
import attacks.waterball.WaterballHolder;
import entities.Camera;
import entities.Enemy;
import entities.Entity;
import entities.EntityCreator;
import entities.EntityTypeHolder;
import entities.Light;
import entities.MoveTypeHolder;
import entities.OverheadCamera;
import entities.Player;
import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.Text;
import guis.GuiRenderer;
import guis.GuiTexture;
import guis.hud.HUDCreator;
import guis.hud.HUDRenderer;
import guis.menu.MenuRenderer;
import particles.ParticleEmitter;
import particles.ParticleHolder;
import particles.ParticleTexture;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import terrains.Terrain;
import terrains.TerrainCreator;
import toolbox.FPSCounter;
import toolbox.MemoryData;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

/**
 * Already surpassed 10k lines of
 * cumulative code! Keep up the
 * good work - Mift Build 53
 * 
 * @author Bryce Hahn, Mason Cluff
 * @since 1.0
 */
public class Mift {
	public static final String BUILD = "53";
	public static final String RELEASE = "1";
	public static final String RELEASE_TITLE = "Alpha";
	public static final String NAME = "Mift";
	
	public static final Boolean fps = false;
	private static final boolean isNight = false;
	private static boolean isPaused = false;
	private static Random random;
	
	public static int instance_count = 0;
	public static int game_quality = 1;
	//camera stuff
	public static Camera camera;
	public static OverheadCamera overheadCamera;
	
	private static MousePicker defaultMouse;
	private static MousePicker overheadMouse;
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	public static List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
	public static List<Terrain> terrains = new ArrayList<Terrain>();
	public static ParticleEmitter particleEmitter;
	private static Loader loader = new Loader();
	private static HUDCreator hudCreator;

	public static FontHolder fontHolder;
	public static EntityTypeHolder entityTypeHolder;
	public static MoveTypeHolder moveTypeHolder;
	public static AttackHolder attackHolder;
	public static FireballHolder fireballHolder;
	public static WaterballHolder waterballHolder;
	public static Player player;
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		new ImportLibs(); //link the natives for lwjgl
		random = new Random();
		random.setSeed(Sys.getTime());
		//initialize the quality of the game while loading
		DisplayManager.createDisplay(game_quality);
		List<String> arg = Arrays.asList(args);
		DisplayManager.debugPolys =  arg.contains("-d"); //draw quality from arguments from startup command
		//test graphics import
		MemoryData.sendGraphicsInformationToFile();
		//init main objects
		FPSCounter fpsCounter = new FPSCounter();
		Text.init();
		fontHolder = new FontHolder();
		entityTypeHolder = new EntityTypeHolder();
		moveTypeHolder = new MoveTypeHolder();
		attackHolder = new AttackHolder();
		fireballHolder = new FireballHolder();
		waterballHolder = new WaterballHolder();
		
		// ********* TERRAIN TEXTURE STUFF **********
		Terrain terrain = new TerrainCreator(0, 0, "deadGrass1", "grassy2", "path", "path", "path", "blendMap2").getTerrain(); //create the terrain
		entities = terrain.generateEntities(terrains, entities); //spawn the entities on it
		entities = terrain.generateEnemies(terrains, entities, game_quality); // spawn the enemies on it
		terrains.add(terrain); //add it to the array
		
		terrain = new TerrainCreator(1, 1, "deadGrass1", "grassy2", "path", "path", "path", "blendMap").getTerrain(); //create the terrain
		entities = terrain.generateEntities(terrains, entities); //spawn the entities on it
		entities = terrain.generateEnemies(terrains, entities, game_quality); // spawn the enemies on it
		terrains.add(terrain); //add it to the array

		// ******************* OTHER SETUP ***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(1000000, 1000000, -1000000), new Vector3f(1.3f, 1.3f, 1.3f));
		Mift.instance_count++;
		lights.add(sun);


		player = new EntityCreator(terrains).createPlayer(new Vector3f(200, terrains.get(0).getHeightOfTerrain(200,  200), 200), new Vector3f(0, 100, 0), loader);
		entities.add(player);
		camera = new Camera(player);
		overheadCamera = new OverheadCamera(player, terrains);

		MasterRenderer renderer = new MasterRenderer(game_quality);
		
		// ******************* EXTRAS ****************
		
		GuiRenderer guiRenderer = new GuiRenderer();
		defaultMouse = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		overheadMouse = new MousePicker(overheadCamera, renderer.getProjectionMatrix(), terrain);
		
		// ********** Water Renderer Top Side ************************

		WaterFrameBuffers topBuffers = new WaterFrameBuffers(game_quality);
		WaterShader waterShaderTop = new WaterShader();
		WaterRenderer topWaterRenderer = new WaterRenderer(waterShaderTop, renderer.getProjectionMatrix(), topBuffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile topWater = new WaterTile(500, 500);
		instance_count++;
		waters.add(topWater);
		
		Display.setTitle(NAME + " Release " + RELEASE + " b" + BUILD);
		
		// **************** Text Rendering *********************
		
		GUIText[] texts = new GUIText[5];
		
		texts[0] = new GUIText(Mift.NAME + " Alpha Build " + Mift.RELEASE + "." + Mift.BUILD, 1.25f, fontHolder.getBerlinSans(), new Vector2f(0, 0), 0.25f, ALIGNMENT.LEFT);
			texts[0].setColor(255, 255, 255);
		texts[1] = new GUIText("", 2.0f, fontHolder.getCandara(), new Vector2f(0.85f, 0.0f), 0.25f, ALIGNMENT.CENTER);
			texts[1].setColor(255, 223, 0);
		texts[2] = new GUIText("", 0.75f, fontHolder.getArial(), new Vector2f(0.75f, 0.1f), 0.25f, ALIGNMENT.CENTER);
			texts[2].setColor(200, 200, 200);
		texts[3] = new GUIText("Default Entity", 0.75f, fontHolder.getBerlinSans(), new Vector2f(0.27f, 0.93f), 0.5f, ALIGNMENT.CENTER);
			texts[3].setColor(220, 220, 220);
		texts[4] = new GUIText("Default Attack", 0.75f, fontHolder.getBerlinSans(), new Vector2f(0.27f, 0.93f), 0.5f, ALIGNMENT.CENTER);
			texts[4].setColor(220, 220, 220);
		
		// **************** PARTICLE SYSTEM *********************
		ParticleHolder.init(loader, renderer.getProjectionMatrix());
		
		ParticleTexture pt = new ParticleTexture(loader.loadParticleTexture("star"), 4, true);
		ParticleEmitter pe = new ParticleEmitter(pt, 120, 10, 0.1f, 1, 1.6f);
		pe.setLifeError(0.1f);
		pe.setSpeedError(0.25f);
		pe.setScaleError(0.5f);
		pe.randomizeRotation();
		
		// ********************* ATTACKS ************************\
		AttacksRenderer attackRenderer = new AttacksRenderer();
		
		// **************** HUD *********************************
		HUDRenderer hudRenderer = new HUDRenderer();
		hudCreator = new HUDCreator(loader, false);
		
		MenuRenderer pauseRenderer = new MenuRenderer();
		
		// **************** Game Loop Below *********************

		while (!Display.isCloseRequested()) {
			if (isPaused) { //render pause
				pauseRenderer.render();
			} else { //render in game
				player.move(terrain);
				//have all enemies in the map move around, even the ones spawned by player
				for (Enemy e : enemies) {
					if (e != null) {
						e.move(player);
					}
				}
				GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
				if (player.isOverhead() == false) {
					renderer.renderCallStandardView(topBuffers, camera, defaultMouse, topWaterRenderer, topWater, lights, entities, null, terrains, waters, sun, player, isNight);
					//hudCreator.update(camera);
				} else {
					renderer.renderCallOverheadView(topBuffers, overheadCamera, overheadMouse, topWaterRenderer, topWater, lights, entities, null, terrains, waters, sun, isNight);
					//hudCreator.update(overheadCamera);
				}
				attackRenderer.render(fireballHolder.getAll(), waterballHolder.getAll());
				//ParticleHolder.renderParticles(player.isOverhead());
				guiRenderer.render(guiTextures);
				hudRenderer.render(hudCreator.getTextures(), hudCreator.getCompass());
				fpsCounter.updateCounter();
				texts[1].setText((int) (fpsCounter.getFPS()) + "");
				texts[2].setText(updateDebugText(player));
				texts[3].setText(updateModelPlacerText(entityTypeHolder, moveTypeHolder, player));
				texts[4].setText(updateAttackText(attackHolder, player));
				Text.render();
			}
			DisplayManager.updateDisplay();
		}

		// ********* Clean Up Below **************
		
		//ParticleHolder.cleanUp();
		Text.cleanUp();
		topBuffers.cleanUp();
		waterShaderTop.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
	private static String updateDebugText(Player player) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		//update string
		StringBuilder sb = new StringBuilder();
		sb.append(" Developer Mode: " + DisplayManager.debugPolys);
		sb.append("  ~ Resolution: " + DisplayManager.getRes());
		sb.append("  ~ Fullscreened: " + DisplayManager.isFullscreen);
		if (player.isOverhead()) {
			sb.append("  ~ Camera Distance: " + (int)overheadCamera.distanceFromPlayer);
			sb.append("  ~ Camera POV: Over Head Perspective");
			sb.append("  ~ Camera Position: [" + df.format(overheadCamera.getPosition().x)
					+ ", " + df.format(overheadCamera.getPosition().y) + ", "
					+ df.format(overheadCamera.getPosition().z) + "]");
			sb.append("  ~ Camera View Position: [" + df.format(overheadCamera.getView().x)
					+ ", " + df.format(overheadCamera.getView().y) + ", "
					+ df.format(overheadCamera.getPosition().z) + "]");
		} else {
			sb.append("  ~ Camera Distance: " + (int)camera.distanceFromPlayer);
			sb.append("  ~ Camera POV: " + (camera.distanceFromPlayer == 0
					? "First Person Perspective" : "Third Person Perspective"));
			sb.append("  ~ Camera Position: [" + df.format(camera.getPosition().x)
					+ ", " + df.format(camera.getPosition().y) + ", "
					+ df.format(camera.getPosition().z) + "]");
			sb.append("  ~ Camera View Position: [" + df.format(camera.getView().x)
					+ ", " + df.format(camera.getView().y) + ", "
					+ df.format(camera.getPosition().z) + "]");
		}
		sb.append("  ~ Player Position: [" + df.format(player.getPosition().x)
				+ ", " + df.format(player.getPosition().y) + ", "
				+ df.format(player.getPosition().z) + "]");
		sb.append(" ~ Player Rotation: [" + df.format(player.getRotX())
				+ ", " + df.format(player.getRotY()) + ", "
				+ df.format(player.getRotZ()) + "]");
		sb.append("  ~ Used Memory: " + (MemoryData.getFreeMemory() / MemoryData.getUsedMemory())
				+ "% (" + MemoryData.getUsedMemory() + "MB) of " + MemoryData.getFreeMemory() + "MB");
		sb.append("  ~ Allocated Memory: " + (MemoryData.getMaxMemory() / MemoryData.getTotalMemory())
				+ "% (" + MemoryData.getTotalMemory() + "MB) of " + MemoryData.getMaxMemory() + "MB");
		sb.append("  ~ Graphics Card: " + MemoryData.getGraphicsCard());
		sb.append("  ~ Graphics Memory: " + MemoryData.getGraphicsMemory());
		sb.append("  ~ Native Resolution: " + MemoryData.getNativeResolution());
		sb.append("  ~ Instances Within World: " + instance_count);
		sb.append("  ~ Compas Rotation: " + hudCreator.getCompass().compassTexture.getRotation());
		return sb.toString();
	}
	
	private static String updateAttackText(AttackHolder at, Player player) {
		if (getCamera().isFPS()) {
			StringBuilder sb = new StringBuilder();
			sb.append(at.get(at.rotateReverse(player.attackType)).getName());
			sb.append(" <--Q-- [");
			sb.append(at.get(player.attackType).getName());
			sb.append("] --E--> ");
			sb.append(at.get(at.rotate(player.attackType)).getName());
			sb.append(" ~ ");
			sb.append(at.get(player.attackType).getDefinition());
			return sb.toString();
		} else {
			return "";
		}
	}
	
	private static String updateModelPlacerText(EntityTypeHolder eth, MoveTypeHolder mth, Player player) {
		if (player.isOverhead()) {
			StringBuilder sb = new StringBuilder();
			sb.append(eth.get(eth.rotateReverse(overheadCamera.placerType)).getName());
			sb.append(" <--1-- [");
			sb.append(eth.get(overheadCamera.placerType).getName());
			sb.append("] --2--> ");
			sb.append(eth.get(eth.rotate(overheadCamera.placerType)).getName());
			sb.append(" ~ ");
			sb.append(mth.get(mth.rotateReverse(overheadCamera.move_type)).getName());
			sb.append(" <--3-- [");
			sb.append(mth.get(overheadCamera.move_type).getName());
			sb.append("] --4--> ");
			sb.append(mth.get(mth.rotate(overheadCamera.move_type)).getName());
			sb.append(" ~ (");
			sb.append(mth.get(overheadCamera.move_type).getDescription());
			sb.append(")");
			return sb.toString();
		} else {
			return "";
		}
	}
	
	public static Camera getCamera() {
		return camera;
	}
	
	public static OverheadCamera getOverheadCamera() {
		return overheadCamera;
	}
	
	public static MousePicker getMousePicker(boolean isOverhead) {
		return (isOverhead ? overheadMouse : defaultMouse);
	}
	
	public static Terrain getTerrain(int gridX, int gridZ) {
		for (Terrain terrain : terrains) {
			if (terrain.getX() ==  gridX && terrain.getZ() == gridZ) {
				return terrain;
			}
		}
		return terrains.get(0);
	}
	
	public static Loader getLoader() {
		return loader;
	}
	
	public static void addEnemy(Enemy e) {
		entities.add(e);
		enemies.add(e);
	}
	
	public static void updateEntities(Player player) {
		// If we're drawing close to the camera, or the top of the player's head,
		// remove the player so it doesn't get drawn and we can see it in first person view
		if (camera.distanceFromPlayer < 1) {
			player.setRenderable(false);
		} else {
			player.setRenderable(true);
		}
	}
	
	public boolean isPaused() {
		return isPaused;
	}
	
	public void setPaused(Boolean paused) {
		isPaused = paused;
	}
}