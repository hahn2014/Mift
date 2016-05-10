package main;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import attacks.AttackHolder;
import attacks.AttackUpdater;
import entities.Camera;
import entities.Enemy;
import entities.Entity;
import entities.EntityTypeHolder;
import entities.Light;
import entities.MoveTypeHolder;
import entities.OverheadCamera;
import entities.Player;
import entities.Sun;
import fontCreator.FontHolder;
import fontCreator.GUIText;
import fontCreator.GUIText.ALIGNMENT;
import fontRender.TextRenderer;
import guis.GuiRenderer;
import guis.GuiTexture;
import guis.hud.HUDCreator;
import guis.hud.HUDRenderer;
import guis.menu.CreditsRenderer;
import guis.menu.DeadRenderer;
import guis.menu.MenuRenderer;
import guis.menu.SettingsRenderer;
import guis.menu.WorldLoadRenderer;
import io.Logger;
import io.SettingHolder;
import io.Settings;
import myo.MyoSetup;
import particles.ParticleEmitter;
import particles.ParticleHolder;
import particles.ParticleTexture;
import postProcessing.FBO;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import toolbox.FPSCounter;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;

/**
 * Already surpassed 16k lines of
 * cumulative code! - Mift Build 64
 * 
 * @author Bryce Hahn, Mason Cluff
 * @since 1.0 - 06/12/2015
 */
public class Mift {
	public static final String BUILD = "64";
	public static final String RELEASE = "1";
	public static final String RELEASE_TITLE = "Pre-Alpha";
	public static final String NAME = "Mift";
	
	private static boolean isPaused = true;
	private static SecureRandom random;
	public static boolean hasMadeWorld = false;
	private static boolean textsDissabled = false;
	
	public static int instance_count = 0;
	public static final int dayTimeMultiplier = 1;
	public static int menuIndex = 0;
	
	//camera stuff
	public static Camera camera;
	public static OverheadCamera overheadCamera;
	
	public static Sun sunLight;
	private static MousePicker defaultMouse;
	private static MousePicker overheadMouse;
	private static TextRenderer textRenderer;
	public static List<Entity> entities = new ArrayList<Entity>();
	public static List<Enemy> enemies = new ArrayList<Enemy>();
	public static List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
	public static List<GUIText> texts = new ArrayList<GUIText>();
	public static Terrain terrain = null;
	public static ParticleEmitter particleEmitter;
	public static Loader loader = new Loader();
	public static Settings settings;

	public static HUDCreator hudCreator;
	public static MasterRenderer renderer;
	public static EntityTypeHolder entityTypeHolder;
	public static MoveTypeHolder moveTypeHolder;
	public static AttackHolder attackHolder;
	public static Player player;
	
	private static DisplayManager dm;
	
	@SuppressWarnings({ "static-access", "unused" })
	public static void main(String[] args) {
		new ImportLibs(); //link the natives for lwjgl
		new SettingHolder(); //generate and import settings
		random = new SecureRandom();
		random.setSeed(System.currentTimeMillis());
		//initialize the quality of the game while loading
		dm = new DisplayManager();
		settings = new Settings();
		dm.createDisplay();
		
		// LOOK HERE MASON
		MyoSetup.init(false);
		
		//init main objects
		FPSCounter fpsCounter = new FPSCounter();
		entityTypeHolder = new EntityTypeHolder();
		moveTypeHolder = new MoveTypeHolder();
		attackHolder = new AttackHolder();
		textRenderer = new TextRenderer();
		
		// ******************* LIGHT SETUP ***************
		List<Light> lights = new ArrayList<Light>();
		sunLight = new Sun(new Vector3f(Terrain.SIZE, 50, Terrain.SIZE / 2), 203, 133, 173, 1.0f); //about 12:00am
		Mift.instance_count++;
		lights.add(sunLight);
		
		// ******************* PLAYER SETUP ***************
		camera = new Camera();
		overheadCamera = new OverheadCamera();
		renderer = new MasterRenderer();
		
		// ******************* EXTRAS ****************
		GuiRenderer guiRenderer = new GuiRenderer();
		defaultMouse = new MousePicker(camera, renderer.getProjectionMatrix());
		overheadMouse = new MousePicker(overheadCamera, renderer.getProjectionMatrix());
		
		// ********** Water Renderer Top Side ************************
		WaterFrameBuffers topBuffers = new WaterFrameBuffers();
		WaterShader waterShaderTop = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(waterShaderTop, renderer.getProjectionMatrix(), topBuffers);
		WaterTile water = new WaterTile(500, 500);
		instance_count++;
		
		Display.setTitle(NAME + " Release " + RELEASE + " b" + BUILD);
		
		// **************** Text Rendering *********************
		texts.add(new GUIText(textRenderer, "", 1.25f, FontHolder.getBerlinSans(), new Vector2f(0, 0), 0.25f, ALIGNMENT.LEFT));
			texts.get(0).setColor(255, 255, 255);
		texts.add(new GUIText(textRenderer, "", 2.0f, FontHolder.getCandara(), new Vector2f(0.85f, 0.0f), 0.25f, ALIGNMENT.CENTER));
			texts.get(1).setColor(255, 223, 0);
		texts.add(new GUIText(textRenderer, "", 0.75f, FontHolder.getArial(), new Vector2f(0.75f, 0.1f), 0.25f, ALIGNMENT.CENTER));
			texts.get(2).setColor(200, 200, 200);
		texts.add(new GUIText(textRenderer, "", 0.75f, FontHolder.getBerlinSans(), new Vector2f(0.27f, 0.93f), 0.5f, ALIGNMENT.CENTER));
			texts.get(3).setColor(220, 220, 220);
		texts.add(new GUIText(textRenderer, "", 0.75f, FontHolder.getBerlinSans(), new Vector2f(0.27f, 0.93f), 0.5f, ALIGNMENT.CENTER));
			texts.get(4).setColor(220, 220, 220);
		texts.add(new GUIText(textRenderer, "", 1.25f, FontHolder.getBerlinSans(), new Vector2f(0.5f, 0), 0.25f, ALIGNMENT.LEFT));
			texts.get(5).setColor(100, 100, 255);
		
		// **************** PARTICLE SYSTEM *********************
		ParticleHolder.init(loader, renderer.getProjectionMatrix());
		
		ParticleTexture pt = new ParticleTexture(loader.loadParticleTexture("star"), 4, true);
		ParticleEmitter pe = new ParticleEmitter(pt, 120, 10, 0.1f, 1, 1.6f);
		pe.setLifeError(0.1f);
		pe.setSpeedError(0.25f);
		pe.setScaleError(0.5f);
		pe.randomizeRotation();

		// **************** ATTACKS *****************************
		AttackUpdater attackUpdater = new AttackUpdater();
		
		// **************** HUD *********************************
		HUDRenderer hudRenderer = new HUDRenderer();
		hudCreator = new HUDCreator(loader, false);
		
		//****************** Menu Rendering *******************
		MenuRenderer pauseRenderer = new MenuRenderer();
		SettingsRenderer settingRenderer = new SettingsRenderer();
		WorldLoadRenderer worldLoadingRenderer = new WorldLoadRenderer();
		DeadRenderer deadRenderer = new DeadRenderer();
		CreditsRenderer creditsRenderer = new CreditsRenderer();
		
		// ******************POST PROCESSING ********************
		FBO fbo1 = new FBO(Display.getWidth(), Display.getHeight(), FBO.DEPTH_RENDER_BUFFER);
		PostProcessing.init(false, true);
		
		// **************** Game Loop Below *********************
		while (!Display.isCloseRequested()) {
			if (isPaused) { 
				if (menuIndex == 0) {//render pause main menu
					pauseRenderer.update();
				} else if (menuIndex == 1) { //render settings
					settingRenderer.update();
				} else if (menuIndex == 2) { //render load world
					worldLoadingRenderer.update();
				} else if (menuIndex == 3) { //render death
					deadRenderer.update();
				} else if (menuIndex == 4) { //render credits
					creditsRenderer.update();
				} else {
					Logger.error("Something went wrong when rendering menu index of " + menuIndex);
					System.exit(-1);
				}
			} else { //render in game
				if (SettingHolder.get("cp_myo_enabled").getValueB()) {
					MyoSetup.update(DisplayManager.getFrameTimeSeconds());
				}
				player.move();
				sunLight.move();
				SkyboxRenderer.move();
				
				//pe.generateParticles(player.getPosition());
				
				for (Enemy e : enemies) {
					if (e != null) {
						e.move(player);
					}
				}
				GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
				//render calls
				if (player.isOverhead() == false) { //3rd to 1st person view
					renderer.renderCallStandardView(topBuffers, camera, defaultMouse, waterRenderer, water, lights, entities, null, sunLight, attackHolder, fbo1);
					ParticleHolder.renderParticles(camera);
					if (!SettingHolder.get("cg_theatrical").getValueB()) {hudCreator.update(camera);}
				} else { //overhead view
					renderer.renderCallOverheadView(topBuffers, overheadCamera, overheadMouse, waterRenderer, water, lights, entities, null, sunLight, attackHolder, fbo1);
					ParticleHolder.renderParticles(overheadCamera);
					if (!SettingHolder.get("cg_theatrical").getValueB()) {hudCreator.update(overheadCamera);}
				}
				
				attackUpdater.update(attackHolder);
				
				if (SettingHolder.get("cg_fps").getValueB()) fpsCounter.updateCounter();
				
				if (!SettingHolder.get("cg_theatrical").getValueB()) {
					guiRenderer.render(guiTextures);
					
					//if (camera.distanceFromPlayer == 0) {hudRenderer.render(hudCreator.getTextures(), hudCreator.getCompass());}
					
					texts.get(0).setText(Mift.NAME + " " + Mift.RELEASE_TITLE + " Build " + Mift.RELEASE + "." + Mift.BUILD);
					texts.get(1).setText((SettingHolder.get("cg_fps").getValueB() ? (int)(fpsCounter.getFPS()) + "" : ""));
					texts.get(2).setText(SettingHolder.get("cg_developer").getValueB() ? updateDebugText(player) : "");
					texts.get(3).setText(player.isOverhead() ? updateModelPlacerText(entityTypeHolder, moveTypeHolder, player) : "");
					texts.get(4).setText(camera.isFPS() ? updateAttackText(attackHolder, player) : "");
					texts.get(5).setText("Day " + sunLight.getDay() + "  ~" + sunLight.getCurrentTimeDebug());
					textRenderer.render(texts);
				}
				
				if (textsDissabled) {
					enableAllTexts(true);
				}
			}
			DisplayManager.updateDisplay();
			
		}

		// ********* Clean Up Below **************
		PostProcessing.cleanUp();
		ParticleHolder.cleanUp();
		fbo1.cleanUp();
		textRenderer.cleanUp();
		topBuffers.cleanUp();
		waterShaderTop.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
	private static String updateDebugText(Player player) {
		StringBuilder sb = new StringBuilder();
		sb.append(" Developer Mode: " + SettingHolder.get("cg_developer").getValueB());
		sb.append("  ~ Resolution: " + DisplayManager.getRes());
		sb.append("  ~ Fullscreened: " + SettingHolder.get("cg_fullscreened").getValueB());
		sb.append("  ~ Camera Distance: " + (player.isOverhead() ? Integer.toString((int)overheadCamera.distanceFromPlayer) : Integer.toString((int)camera.distanceFromPlayer)));
		sb.append("  ~ Camera POV: " + (player.isOverhead() ? "Over Head Perspective" : (camera.distanceFromPlayer == 0 ? "First Person Perspective" : "Third Person Perspective")));
		sb.append("  ~ Camera FOV: " + SettingHolder.get("cg_fov").getValueI());
		sb.append("  ~ Camera Position : " + (player.isOverhead() ? overheadCamera.getPositionDebug() : camera.getPositionDebug()));
		sb.append("  ~ Camera View Position: " + (player.isOverhead() ? overheadCamera.getViewDebug() : camera.getViewDebug()));
		sb.append("  ~ Pointer Terrain Pos: " + (player.isOverhead() ? ("null") : (camera.distanceFromPlayer == 0 ? defaultMouse.getTerrainPointDebug(new Vector2f(Display.getWidth() / 2, Display.getHeight() / 2)) : "null")));
		sb.append("  ~ Player Position: " + player.getPositionDebug());
		sb.append("  ~ Player Rotation: " + player.getRotationDebug());
		sb.append("  ~ Player Health: " + player.getHealth());
		sb.append("  ~ Instances Within World: " + instance_count);
		return sb.toString();
	}
	
	private static String updateAttackText(AttackHolder at, Player player) {
		StringBuilder sb = new StringBuilder();
		sb.append(at.get(at.rotateReverse(player.attackType)).getName());
		sb.append(" <--Q-- [");
		sb.append(at.get(player.attackType).getName());
		sb.append("] --E--> ");
		sb.append(at.get(at.rotate(player.attackType)).getName());
		sb.append(" ~ ");
		sb.append(at.get(player.attackType).getDefinition());
		return sb.toString();
	}
	
	private static String updateModelPlacerText(EntityTypeHolder eth, MoveTypeHolder mth, Player player) {
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
	}
	
	public static MousePicker getMousePicker(boolean isOverhead) {
		return (isOverhead ? overheadMouse : defaultMouse);
	}
	
	public static void addEnemy(Enemy e) {
		entities.add(e);
		enemies.add(e);
	}
	
	public static void hidePlayerInFPS() {
		// If we're drawing close to the camera, or the top of the player's head,
		// remove the player so it doesn't get drawn and we can see it in first person view
		if (camera.distanceFromPlayer < 1) {
			player.setRenderable(false);
		} else {
			player.setRenderable(true);
		}
	}
	
	public static boolean isPaused() {
		return isPaused;
	}
	
	public static void setPaused(Boolean paused) {
		if (paused) {
			//dissable all texts then re-enable
			textsDissabled = true;
			for (int i = 0; i < texts.size(); i++) {
				texts.get(i).setRenderable(false);
			}
		} else {
			isPaused = false;
		}
	}
	
	private static void enableAllTexts(boolean pause) {
		for (int i = 0; i < texts.size(); i++) {
			texts.get(i).setRenderable(true);
		}
		textsDissabled = false;
		isPaused = pause;
	}
}