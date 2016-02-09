package terrains;

import main.Mift;
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
	
	public TerrainCreator(int gridX, int gridY, String background, String r, String g, String b, String blend) {
		backgroundTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + background));
		rTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + r));
		gTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + g));
		bTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + b));
		blendmapTex = new TerrainTexture(Mift.getLoader().loadTexture(blend));
		texturePack = new TerrainTexturePack(backgroundTex, rTex, gTex, bTex);
		terrain = new Terrain(gridX, gridY, Mift.getLoader(), texturePack, blendmapTex);
		Mift.instance_count++;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}