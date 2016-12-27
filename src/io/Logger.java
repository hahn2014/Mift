package io;

public class Logger {
	
	public static void debug(String message) {
		if (SettingHolder.get("cg_developer").getValueB()) {
			System.out.println("[DBUG] " + message);
		}
	}
	
	public static void warn(String msg) {
		System.out.println("[WARN] " + msg);
	}
	
	public static void info(String message) {
		System.out.println("[INFO] " + message);
	}
	
	public static void error(String message) {
		System.err.println("[ERRR] " + message);
	}
	
	public static void set(String message) {
		System.out.println("[SETV]" + message);
	}
}