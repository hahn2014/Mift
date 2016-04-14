package skybox;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import entities.Camera;
import entities.OverheadCamera;
import entities.Sun;
import main.Mift;
import models.RawModel;

public class SkyboxRenderer {
	
private static final float SIZE = 500f;
	
	private static final float[] VERTICES = {        
	    -SIZE,  SIZE, -SIZE,
	    -SIZE, -SIZE, -SIZE,
	    SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE, -SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,

	    -SIZE, -SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE,
	    -SIZE, -SIZE,  SIZE,

	    -SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE, -SIZE,
	     SIZE,  SIZE,  SIZE,
	     SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE,  SIZE,
	    -SIZE,  SIZE, -SIZE,

	    -SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE, -SIZE,
	     SIZE, -SIZE, -SIZE,
	    -SIZE, -SIZE,  SIZE,
	     SIZE, -SIZE,  SIZE
	};
	
	private static String[] DAY_TEXTURE_FILES = { "right", "left", "top", "bottom", "back", "front" };
	private static String[] NIGHT_TEXTURE_FILES = { "nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront" };

	private RawModel cube;
	//private int texture;
	private int nightTexture;
	private int dayTexture;
	private SkyboxShader shader;
	private float blendFactor = 1;
	
	private static final float realTimeMultiplyer = 22 * Mift.dayTimeMultiplier; //1 minute in game = about 1 seconds in real life
	private static final float realTimeSeconds = 0.1f; //0.01345f; //will slow the increments down to a days second within 5%
	private static final float stepSeconds = (realTimeSeconds * realTimeMultiplyer);
	private static final int dayStart = 0; 			//12:00.00am
	private static final int dayMid = 21600; 		//06:00.00am
	private static final int dayEnd = 43199; 		//11:59.59pm
	private static final int nightStart = 43200; 	//12:00.00pm
	private static final int nightMid = 64800;		//06:00.00pm
	private static final int nightEnd = 86399; 		//11:59.59pm
	private static float currentTime = 0; 			//12:00.00am
	private static boolean isDay = true;

	public SkyboxRenderer(Matrix4f projectionMatrix) {
		cube = Mift.loader.loadToVAO(VERTICES, 3);
		nightTexture = Mift.loader.loadCubeMap(NIGHT_TEXTURE_FILES);
		dayTexture = Mift.loader.loadCubeMap(DAY_TEXTURE_FILES);
		shader = new SkyboxShader();
		shader.start();
		shader.connectTextureUnits();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
	}

	public void render(Camera camera, int r, int g, int b) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(r, g, b);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public void render(OverheadCamera camera, int r, int g, int b) {
		shader.start();
		shader.loadViewMatrix(camera);
		shader.loadFogColor(r, g, b);
		GL30.glBindVertexArray(cube.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		bindTextures();
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, cube.getVertexCount());
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		shader.stop();
	}
	
	public static void move() {
		if (Sun.cg_animate_day) {
			//first things first, lets get the updated TOD for the game
			if (isDay) { //day time calculations
				if (currentTime + 1 <= dayEnd) { //still got time during the day
					currentTime += stepSeconds;
				} else { //day is over, switch to night
					currentTime = nightStart;
					isDay = false;
				}
			} else { //night time calculations
				if (currentTime + 1 <= nightEnd) { //still got time during the night
					currentTime += stepSeconds;
				} else { //night is over switch to day
					currentTime = dayStart;
					isDay = true;
				}
			}
		}
	}
	
	public static int getCurrentTimeInt() {
		return Math.round(currentTime);
	}

	private void bindTextures() {
		int texture1 = dayTexture;
		int texture2 = nightTexture;
		
		if (isDay) { //day time
			if (currentTime >= dayStart && currentTime < dayMid) { //12am to 6am
				blendFactor += calculateBlendFade(blendFactor, 0.75f, (dayMid - currentTime) / stepSeconds);
			} else if (currentTime >= dayMid && currentTime < dayEnd) { //6am to 11:59.59am
				blendFactor += calculateBlendFade(blendFactor, 0.05f, (dayEnd - currentTime) / stepSeconds);
			}
			if (currentTime == dayEnd) { //noon
				blendFactor = 0; //full show of dayTexture
			}
		} else {
			if (currentTime >= nightStart && currentTime < nightMid) { //12pm to 6pm
				blendFactor += calculateBlendFade(blendFactor, 0.25f, (nightMid - currentTime) / stepSeconds);
			} else if (currentTime >= nightMid && currentTime < nightEnd) { //6pm to 12am
				blendFactor += calculateBlendFade(blendFactor, 1.0f, (nightEnd - currentTime) / stepSeconds);
			}
			if (currentTime == nightEnd) {//midnight
				blendFactor = 1; //full show of night texture
			}
		}
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture1);
		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL13.GL_TEXTURE_CUBE_MAP, texture2);
		shader.loadBlendFactor(blendFactor);
	}
	
	private float calculateBlendFade(float current, float desired, float time) {
		float blendDistance = desired - current;
		float stepChange = blendDistance / time;
		return stepChange;
	}
}