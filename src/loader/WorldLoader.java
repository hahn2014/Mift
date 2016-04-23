package loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import entities.Enemy;
import entities.Entity;
import entities.Player;
import io.Logger;
import io.Settings;
import main.Mift;
import terrains.Terrain;

public class WorldLoader {
	private static final String SAVE_DIRECTORY = System.getProperty("user.dir") + "\\saves\\";
	
	public static void loadWorld(String worldName) {
		File worldSave = new File(SAVE_DIRECTORY + worldName + ".world");
		Logger.debug("Attemping to load save file from " + worldSave.getAbsolutePath());
		
		Settings.saveSettings();

		FileInputStream fin = null;
		ObjectInputStream ois = null;
		List<Object> objects = new ArrayList<Object>();
		try {
			fin = new FileInputStream(worldSave);
			ois = new ObjectInputStream(fin);
			
			while (true) {
				if (ois.readObject() == null) {
					break;
				}
				objects.add(ois.readObject());
			}
			translateObjects(objects);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ois != null)
					ois.close();
				if (fin != null)
					fin.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void translateObjects(List<Object> objs) {
		for (Object o : objs) {
			if (o instanceof Player) {
				Mift.player = (Player) o;
			} else if (o instanceof Terrain) {
				Mift.terrain = (Terrain) o;
			} else if (o instanceof Entity) {
				Mift.entities.add((Entity) o);
			} else if (o instanceof Enemy) {
				Mift.enemies.add((Enemy) o);
			}
		}
		Mift.hasMadeWorld = true;
	}
}