package io;

import java.util.ArrayList;
import java.util.List;

public class SettingHolder {
	private static List<Setting> settings = new ArrayList<Setting>();
	
	public SettingHolder() {
		//editable default settings
		settings.add(new Setting("cg_fullscreened", 			"Fullscreen*", 				"Toggle Fullscreened Mode", 												false, 		0, 0));
		settings.add(new Setting("cg_anisotropic_filtering", 	"Anisotropic Filtering", 	"Smooths Textures Rendered At A Distant Angle", 							true, 		0, 1));
		settings.add(new Setting("cg_antialiasing_filtering", 	"Antialiasing Filtering", 	"Smooths Texture And Model Edge Quality", 									true, 		0, 2));
		settings.add(new Setting("cg_fps", 						"Draw FPS", 				"Toggle Rendering FPS In Corner", 											true, 		0, 3));
		settings.add(new Setting("cg_theatrical",  				"Theater Mode", 			"Toggle Rendering Gui Elements", 											false, 		0, 4));
		settings.add(new Setting("cp_myo_enabled", 				"Myo Armband Connection*", 	"Toggle Use Of The Myo Armband In Game", 									false, 		0, 5));
		settings.add(new Setting("cg_quality", 					"Quality*", 				"Change Quality Of Textures, Render Distance, etc..", 						2, 			0, 6, 1, 4));
		settings.add(new Setting("cg_vsync", 					"VSync", 					"Toggle Vertical Syncing To Avoid Screen Tearing", 							true, 		0, 7));
		settings.add(new Setting("cg_fov", 						"FOV", 						"Set The Field Of View The Camera Renders At", 								70, 		0, 8, 70, 110));
		//editable post processing settings
		settings.add(new Setting("cg_gaussian_blur", 			"Gaussian Blur", 			"Blur the screen", 															false, 		1, 0));
		settings.add(new Setting("cg_contrast_adjust", 			"Adjust Contrast", 			"Raise the contrast level to simulate a more realistic lighting effect",	true, 		1, 1));
		settings.add(new Setting("cg_bloom", 					"Bloom Effect", 			"Create a glowing effect off specified objects", 							false, 		1, 2));
		settings.add(new Setting("cg_depth_of_field",			"Depth Of Field Effect", 	"Comming Soon To Pre-Alpha 1.70", 											true, 		1, 3));
		//editable developer settings
		settings.add(new Setting("cg_developer", 				"Developer", 				"Set Developer Privaliges - turning off will dissable all dev settings",	false, 		2, 0));
		settings.add(new Setting("cg_animate_day", 				"Day Animated", 			"Toggle Day Animation Of Shadows", 											true, 		2, 1));
		settings.add(new Setting("cg_debug_polygons", 			"Debug Polygons", 			"Toggle Rendering Lines Instead Of Filled Triangles", 						false, 		2, 2));
		settings.add(new Setting("player_sprint_unlimited", 	"Player Unlimited Sprint", 	"Toggle If The Player Can Sprint For Ever", 								false, 		2, 3));
		settings.add(new Setting("player_ufo",					"Player UFO", 				"Toggle If The Player Has Collisions And Gravity", 							false, 		2, 4));
		settings.add(new Setting("player_god", 					"Player GOD", 				"Toggle If The Player Has Health", 											false, 		2, 5));
		settings.add(new Setting("player_fire_unlimited", 		"Player Fire Unlimited", 	"Toggle The Player's Ability To Shoot Without Cooldown", 					false, 		2, 6));
		settings.add(new Setting("player_undetected", 			"Player Undetected", 		"Toggle The Player's Visibility From Enemies", 								false, 		2, 7));
	}
	
	public static Setting get(int id) {
		return settings.get(id);
	}
	
	public static Setting get(int index, int id) {
		for (Setting s : settings) {
			if (s.getColumnIndex() == index) {
				if (s.getRowIndex() == id) {
					return s;
				}
			}
		}
		Logger.error("Unable to find setting within [" + index + ", " + id + "] range");
		return null;
	}
	
	public static String getDefinition(int id) {
		return settings.get(id).getDefinition();
	}
	
	public static String getDefinition(int index, int id) {
		for (Setting s : settings) {
			if (s.getColumnIndex() == index) {
				if (s.getRowIndex() == id) {
					return s.getDefinition();
				}
			}
		}
		Logger.error("Unable to find setting within [" + index + ", " + id + "] range");
		return "null";
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