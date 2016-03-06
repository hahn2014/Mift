package terrains;

import main.Mift;
import textures.TerrainTexture;
import textures.TerrainTexturePack;

public class TerrainCreator {
	Terrain terrain;
	TerrainTexture backgroundTex;
	TerrainTexture tex1;
	TerrainTexture tex2;
	TerrainTexture tex3;
	TerrainTexture tex4;
	TerrainTexture blendmapTex;
	TerrainTexturePack texturePack;
	
	public TerrainCreator(int gridX, int gridY, String background, String r, String g, String b, String blend) {
		backgroundTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + background));
		tex1 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + r));
		tex2 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + g));
		tex3 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + b));
		blendmapTex = new TerrainTexture(Mift.getLoader().loadTexture(blend));
		texturePack = new TerrainTexturePack(backgroundTex, tex1, tex2, tex3);
		terrain = new Terrain(gridX, gridY, Mift.getLoader(), texturePack, blendmapTex);
		Mift.instance_count++;
	}
	
	public TerrainCreator(int gridX, int gridY, String background, String t1, String t2, String t3, String t4, String blend) {
		backgroundTex = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + background));
		tex1 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + t1));
		tex2 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + t2));
		tex3 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + t3));
		tex4 = new TerrainTexture(Mift.getLoader().loadTexture("terrain/" + t4));
		blendmapTex = new TerrainTexture(Mift.getLoader().loadTexture(blend));
		texturePack = new TerrainTexturePack(backgroundTex, tex1, tex2, tex3, tex4);
		terrain = new Terrain(gridX, gridY, Mift.getLoader(), texturePack, blendmapTex);
		Mift.instance_count++;
	}
	
	public Terrain getTerrain() {
		return terrain;
	}
}