package io;

import renderEngine.DisplayManager;

public class Logger {
	
	public static void debug(String message) {
		if (DisplayManager.cg_developer_status == true) {
			System.out.println("[DBUG] " + message);
		}
	}
	
	public static void info(String message) {
		System.out.println("[INFO] " + message);
	}
	
	public static void error (String message) {
		System.err.println("[ERRR] " + message);
	}
}