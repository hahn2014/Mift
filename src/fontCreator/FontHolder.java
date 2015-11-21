package fontCreator;

import java.io.File;

import main.Mift;

public class FontHolder {
	private static FontType arial;
	private static FontType berlinSans;
	private static FontType candara;
	private static FontType segoe;
	
	public FontHolder() {
		arial = new FontType(Mift.getLoader().loadFontTexture("arial"), new File("res/fonts/arial.fnt"));
		berlinSans = new FontType(Mift.getLoader().loadFontTexture("berlinSans"), new File("res/fonts/berlinSans.fnt"));
		candara = new FontType(Mift.getLoader().loadFontTexture("candara"), new File("res/fonts/candara.fnt"));
		segoe = new FontType(Mift.getLoader().loadFontTexture("segoe"), new File("res/fonts/segoe.fnt"));
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