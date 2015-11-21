package terrains;

import main.Mift;
import renderEngine.DisplayManager;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class TerrainCreator {
	Terrain terrain;
	TerrainTexture backgroundTex;
	TerrainTexture rTex;
	TerrainTexture gTex;
	TerrainTexture bTex;
	TerrainTexture blendmapTex;
	TerrainTexturePack texturePack;
	
	private int light = 256;
	private int moderate = 512;
	private int ultra = 1024;
	
	public TerrainCreator(int gridX, int gridY, String background, String r, String g, String b, String blend, String heightmap) {
		int res = getRes();
		backgroundTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + background + res));
		rTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + r + res));
		gTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + g + res));
		bTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + b + res));
		blendmapTex = new TerrainTexture(Mift.getLoader().loadTexture(blend));
		texturePack = new TerrainTexturePack(backgroundTex, rTex, gTex, bTex);
		terrain = new Terrain(0, -1, Mift.getLoader(), texturePack, blendmapTex, heightmap);
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
	
	public int getRes() {
		if (DisplayManager.quality == DisplayManager.QUALITY.LIGHT) {
			return light;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.MODERATE) {
			return moderate;
		} else if (DisplayManager.quality == DisplayManager.QUALITY.ULTRA) {
			return ultra;
		}
		return light;
	}
}