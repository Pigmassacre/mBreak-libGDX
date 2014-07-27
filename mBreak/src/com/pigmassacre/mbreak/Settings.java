package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

	private static final String PREFS_NAME = "mBreak";
	
	private static final String DEBUG_MODE = "debug_mode";
	
	public static float GAME_SCALE = 3f;
	public static int GAME_FPS = 60;
	
	public static int BASE_SCREEN_WIDTH = 285;
	public static int BASE_SCREEN_HEIGHT = 160;
	
	public static float LEVEL_WIDTH, LEVEL_HEIGHT, LEVEL_X, LEVEL_MAX_X, LEVEL_Y, LEVEL_MAX_Y;
	
	protected static Preferences getPreferences() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}
	
	public static boolean getDebugMode() {
		return getPreferences().getBoolean(DEBUG_MODE, true);
	}
	
	public static void setDebugMode(boolean mode) {
		getPreferences().putBoolean(DEBUG_MODE, mode);
	}
	
	public static float getLevelYOffset() {
		return -2 * Settings.GAME_SCALE;
	}

}
