package fontCreator;

import java.io.File;

import main.Mift;

public class FontHolder {
	private static FontType arial = new FontType(Mift.loader.loadFontTexture("arial"), new File("res/fonts/arial.fnt"));
	private static FontType berlinSans = new FontType(Mift.loader.loadFontTexture("berlinSans"), new File("res/fonts/berlinSans.fnt"));
	private static FontType candara = new FontType(Mift.loader.loadFontTexture("candara"), new File("res/fonts/candara.fnt"));
	private static FontType segoe = new FontType(Mift.loader.loadFontTexture("segoe"), new File("res/fonts/segoe.fnt"));
	
	public static FontType getSegoe() {
		return segoe;
	}

	public static FontType getArial() {
		return arial;
	}

	public static FontType getBerlinSans() {
		return berlinSans;
	}

	public static FontType getCandara() {
		return candara;
	}
}