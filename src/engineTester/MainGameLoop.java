package engineTester;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import models.TexturedModel;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import renderEngine.*;
import renderEngine.DisplayManager.QUALITY;
import terrains.*;
import toolbox.MousePicker;
import water.*;
import entities.*;
import entities.Camera.POV;
import guis.*;

/**
 * Already surpassed 4k lines of code while trending currently at 4670! Keep up
 * the good work - Mift Build 34
 * 
 * @author Bryce Hahn, Mason Cluff
 * @since 1.0
 */
public class MainGameLoop {
	public static final String BUILD = "35";
	public static final String RELEASE = "1";
	public static final String RELEASE_TITLE = "Alpha";
	public static final String NAME = "Mift";
	
	public static final Boolean fps = false;
	public static Camera camera;
	
	public static void main(String[] args) {
		new ImportLibs(); //link the natives for lwjgl
		
		//initialize the quality of the game while loading
		DisplayManager.createDisplay(QUALITY.ULTRA); // default quality value
		List<String> arg = Arrays.asList(args);
		DisplayManager.debugPolys 		=  arg.contains("-d"); //draw quality from arguments from startup command
		if (arg.contains("-Qlight")) {
			DisplayManager.quality = QUALITY.LIGHT;
		} else if (arg.contains("-Qmoderate")) {
			DisplayManager.quality = QUALITY.MODERATE;
		} else if (arg.contains("-Qultra")) {
			DisplayManager.quality = QUALITY.ULTRA;
		}
		
		Loader loader = new Loader();
		EntityCreater entityCreater = new EntityCreater();

		// *********TERRAIN TEXTURE STUFF**********

		Terrain terrain = new TerrainCreater(loader, 0, -1, "lunar_surface", "dirt", "lunar_surface2_", "path", "blendMap", "heightmap").getTerrain();
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);

		// *********MODELS TEXTURE STUFF **********


		List<Entity> entities = new ArrayList<Entity>();

		// ******************NORMAL MAP MODELS************************

		TexturedModel barrelModel = entityCreater.createNormalTexturedModel(loader, "barrel", "barrelNormal", 10, 0.5f);
		TexturedModel crateModel = entityCreater.createNormalTexturedModel(loader, "crate", "crateNormal", 10, 0.5f);
		TexturedModel boulderModel = entityCreater.createNormalTexturedModel(loader, "boulder", "boulderNormal", 10, 0.5f);
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		normalMapEntities.add(new Entity(barrelModel, new Vector3f(75, 10, -75), 0, 0, 0, 1f));
		normalMapEntities.add(new Entity(boulderModel, new Vector3f(85, 10, -75), 0, 0, 0, 1f));
		normalMapEntities.add(new Entity(crateModel, new Vector3f(65, 10, -75), 0, 0, 0, 0.04f));

		// ************ENTITIES*******************
		entities = entityCreater.generateObjects(loader, entities, terrain, 0);

		// *******************OTHER SETUP***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(10000, 10000, -10000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);

		MasterRenderer renderer = new MasterRenderer(loader);

		Player player = new EntityCreater().createPlayer(loader, "person", "playerTexture", new Vector3f(30, 5, -90), new Vector3f(0, 100, 0), 0.6f);

		if (!fps) {
			entities.add(player);
			camera = new Camera(player, POV.THIRD_PERSON_POV);
		} else {
			camera = new Camera(player, POV.FIRST_PERSON_POV);
		}

		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		// **********Water Renderer Set-up************************

		WaterFrameBuffers buffers = new WaterFrameBuffers(DisplayManager.quality);
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, -75, 0);
		waters.add(water);

		// ****************Game Audio Test*********************
		
		//SoundTest soundTester = new SoundTest();
		
		camera.setGrabbed(true);
		Display.setTitle(MainGameLoop.NAME + " Release " + MainGameLoop.RELEASE + " b" + MainGameLoop.BUILD);
		
		// ****************Game Loop Below*********************

		while (!Display.isCloseRequested()) {
			// If we're drawing close to the camera, or the top of the player's head,
			// remove the player so it doesn't get drawn and we can see it in first person view
			if (camera.distanceFromPlayer < 1) {
				entities.remove(player);
			} else {
				if (!entities.contains(player)) {
					entities.add(player);
				}
			}
			player.move(terrain);
			camera.move();
			camera.rotate();
			picker.update();
			
			for (int i = 0; i < normalMapEntities.size(); i++) {
				normalMapEntities.get(i).setRotY(normalMapEntities.get(i).getRotY() + 2);
			}
			
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);

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
			guiRenderer.render(guiTextures);

			DisplayManager.updateDisplay();
		}

		// *********Clean Up Below**************

		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
	
	public static Camera getCamera() {
		return camera;
	}
}