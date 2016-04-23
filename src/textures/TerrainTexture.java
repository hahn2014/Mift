package textures;

import java.io.Serializable;

public class TerrainTexture implements Serializable {
	private static final long serialVersionUID = -1929199517550550298L;
	private int textureID;

	public TerrainTexture(int textureID) {
		this.textureID = textureID;
	}

	public int getTextureID() {
		return textureID;
	}
}