package fontCreator;

import java.io.File;

import renderEngine.Loader;

public class FontHolder {
	private static FontType arial;
	private static FontType berlinSans;
	private static FontType candara;
	private static FontType segoe;
	
	public FontHolder(Loader loader) {
		arial = new FontType(loader.loadFontTexture("arial"), new File("res/fonts/arial.fnt"));
		berlinSans = new FontType(loader.loadFontTexture("berlinSans"), new File("res/fonts/berlinSans.fnt"));
		candara = new FontType(loader.loadFontTexture("candara"), new File("res/fonts/candara.fnt"));
		segoe = new FontType(loader.loadFontTexture("segoe"), new File("res/fonts/segoe.fnt"));
	}

	public FontType getSegoe() {
		return segoe;
	}

	public FontType getArial() {
		return arial;
	}

	public FontType getBerlinSans() {
		return berlinSans;
	}

	public FontType getCandara() {
		return candara;
	}
}