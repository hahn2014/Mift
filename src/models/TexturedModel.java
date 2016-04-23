package models;

import java.io.Serializable;

import textures.ModelTexture;

public class TexturedModel implements Serializable {
	private static final long serialVersionUID = 7479637957994782498L;
	private RawModel rawModel;
	private ModelTexture texture;

	public TexturedModel(RawModel model, ModelTexture texture) {
		this.rawModel = model;
		this.texture = texture;
	}

	public RawModel getRawModel() {
		return rawModel;
	}

	public ModelTexture getTexture() {
		return texture;
	}
}