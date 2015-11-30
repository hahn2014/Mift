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
import org.lwjgl.util.vector.Vector4f;

import entities.Camera;
import entities.Enemy;
import entities.Entity;
import entities.EntityCreator;
import entities.EntityTypeHolder;
import entities.Light;
import entities.MoveType.move_factor;
import entities.MoveTypeHolder;
import entities.OverheadCamera;
import entities.Player;
import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.Text;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.TexturedModel;
import particles.ParticleEmitter;
import particles.ParticleHolder;
import renderEngine.DisplayManager;
import renderEngine.DisplayManager.QUALITY;
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
 * Already surpassed 4k lines of code while trending currently at 4670! Keep up
 * the good work - Mift Build 40
 * 
 * @author Bryce Hahn, Mason Cluff
 * @since 1.0
 */
public class Mift {
	public static final String BUILD = "40";
	public static final String RELEASE = "1";
	public static final String RELEASE_TITLE = "Alpha";
	public static final String NAME = "Mift";
	
	public static final Boolean fps = false;
	private static Random random;
	
	//camera stuff
	public static Camera camera;
	public static OverheadCamera overheadCamera;
	
	private static MousePicker defaultMouse;
	private static MousePicker overheadMouse;
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	private static Terrain terrain;
	private static Loader loader = new Loader();
	
	public static enum GAMESTATE {
		MENU,
		INGAME,
		SETTINGS
	};
	public static GAMESTATE currentMenu = GAMESTATE.INGAME;
	
	public static void main(String[] args) {
		new ImportLibs(); //link the natives for lwjgl
		random = new Random();
		random.setSeed(Sys.getTime());
		//initialize the quality of the game while loading
		DisplayManager.createDisplay(QUALITY.ULTRA); // default quality value
		List<String> arg = Arrays.asList(args);
		DisplayManager.debugPolys =  arg.contains("-d"); //draw quality from arguments from startup command
		if (arg.contains("-Qlight")) {
			DisplayManager.quality = QUALITY.LIGHT;
		} else if (arg.contains("-Qmoderate")) {
			DisplayManager.quality = QUALITY.MODERATE;
		} else if (arg.contains("-Qultra")) {
			DisplayManager.quality = QUALITY.ULTRA;
		}
		//test graphics import
		MemoryData.sendGraphicsInformationToFile();
		//init main objects
		EntityCreator entityCreater = new EntityCreator();
		FPSCounter fpsCounter = new FPSCounter();
		Text.init();
		FontHolder fontHolder = new FontHolder();
		EntityTypeHolder entityTypeHolder = new EntityTypeHolder();
		MoveTypeHolder moveTypeHolder = new MoveTypeHolder();
		
		// ********* TERRAIN TEXTURE STUFF **********

		terrain = new TerrainCreator(0, -1, "lunar_surface", "dirt", "lunar_surface", "path", "blendMap", "heightmap").getTerrain();
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);

		// ****************** NORMAL MAP MODELS ************************

		TexturedModel barrelModel = entityCreater.createNormalTexturedModel("barrel", "barrelNormal", 10, 0.5f);
		TexturedModel crateModel = entityCreater.createNormalTexturedModel("crate", "crateNormal", 10, 0.5f);
		TexturedModel boulderModel = entityCreater.createNormalTexturedModel("boulder", "boulderNormal", 10, 0.5f);
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(75, 10, -75), 0, 0, 0, 1f));
		normalMapEntities.add(new Entity(boulderModel, new Vector3f(85, 10, -75), 0, 0, 0, 1f));
		normalMapEntities.add(new Entity(crateModel, new Vector3f(65, 10, -75), 0, 0, 0, 0.04f));

		// ************ ENTITIES *******************
		entities = entityCreater.generateObjects(entities, terrain, 0);

		// ******************* OTHER SETUP ***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		MasterRenderer renderer = new MasterRenderer();

		Player player = new EntityCreator().createPlayer(new Vector3f(30, 5, -90), new Vector3f(0, 100, 0));
		entities.add(player);
		camera = new Camera(player);
		overheadCamera = new OverheadCamera(player);
		
		// *********** ENEMY CREATION *******************************
		
		for (int i = 0; i < 5; i++) {
			enemies.add(new EntityCreator().createRandomEnemy(move_factor.MOVE_CIRCLES));
			entities.add(enemies.get(i));
		}
		
		// ******************* EXTRAS ****************
		
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer();
		defaultMouse = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		overheadMouse = new MousePicker(overheadCamera, renderer.getProjectionMatrix(), terrain);
		
		// ********** Water Renderer Set-up ************************

		WaterFrameBuffers buffers = new WaterFrameBuffers(DisplayManager.quality);
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, -75, 0);
		waters.add(water);

		// **************** Game Audio Test *********************
		
		//SoundTest soundTester = new SoundTest();
		
		Display.setTitle(NAME + " Release " + RELEASE + " b" + BUILD);
		
		// **************** Text Rendering *********************
		
		GUIText[] texts = new GUIText[4];
		
		texts[0] = new GUIText(Mift.NAME + " Alpha Build " + Mift.RELEASE + "." + Mift.BUILD, 1.25f, fontHolder.getBerlinSans(), new Vector2f(0, 0), 0.25f, ALIGNMENT.LEFT);
			texts[0].setColor(255, 255, 255);
		texts[1] = new GUIText("", 2.0f, fontHolder.getCandara(), new Vector2f(0.85f, 0.0f), 0.25f, ALIGNMENT.CENTER);
			texts[1].setColor(255, 223, 0);
		texts[2] = new GUIText("", 0.75f, fontHolder.getArial(), new Vector2f(0.75f, 0.1f), 0.25f, ALIGNMENT.CENTER);
			texts[2].setColor(200, 200, 200);
		texts[3] = new GUIText("Default Entity", 0.75f, fontHolder.getBerlinSans(), new Vector2f(0.27f, 0.93f), 0.5f, ALIGNMENT.CENTER);
			texts[3].setColor(220, 220, 220);
		
		// **************** PARTICLE SYSTEM *********************
		ParticleHolder.init(loader, renderer.getProjectionMatrix());
		ParticleEmitter particleEmitter = new ParticleEmitter(50, 35, 0.45f, 5, 0.4f);
		particleEmitter.randomizeRotation();
		particleEmitter.setDirection(new Vector3f(0, 1, 0),  0.1f);
		particleEmitter.setLifeError(0.1f);
		particleEmitter.setSpeedError(0.4f);
		particleEmitter.setScaleError(0.4f);
		
		// **************** Game Loop Below *********************

		while (!Display.isCloseRequested()) {
			if (currentMenu == GAMESTATE.MENU) {
				
			} else if (currentMenu == GAMESTATE.INGAME) {
				// If we're drawing close to the camera, or the top of the player's head,
				// remove the player so it doesn't get drawn and we can see it in first person view
				if (camera.distanceFromPlayer < 1) {
					if (entities.contains(player)) {
						entities.remove(player);
					}
				} else {
					if (!entities.contains(player)) {
						entities.add(player);
					}
				}
				player.move(terrain);
				if (player.isOverhead()) {
					overheadCamera.move();
					overheadCamera.rotate();
					overheadCamera.getClicks();
					overheadMouse.update(true);
				} else {
					camera.move();
					camera.rotate();
					camera.getClicks();
					defaultMouse.update(false);
				}
				particleEmitter.generateParticles(new Vector3f(player.getPosition().x, player.getPosition().y + 7, player.getPosition().z));
				ParticleHolder.update();
				
				
				for (int i = 0; i < normalMapEntities.size(); i++) {
					normalMapEntities.get(i).setRotY(normalMapEntities.get(i).getRotY() + 2);
				}
				
				for (Enemy e : enemies) {
					if (e != null) {
						e.move(player);
						particleEmitter.generateParticles(new Vector3f(e.getPosition().x, e.getPosition().y + 6, e.getPosition().z));
					}
				}
				
				GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
				if (player.isOverhead() == false) {
					// render reflection texture
					buffers.bindReflectionFrameBuffer();
					float distance = 2 * (camera.getPosition().y - water.getHeight());
					camera.getPosition().y -= distance;
					camera.invertPitch();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
							new Vector4f(0, 1, 0, -water.getHeight() + 1));
					camera.getPosition().y += distance;
					camera.invertPitch();
		
					// render refraction texture
					buffers.bindRefractionFrameBuffer();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, camera,
							new Vector4f(0, -1, 0, water.getHeight()));
		
					// render to screen
					GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
					buffers.unbindCurrentFrameBuffer();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
					waterRenderer.render(waters, camera, sun);
				} else {
					// render reflection texture
					buffers.bindReflectionFrameBuffer();
					float distance = 2 * (overheadCamera.getPosition().y - water.getHeight());
					overheadCamera.getPosition().y -= distance;
					overheadCamera.invertPitch();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, overheadCamera,
							new Vector4f(0, 1, 0, -water.getHeight() + 1));
					overheadCamera.getPosition().y += distance;
					overheadCamera.invertPitch();
		
					// render refraction texture
					buffers.bindRefractionFrameBuffer();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, overheadCamera,
							new Vector4f(0, -1, 0, water.getHeight()));
		
					// render to screen
					GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
					buffers.unbindCurrentFrameBuffer();
					renderer.renderScene(entities, normalMapEntities, terrains, lights, overheadCamera, new Vector4f(0, -1, 0, 100000));
					waterRenderer.render(waters, overheadCamera, sun);
				}
				ParticleHolder.renderParticles(player.isOverhead());
				guiRenderer.render(guiTextures);
			} else if (currentMenu == GAMESTATE.SETTINGS) {
				
			}
			fpsCounter.updateCounter();
			texts[1].setText((int) (fpsCounter.getFPS()) + "");
			texts[2].setText(updateDebugText(player));
			if (player.isOverhead()) {
				StringBuilder sb = new StringBuilder();
				sb.append(entityTypeHolder.get(entityTypeHolder.rotateReverse(overheadCamera.placerType)).getName());
				sb.append(" <--1-- [");
				sb.append(entityTypeHolder.get(overheadCamera.placerType).getName());
				sb.append("] --2--> ");
				sb.append(entityTypeHolder.get(entityTypeHolder.rotate(overheadCamera.placerType)).getName());
				sb.append(" ~ ");
				sb.append(moveTypeHolder.get(moveTypeHolder.rotateReverse(overheadCamera.move_type)).getName());
				sb.append(" <--3-- [");
				sb.append(moveTypeHolder.get(overheadCamera.move_type).getName());
				sb.append("] --4--> ");
				sb.append(moveTypeHolder.get(moveTypeHolder.rotate(overheadCamera.move_type)).getName());
				sb.append(" ~ (");
				sb.append(moveTypeHolder.get(overheadCamera.move_type).getDescription());
				sb.append(")");
				texts[3].setText(sb.toString());
			} else {
				texts[3].setText("");
			}
			Text.render();
			DisplayManager.updateDisplay();
		}

		// ********* Clean Up Below **************
		
		ParticleHolder.cleanUp();
		Text.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
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
		return sb.toString();
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
	
	public static Terrain getTerrain() {
		return terrain;
	}
	
	public static Loader getLoader() {
		return loader;
	}
	
	public static void addEnemy(Enemy e) {
		enemies.add(e);
		entities.add(e);
	}
}