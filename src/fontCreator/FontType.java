package fontCreator;

import java.io.File;

public class FontType {

	private int textureAtlas;
	private TextCreator loader;

	public FontType(int textureAtlas, File fontFile) {
		this.textureAtlas = textureAtlas;
		this.loader = new TextCreator(fontFile);
	}

	public int getTextureAtlas() {
		return textureAtlas;
	}

	public TextData loadText(GUIText text) {
		return loader.createText(text);
	}
}