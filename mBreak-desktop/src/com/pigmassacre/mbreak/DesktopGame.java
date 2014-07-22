package com.pigmassacre.mbreak;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.fullscreen = false;
		
		if (config.fullscreen) {
			DisplayMode[] displayModes = LwjglApplicationConfiguration.getDisplayModes(); 
			for (int i = 0; i < displayModes.length; i++) {
				System.out.println(displayModes[i]);
			}
			config.setFromDisplayMode(displayModes[0]);
			
			float newScale = displayModes[0].width / Settings.BASE_SCREEN_WIDTH;
	        Settings.GAME_SCALE = newScale + 1;
		} else {
			config.width = (int) (285 * Settings.GAME_SCALE);
			config.height = (int) (160 * Settings.GAME_SCALE);
		}
		
		config.useGL20 = true;
		config.title = "mBreak";
		config.addIcon("icon/icon16.png", Files.FileType.Internal);
		config.addIcon("icon/icon32.png", Files.FileType.Internal);
		config.addIcon("icon/icon128.png", Files.FileType.Internal);
		new LwjglApplication(new MBreak(), config);
	}
}