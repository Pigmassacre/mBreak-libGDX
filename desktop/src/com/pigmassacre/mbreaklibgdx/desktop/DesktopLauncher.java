package com.pigmassacre.mbreaklibgdx.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.pigmassacre.mbreaklibgdx.MBreak;
import com.pigmassacre.mbreaklibgdx.Settings;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		TexturePacker.processIfModified("../../texturepackerassets/menu", "images", "menutextures");
		TexturePacker.processIfModified("../../texturepackerassets/game", "images", "gametextures");

		config.fullscreen = false;

		if (config.fullscreen) {
			config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
			float newScale = LwjglApplicationConfiguration.getDesktopDisplayMode().width / Settings.BASE_SCREEN_WIDTH;
			Settings.GAME_SCALE = (int) Math.ceil(newScale + 1);
		} else {
			config.width = 285 * Settings.GAME_SCALE;
			config.height = 160 * Settings.GAME_SCALE;
		}

		System.out.println("Set GAME_SCALE to " + Settings.GAME_SCALE);

		//config.useGL30 = true;
		config.title = "mBreak";
		config.addIcon("icon/icon16.png", Files.FileType.Internal);
		config.addIcon("icon/icon32.png", Files.FileType.Internal);
		config.addIcon("icon/icon128.png", Files.FileType.Internal);
		new LwjglApplication(new MBreak(), config);
	}
}
