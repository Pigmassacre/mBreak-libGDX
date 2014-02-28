package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {

	private static final String PREFS_NAME = "mBreak";
	
	private static final String DEBUG_MODE = "debug_mode";
	
	public static int GAME_SCALE = 5;
	public static int GAME_FPS = 60;
	
	public static int BASE_SCREEN_WIDTH = 285;
	public static int BASE_SCREEN_HEIGHT = 160;
	
	public Settings() {

	}
	
	protected Preferences getPreferences() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}
	
	public boolean getDebugMode() {
		return getPreferences().getBoolean(DEBUG_MODE, true);
	}
	
	public void setDebugMode(boolean mode) {
		getPreferences().putBoolean(DEBUG_MODE, mode);
	}

}
