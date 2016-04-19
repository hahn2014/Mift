package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.Setting.SettingType;

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
	
	private static void replaceFile() {
		File old = new File(settingsFile);
		old.delete();
		createSettingsFileNewVals();
	}

	private static void createSettingsFileNewVals() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(settingsFile));
			for (Setting set : SettingHolder.getAll()) {
				if (set.getType() == SettingType.integer) {
					bw.write(set.getVariable() + ": " + set.getValueI() + ";");
				} else if (set.getType() == SettingType.bool) {
					bw.write(set.getVariable() + ": " + set.getValueB() + ";");
				} else if (set.getType() == SettingType.string) {
					bw.write(set.getVariable() + ": " + set.getValueS() + ";");
				}
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Logger.debug("Settings file has been replaced");
	}

	private void createSettingsFile() {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(settingsFile));
			for (Setting set : SettingHolder.getAll()) {
				if (set.getType() == SettingType.integer) {
					bw.write(set.getVariable() + ": " + set.getDefaultInteger() + ";");
				} else if (set.getType() == SettingType.bool) {
					bw.write(set.getVariable() + ": " + set.getDefaultBoolean() + ";");
				} else if (set.getType() == SettingType.string) {
					bw.write(set.getVariable() + ": " + set.getDefaultString() + ";");
				}
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
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
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}

		String[] variables = listToArray(lines);
		for (String var : variables) {
			String vari = "";
			int valiAt = 0;
			int valiI = -1;
			String valiB = "";
			String valiS = "";
			for (int i = 0; i < var.length(); i++) {
				if (var.charAt(i) != ':') {
					vari += var.charAt(i);
				} else {
					valiAt = i + 1;
					break;
				}
			}

			if (isInteger(var.substring(valiAt, var.length()))) { // integer
				valiI = Integer.parseInt(var.substring(valiAt, var.length()));
			} else if (isBoolean(var.substring(valiAt, var.length()))) { // boolean
				valiB = var.substring(valiAt, var.length());
			} else { // String
				valiS = var.substring(valiAt, var.length());
			}

			if (valiI != -1 && valiB == "" && valiS == "") { // value is a
																// integer
				applyChanges(vari, Integer.toString(valiI));
			} else if (valiI == -1 && valiB != "" && valiS == "") { // value is
																	// a boolean
				applyChanges(vari, valiB);
			} else if (valiI == -1 && valiB == "" && valiS != "") {
				applyChanges(vari, valiS);
			} else {
				Logger.error("Something messed up while importing " + vari + " at value "
						+ var.substring(valiAt, var.length()));
			}
		}
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	private static boolean isBoolean(String s) {
		try {
			Boolean.parseBoolean(s);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static void saveSettings() {
		Logger.debug("Saving Settings to file");
		if (settingsF.exists()) {
			Logger.debug("Settings file was found, attempting to delete and recreate.");
			replaceFile();
		} else {
			Logger.debug("Setings file was not found, creating file with new values.");
			createSettingsFileNewVals();
		}
	}

	private String[] listToArray(List<String> lines) {
		List<String> variables = new ArrayList<String>();
		String curVar = "";
		for (String line : lines) {
			for (int i = 0; i < line.length(); i++) {
				if (line.charAt(i) == ';') {
					variables.add(curVar);
					curVar = "";
					break;
				} else {
					if (line.charAt(i) != ' ') {
						curVar += line.charAt(i);
					}
				}
			}
		}
		return variables.toArray(new String[variables.size()]);
	}

	private void applyChanges(String var, String val) {
		if (SettingHolder.get(var).getType() == SettingType.integer) {
			try {
				SettingHolder.get(var).setValueI(Integer.parseInt(val));
			} catch (Exception e) {
				SettingHolder.get(var).setValueI(SettingHolder.get(var).getDefaultInteger());
			}
		} else if (SettingHolder.get(var).getType() == SettingType.string) {
			try {
				SettingHolder.get(var).setValueS(val);
			} catch (Exception e) {
				SettingHolder.get(var).setValueS(SettingHolder.get(var).getDefaultString());
			}
		} else if (SettingHolder.get(var).getType() == SettingType.bool) {
			try {
				SettingHolder.get(var).setValueB(Boolean.parseBoolean(val));
			} catch (Exception e) {
				SettingHolder.get(var).setValueB(SettingHolder.get(var).getDefaultBoolean());
			}
		}
	}
}