package io;

public class Setting {
	public enum SettingType {
		integer,
		bool,
		string
	};
	
	private String variable;
	private String debugName;
	private String definition;
	private SettingType type;
	
	private String defValueS;
	private String valueS;
	
	private boolean defValueB;
	private boolean valueB;
	
	private int defValueI;
	private int valueI;
	private int min;
	private int max;
	private int columnIndex;
	private int rowIndex;
	
	//debug drawing vars
	private final int spaceLength = 30;
	
	public Setting(String name, String debugName, String def, String defaultValue, int column, int row) {
		this.variable = name;
		this.debugName = debugName;
		this.definition = def;
		this.type = SettingType.string;
		this.defValueS = defaultValue;
		this.valueS = defaultValue;
		columnIndex = column;
		rowIndex = row;
	}
	
	public Setting(String name, String debugName, String def, boolean defaultValue, int column, int row) {
		this.variable = name;
		this.debugName = debugName;
		this.definition = def;
		this.type = SettingType.bool;
		this.defValueB = defaultValue;
		this.valueB = defaultValue;
		columnIndex = column;
		rowIndex = row;
	}
	
	public Setting(String name, String debugName, String def, int defaultValue, int column, int row, int min, int max) {
		this.variable = name;
		this.debugName = debugName;
		this.definition = def;
		this.type = SettingType.integer;
		this.defValueI = defaultValue;
		this.valueI = defaultValue;
		this.min = min;
		this.max = max;
		columnIndex = column;
		rowIndex = row;
	}
	
	public String getVariable() {
		return variable;
	}
	
	public String getDefinition() {
		return definition;
	}
	
	public String getDebugName() {
		return debugName;
	}
	
	public String getDebugNameAndValue() {
		return debugName + " [" + getValueDebug() + "]";
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
	
	public int getMin() {
		return min;
	}
	
	public int getMax() {
		return max;
	}
	
	public boolean getValueB() {
		return valueB;
	}
	
	public int getColumnIndex() {
		return columnIndex;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	
	public void setValueS(String value) {
		String spacer = "";
		System.out.println("here");
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueS = value;
		Logger.set(variable + ":" + spacer + "-> " + value);
	}
	
	public void setValueB(boolean value) {
		String spacer = "";
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueB = value;
		Logger.set(variable + ":" + spacer + "-> " + value);
	}
	
	public void setValueI(int value) {
		String spacer = "";
		for (int i = 0; i < spaceLength - variable.length(); i++) {spacer += " ";}
		valueI = value;
		Logger.set(variable + ":" + spacer + "-> " + value);
	}
	
	public String getValueDebug() {
		if (type == SettingType.bool) {
			return (valueB ? "On" : "Off");
		} else if (type == SettingType.integer) {
			return valueI + "";
		} else if (type == SettingType.string) {
			return valueS;
		} else {
			return "null";
		}
	}
	
	public String getQualityDebug() {
		if (type == SettingType.integer) {
			if (valueI == 1) {
				return "Low";
			} else if (valueI == 2) {
				return "Medium";
			} else if (valueI == 3) {
				return "High";
			} else if (valueI == 4) {
				return "Ultra";
			}
		}
		return "null";
	}
}