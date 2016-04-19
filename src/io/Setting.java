package io;

public class Setting {
	public enum SettingType {
		integer,
		bool,
		string
	};
	
	private String variable;
	private SettingType type;
	
	private String defValueS;
	private String valueS;
	private boolean defValueB;
	private boolean valueB;
	private int defValueI;
	private int valueI;
	
	
	//debug drawing vars
	private final int spaceLength = 30;
	
	public Setting(String name, SettingType type, String defaultValue) {
		this.variable = name;
		this.type = type;
		this.defValueS = defaultValue;
		this.valueS = defaultValue;
	}
	
	public Setting(String name, SettingType type, boolean defaultValue) {
		this.variable = name;
		this.type = type;
		this.defValueB = defaultValue;
		this.valueB = defaultValue;
	}
	
	public Setting(String name, SettingType type, int defaultValue) {
		this.variable = name;
		this.type = type;
		this.defValueI = defaultValue;
		this.valueI = defaultValue;
	}
	
	public String getVariable() {
		return variable;
	}
	
	public SettingType getType() {
		return type;
	}
	
	public String getDefaultString() {
		return defValueS;
	}

	public boolean getDefaultBoolean() {
		return defValueB;
	}

	public int getDefaultInteger() {
		return defValueI;
	}
	
	public String getValueS() {
		return valueS;
	}
	
	public int getValueI() {
		return valueI;
	}
	
	public boolean getValueB() {
		return valueB;
	}
	
	public void setValueS(String value) {
		String spacer = "";
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueS = value;
		Logger.info(variable + ":" + spacer + "-> " + value);
	}
	
	public void setValueB(boolean value) {
		String spacer = "";
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueB = value;
		Logger.info(variable + ":" + spacer + "-> " + value);
	}
	
	public void setValueI(int value) {
		String spacer = "";
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueI = value;
		Logger.info(variable + ":" + spacer + "-> " + value);
	}
}