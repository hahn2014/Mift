package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import entities.Player;
import entities.Sun;
import renderEngine.DisplayManager;
import toolbox.Maths;

public class Settings {
	private static final String settingsFile = "mift.set";
	private static final File settingsF = new File(settingsFile);
	
	public Settings() {
		if (settingsF.exists()) {
			Logger.info("Settings file was found, attempting to load settings.");
			loadSettings();
		} else {
			Logger.error("Setings file was not found, creating file to defaults.");
			createSettingsFile();
		}
	}
	
	private void createSettingsFile() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(settingsFile));
			bw.write(new String("cg_developer 1;cg_fullscreened 1;cg_animate_day 1;cg_debug_polygons 0;cg_anisotropic_filtering 1;cg_antialiasing_filtering 1;player_sprint_unlimited 0;"));
			bw.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null) bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Logger.info("New settings file has been created");
		loadSettings();
	}
	
	private void loadSettings() {
		BufferedReader br = null;
		List<String> lines = new ArrayList<String>();
		try {
			String line;
			br = new BufferedReader(new FileReader(settingsFile));
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		Logger.info("Applying changes");
		String[] variables = listToArray(lines);
		String vari = "";
		int vali = 0;
		for (String var : variables) {
			vari = var.substring(0, var.length() - 1);
			try {
				vali = Integer.parseInt(var.substring(var.length() - 1, var.length()));
			} catch (Exception e) {
				vali = 0;
			}
			applyVariableChange(vari, vali);
		}
	}
	
	public void saveSettings() {
		
	}
	
	private String[] listToArray(List<String> lines) {
		List<String> variables = new ArrayList<String>();
		String curVar = "";
		for (String line : lines) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == ';') {
					variables.add(curVar);
					curVar = "";
				} else {
					if (line.charAt(i) != ' ') {
						curVar += line.charAt(i);
					}
				}
			}
		}
		return variables.toArray(new String[variables.size()]);
	}
	
	private void applyVariableChange(String variable, int value) {
		if (variable.equalsIgnoreCase("cg_animate_day")) {
			Sun.cg_animate_day = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_debug_polygons")) {
			DisplayManager.cg_debug_polygons = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_developer")) {
			DisplayManager.cg_developer_status = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_fullscreened")) {
			DisplayManager.cg_fullscreened = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_anisotropic_filtering")) {
			DisplayManager.cg_anisotropic_filtering = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_antialiasing_filtering")) {
			DisplayManager.cg_antialiasing_filtering = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("player_sprint_unlimited")) {
			Player.runInfinite = Maths.intToBoolean(value);
			Logger.debug(variable + " has been set to " + Maths.intToBoolean(value));
		} else if (variable.equalsIgnoreCase("cg_quality")) {
			DisplayManager.cg_quality = value;
			Logger.debug(variable + " has been set to " + value);
		} else {
			Logger.error("Could not find variable " + variable + " with value " + value);
		}
	}
}