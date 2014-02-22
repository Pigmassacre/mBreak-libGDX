package com.pigmassacre.mbreak;

public class Settings {

	public static int GAME_SCALE = 4;
	
	private static int BASE_SCREEN_WIDTH = 285;
	private static int BASE_SCREEN_HEIGHT = 160;
	
	public static int getScreenWidth() {
		return BASE_SCREEN_WIDTH * GAME_SCALE;
	}
	
	public static int getScreenHeight() {
		return BASE_SCREEN_HEIGHT * GAME_SCALE;
	}
	
}
