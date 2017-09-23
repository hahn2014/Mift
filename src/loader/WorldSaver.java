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
	private static final String SAVE_DIRECTORY = System.getProperty("user.dir") + "/saves/";
	private static SecureRandom rand = new SecureRandom();

	public static void saveCurrentWorld() {
		rand.setSeed(System.currentTimeMillis());
		//int worldID = rand.nextInt(Integer.SIZE - 1) * 9999;
		int worldID = 1;
		
		File worldSave = null;
		
		if (new File(SAVE_DIRECTORY).exists()) {
			worldSave = new File(SAVE_DIRECTORY + worldID + ".world");
			Logger.debug("Attemping to save file to " + worldSave.getAbsolutePath());
		} else {
			Logger.debug("We could not find the saves directory " + SAVE_DIRECTORY + ", so we are creating it...");
			new File(SAVE_DIRECTORY).mkdir();
			worldSave = new File(SAVE_DIRECTORY + worldID + ".world");
			Logger.debug("Attemping to save file to " + worldSave.getAbsolutePath());
		}
		Settings.saveSettings();

		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		try {
			fout = new FileOutputStream(worldSave);
			oos = new ObjectOutputStream(fout);

			oos.writeBytes("\n:ter:");      //:ter: means terrain for loading
			oos.writeObject(Mift.terrain);
			oos.writeBytes(";ter;\n");
			for (Entity e : Mift.entities) {
				oos.writeBytes("\n:ent:"); //:ent: means entity for loading
				oos.writeObject(e);
				oos.writeBytes(";ent;");
			}
			for (Enemy e : Mift.enemies) {
				oos.writeBytes("\n:ene:"); //:ene: means enemy for loading
				oos.writeObject(e);
				oos.writeBytes(";ene;");
			}
			oos.writeBytes("\n:pla:");
			oos.writeObject(Mift.player);
			oos.writeBytes(";pla;");
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