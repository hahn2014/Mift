package main;

import entities.Player;
import entities.Sun;
import io.Logger;
import renderEngine.DisplayManager;

public class Initialize {
	public Initialize(String[] args) {
		for (String arg : args) {
			if (arg.equalsIgnoreCase("-developer")) {
				DisplayManager.cg_developer_status = true;
				Logger.debug("-developer has been set to true");
			}
			if (arg.equalsIgnoreCase("-cg_debug_polygons")) {
				DisplayManager.cg_debug_polygons = true;
				Logger.debug("-cg_debug_polygons has been set to true");
			}
			if (arg.equalsIgnoreCase("-cg_animate_day")) {
				Sun.cg_animate_day = true;
				Logger.debug("cg_animate_day has been set to true");
			}
			if (arg.equalsIgnoreCase("-cg_antialiasing_filtering")) {
				DisplayManager.cg_antialiasing_filtering = true;
				Logger.debug("cg_antialiasing_filtering has been set to true");
			}
			if (arg.equalsIgnoreCase("-cg_anisotropic_filtering")) {
				DisplayManager.cg_anisotropic_filtering = true;
				Logger.debug("cg_anisotropic_filtering has been set to true");
			}
			if (arg.equalsIgnoreCase("-cg_fullscreened")) {
				DisplayManager.cg_fullscreened = true;
				Logger.debug("cg_fullscreened has been set to true");
			}
			if (arg.equalsIgnoreCase("-player_sprint_unlimited")) {
				Player.runInfinite = true;
				Logger.debug("player_sprint_unlimited has been set to true");
			}
		}
	}
}