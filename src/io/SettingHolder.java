package io;

import java.util.ArrayList;
import java.util.List;

import io.Setting.SettingType;


public class SettingHolder {
	private static List<Setting> settings = new ArrayList<Setting>();
	
	public SettingHolder() {
		settings.add(new Setting("cg_developer", 				SettingType.bool,		false));
		settings.add(new Setting("cg_fullscreened", 			SettingType.bool,		false));
		settings.add(new Setting("cg_animate_day", 				SettingType.bool,		true));
		settings.add(new Setting("cg_debug_polygons", 			SettingType.bool,		false));
		settings.add(new Setting("cg_anisotropic_filtering", 	SettingType.bool,		true));
		settings.add(new Setting("cg_antialiasing_filtering", 	SettingType.bool,		true));
		settings.add(new Setting("cg_fps", 						SettingType.bool,		true));
		settings.add(new Setting("cg_theatrical",  				SettingType.bool,		false));
		settings.add(new Setting("cg_post_processing", 			SettingType.bool,		true));
		settings.add(new Setting("player_sprint_unlimited", 	SettingType.bool,		false));
		settings.add(new Setting("player_ufo",					SettingType.bool, 		false));
		settings.add(new Setting("player_god", 					SettingType.bool,		false));
		settings.add(new Setting("player_fire_unlimited", 		SettingType.bool, 		false));
		settings.add(new Setting("camera_distance", 			SettingType.integer, 	0));
		settings.add(new Setting("cp_myo_enabled", 				SettingType.bool,		false));
		settings.add(new Setting("cg_quality", 					SettingType.integer,	2));
		settings.add(new Setting("cg_fov", 						SettingType.integer,	70));
	}
	
	public static Setting get(int id) {
		return settings.get(id);
	}
	
	public static Setting get(String name) {
		for (Setting set : settings) {
			if (set.getVariable().equals(name)) {
				return set;
			}
		}
		Logger.error("unknown varaible " + name);
		return null;
	}
	
	public static List<Setting> getAll() {
		return settings;
	}
}