package com.pigmassacre.mbreak;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopGame {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		/*config.vSyncEnabled = false;
		config.foregroundFPS = 0;
		config.backgroundFPS = 0;*/
		config.width = 285 * Settings.GAME_SCALE;
		config.height = 160 * Settings.GAME_SCALE;
		config.useGL20 = true;
		config.title = "mBreak";
		config.addIcon("icon/icon16.png", Files.FileType.Internal);
		config.addIcon("icon/icon32.png", Files.FileType.Internal);
		config.addIcon("icon/icon128.png", Files.FileType.Internal);
		new LwjglApplication(new MBreak(), config);
	}
}