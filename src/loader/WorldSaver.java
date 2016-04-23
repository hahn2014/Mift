package loader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.SecureRandom;

import entities.Enemy;
import entities.Entity;
import io.Logger;
import io.Settings;
import main.Mift;

public class WorldSaver {
	private static final String SAVE_DIRECTORY = System.getProperty("user.dir") + "\\saves\\";
	private static SecureRandom rand = new SecureRandom();

	public static void saveCurrentWorld() {
		int worldID = rand.nextInt(Integer.SIZE - 1) * 9999;
		File worldSave = new File(SAVE_DIRECTORY + worldID + ".world");
		Logger.debug("Attemping to save file to " + worldSave.getAbsolutePath());
		rand.setSeed(System.currentTimeMillis());
		Settings.saveSettings();

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream(worldSave);
			oos = new ObjectOutputStream(fout);

			oos.writeObject(Mift.terrain);
			for (Entity e : Mift.entities)
				oos.writeObject(e);
			for (Enemy e : Mift.enemies)
				oos.writeObject(e);
			oos.writeObject(Mift.player);
			oos.flush();
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (oos != null)
					oos.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}