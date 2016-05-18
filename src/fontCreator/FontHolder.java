package fontCreator;

import java.io.File;

import main.Mift;

public class FontHolder {
	private static FontType arial = new FontType(Mift.loader.loadFontTexture("arial"), new File("res/fonts/arial.fnt"));
	private static FontType berlinSans = new FontType(Mift.loader.loadFontTexture("berlinSans"), new File("res/fonts/berlinSans.fnt"));
	//private static FontType courier = new FontType(Mift.loader.loadFontTexture("courierNew"), new File("res/fonts/courierNew.fnt"));
	//private static FontType freestyle = new FontType(Mift.loader.loadFontTexture("freestyle"), new File("res/fonts/freestyle.fnt"));
	
	public static FontType getArial() {
		return arial;
	}

	public static FontType getBerlinSans() {
		return berlinSans;
	}

	public static FontType getFreestyle() {
		return berlinSans;
	}
	
}