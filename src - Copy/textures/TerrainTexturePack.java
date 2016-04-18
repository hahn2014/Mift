package textures;

public class TerrainTexturePack {

	private TerrainTexture backgroundTexture;
	private TerrainTexture texture1;
	private TerrainTexture texture2;
	private TerrainTexture texture3;
	private TerrainTexture texture4;

	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture rTexture, TerrainTexture gTexture,
			TerrainTexture bTexture) {
		this.backgroundTexture = backgroundTexture;
		this.texture1 = rTexture;
		this.texture2 = gTexture;
		this.texture3 = bTexture;
	}

	public TerrainTexturePack(TerrainTexture backgroundTexture, TerrainTexture tex1, TerrainTexture tex2,
			TerrainTexture tex3, TerrainTexture tex4) {
		this.backgroundTexture = backgroundTexture;
		this.texture1 = tex1;
		this.texture2 = tex2;
		this.texture3 = tex3;
		this.texture4 = tex4;
	}

	public TerrainTexture getBackgroundTexture() {
		return backgroundTexture;
	}

	public TerrainTexture getTexture1() {
		return texture1;
	}

	public TerrainTexture getTexture2() {
		return texture2;
	}

	public TerrainTexture getTexture3() {
		return texture3;
	}

	public TerrainTexture getTexture4() {
		return texture4;
	}
}