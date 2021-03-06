package renderEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector4f;

import attacks.AttackHolder;
import attacks.fireball.Fireball;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.OverheadCamera;
import io.SettingHolder;
import main.Mift;
import models.TexturedModel;
import normalMappingRenderer.NormalMappingRenderer;
import postProcessing.FBO;
import postProcessing.PostProcessing;
import shaders.StaticShader;
import shaders.TerrainShader;
import shadows.ShadowMapMasterRenderer;
import skybox.SkyboxRenderer;
import terrains.Terrain;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterTile;

public class MasterRenderer {

	public static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;

	public static final float RED = 0.1f;
	public static final float GREEN = 0.4f;
	public static final float BLUE = 0.2f;

	private static Matrix4f projectionMatrix;

	private StaticShader shader = new StaticShader();
	private EntityRenderer renderer;

	private TerrainRenderer terrainRenderer;
	private TerrainShader terrainShader = new TerrainShader();

	private NormalMappingRenderer normalMapRenderer;

	private SkyboxRenderer skyboxRenderer;
	private ShadowMapMasterRenderer shadowRenderer;

	private static Map<TexturedModel, List<Entity>> entities = new HashMap<TexturedModel, List<Entity>>();
	private Map<TexturedModel, List<Entity>> normalMapEntities = new HashMap<TexturedModel, List<Entity>>();
	private List<Terrain> terrains = new ArrayList<Terrain>();

	public MasterRenderer() {
		enableCulling();
		projectionMatrix = createProjectionMatrix();
		renderer = new EntityRenderer(shader, projectionMatrix);
		terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
		skyboxRenderer = new SkyboxRenderer(projectionMatrix);
		normalMapRenderer = new NormalMappingRenderer(projectionMatrix);
		this.shadowRenderer = new ShadowMapMasterRenderer();
	}

	public static Matrix4f getProjectionMatrix() {
		return projectionMatrix;
	}

	public void renderCallOverheadView(WaterFrameBuffers buffers, OverheadCamera camera, MousePicker mouse,
			WaterRenderer waterRenderer, WaterTile water, List<Light> lights, List<Entity> entities,
			List<Entity> normalMapEntities, Light sun, AttackHolder attackHolder,
			FBO multisampledFBO, FBO singlesampleFBO, FBO outputFBO) {

		camera.move();
		camera.rotate();
		camera.getClicks();
		mouse.update(true);
		
		//************render the shadows*****************
		renderShadowMap(entities, sun, camera);

		//************render reflection texture**********
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - WaterTile.height);
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderScene(entities, normalMapEntities, lights, camera,
				new Vector4f(0, 1, 0, -WaterTile.height + 2.0f), attackHolder);
		camera.getPosition().y += distance;
		camera.invertPitch();

		//**************render refraction texture********
		buffers.bindRefractionFrameBuffer();
		renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, WaterTile.height + 1.0f), attackHolder);
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		
		//******************render with ppe***************
		if (SettingHolder.get("cg_antialiasing_filtering").getValueB()) {
			multisampledFBO.bindFrameBuffer();
			renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
			multisampledFBO.unbindFrameBuffer();
			multisampledFBO.resolveToFBO(outputFBO);
			PostProcessing.doPostProcessing(outputFBO.getColorTexture(), SettingHolder.get("cg_gaussian_blur").getValueB(), SettingHolder.get("cg_bloom").getValueB(), SettingHolder.get("cg_contrast_adjust").getValueB());
		} else {
			singlesampleFBO.bindFrameBuffer();
			renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
			singlesampleFBO.unbindFrameBuffer();
			PostProcessing.doPostProcessing(singlesampleFBO.getColorTexture(), SettingHolder.get("cg_gaussian_blur").getValueB(), SettingHolder.get("cg_bloom").getValueB(), SettingHolder.get("cg_contrast_adjust").getValueB());
		}
		
		//***************render all scenes***************
		renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
		waterRenderer.render(water, camera, sun);
	}

	public void renderCallStandardView(WaterFrameBuffers buffers, Camera camera, MousePicker mouse,
			WaterRenderer waterRenderer, WaterTile water, List<Light> lights, List<Entity> entities,
			List<Entity> normalMapEntities, Light sun, AttackHolder attackHolder,
			FBO multisampledFBO, FBO singlesampleFBO, FBO outputFBO) {
		camera.move();
		camera.getKeys();
		camera.getClicks();
		mouse.update(false);

		//************render the shadows*****************
		renderShadowMap(entities, sun, camera);

		//************render reflection texture**********
		buffers.bindReflectionFrameBuffer();
		float distance = 2 * (camera.getPosition().y - WaterTile.height);
		camera.getPosition().y -= distance;
		camera.invertPitch();
		renderScene(entities, normalMapEntities, lights, camera,
				new Vector4f(0, 1, 0, -WaterTile.height + 2.0f), attackHolder);
		camera.getPosition().y += distance;
		camera.invertPitch();

		//**************render refraction texture********
		buffers.bindRefractionFrameBuffer();
		renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, WaterTile.height + 1.0f), attackHolder);
		GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
		buffers.unbindCurrentFrameBuffer();
		
		//***************render all scenes***************
		renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
		waterRenderer.render(water, camera, sun);
		
		//****************render with ppe****************
		if (SettingHolder.get("cg_antialiasing_filtering").getValueB()) {
			multisampledFBO.bindFrameBuffer();
			renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
			multisampledFBO.unbindFrameBuffer();
			multisampledFBO.resolveToFBO(outputFBO);
			PostProcessing.doPostProcessing(outputFBO.getColorTexture(), SettingHolder.get("cg_gaussian_blur").getValueB(), SettingHolder.get("cg_bloom").getValueB(), SettingHolder.get("cg_contrast_adjust").getValueB());
		} else {
			singlesampleFBO.bindFrameBuffer();
			renderScene(entities, normalMapEntities, lights, camera, new Vector4f(0, -1, 0, 100000), attackHolder);
			singlesampleFBO.unbindFrameBuffer();
			PostProcessing.doPostProcessing(singlesampleFBO.getColorTexture(), SettingHolder.get("cg_gaussian_blur").getValueB(), SettingHolder.get("cg_bloom").getValueB(), SettingHolder.get("cg_contrast_adjust").getValueB());
		}
	}

	public void renderScene(List<Entity> entities, List<Entity> normalEntities,
			List<Light> lights, Camera camera, Vector4f clipPlane, AttackHolder attackHolder) {
		for (Terrain terrain : terrains) {
			processTerrain(terrain);
		}
		if (entities != null) {
			for (Entity entity : entities) {
				processEntity(entity);
			}
		}
		if (normalEntities != null) {
			for (Entity entity : normalEntities) {
				processNormalMapEntity(entity);
			}
		}
		processAttacks(attackHolder);
		render(lights, camera, clipPlane);
	}

	public void renderScene(List<Entity> entities, List<Entity> normalEntities,
			List<Light> lights, OverheadCamera camera, Vector4f clipPlane, AttackHolder attackHolder) {
		for (Terrain terrain : terrains) {
			processTerrain(terrain);
		}
		if (entities != null) {
			for (Entity entity : entities) {
				processEntity(entity);
			}
		}
		if (normalEntities != null) {
			for (Entity entity : normalEntities) {
				processNormalMapEntity(entity);
			}
		}
		processAttacks(attackHolder);
		render(lights, camera, clipPlane);
	}

	public void render(List<Light> lights, Camera camera, Vector4f clipPlane) {
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities, shadowRenderer.getToShadowMapSpaceMatrix());
		shader.stop();
		normalMapRenderer.render(normalMapEntities, clipPlane, lights, camera);
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(Mift.terrain, shadowRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		skyboxRenderer.render(camera, (int)RED * 255, (int)GREEN * 255, (int)BLUE * 255);
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
	}

	public void render(List<Light> lights, OverheadCamera camera, Vector4f clipPlane) {
		prepare();
		shader.start();
		shader.loadClipPlane(clipPlane);
		shader.loadSkyColor(RED, GREEN, BLUE);
		shader.loadLights(lights);
		shader.loadViewMatrix(camera);
		renderer.render(entities, shadowRenderer.getToShadowMapSpaceMatrix());
		shader.stop();
		normalMapRenderer.render(normalMapEntities, clipPlane, lights, camera);
		terrainShader.start();
		terrainShader.loadClipPlane(clipPlane);
		terrainShader.loadSkyColour(RED, GREEN, BLUE);
		terrainShader.loadLights(lights);
		terrainShader.loadViewMatrix(camera);
		terrainRenderer.render(Mift.terrain, shadowRenderer.getToShadowMapSpaceMatrix());
		terrainShader.stop();
		skyboxRenderer.render(camera, (int)RED * 255, (int)GREEN * 255, (int)BLUE * 255);
		terrains.clear();
		entities.clear();
		normalMapEntities.clear();
	}
	
	public static void processAttacks(AttackHolder attackHolder) {
		//fireballs
		if (attackHolder.getFireballHolder().getAll() != null) {
			for (Fireball fire : attackHolder.getFireballHolder().getAll()) {
				TexturedModel attackModel = fire.getModel();
				List<Entity> batch = entities.get(attackModel);
				if (batch != null) {
					batch.add(fire);
				} else {
					List<Entity> newBatch = new ArrayList<Entity>();
					newBatch.add(fire);
					entities.put(attackModel, newBatch);
				}
			}
		}
		//other attacks
	}

	public static void enableCulling() {
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glCullFace(GL11.GL_BACK);
	}

	public static void disableCulling() {
		GL11.glDisable(GL11.GL_CULL_FACE);
	}

	public void processTerrain(Terrain terrain) {
		terrains.add(terrain);
	}

	public void processEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = entities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			entities.put(entityModel, newBatch);
		}
	}

	public void processNormalMapEntity(Entity entity) {
		TexturedModel entityModel = entity.getModel();
		List<Entity> batch = normalMapEntities.get(entityModel);
		if (batch != null) {
			batch.add(entity);
		} else {
			List<Entity> newBatch = new ArrayList<Entity>();
			newBatch.add(entity);
			normalMapEntities.put(entityModel, newBatch);
		}
	}

	public void cleanUp() {
		shader.cleanUp();
		terrainShader.cleanUp();
		normalMapRenderer.cleanUp();
		shadowRenderer.cleanUp();
	}
	
	private void renderShadowMap(List<Entity> entityList, Light sun, Camera camera) {
		for (Entity entity : entityList) {
			processEntity(entity);
		}
		shadowRenderer.render(entities, sun, camera);
		entities.clear();
	}
	
	private void renderShadowMap(List<Entity> entityList, Light sun, OverheadCamera camera) {
		for (Entity entity : entityList) {
			processEntity(entity);
		}
		shadowRenderer.render(entities, sun, camera);
		entities.clear();
	}
	
	public int getShadowMapTexture() {
		return shadowRenderer.getShadowMap();
	}

	public void prepare() {
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glClearColor(RED, GREEN, BLUE, 1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GL13.glActiveTexture(GL13.GL_TEXTURE5);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getShadowMapTexture());
	}

	public static Matrix4f createProjectionMatrix() {
		Matrix4f matrix = new Matrix4f();
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		float y_scale = (float) ((1f / Math.tan(Math.toRadians(SettingHolder.get("cg_fov").getValueI() / 2f))));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = FAR_PLANE - NEAR_PLANE;

		matrix.m00 = x_scale;
		matrix.m11 = y_scale;
		matrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
		matrix.m23 = -1;
		matrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
		matrix.m33 = 0;
		return matrix;
	}
}