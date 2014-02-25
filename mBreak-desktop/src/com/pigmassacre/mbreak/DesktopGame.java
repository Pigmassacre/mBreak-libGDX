package com.pigmassacre.mbreak;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
	public static void main (String[] args) {
	     new LwjglApplication(new MBreak(), "mBreak", 285 * Settings.GAME_SCALE, 160 * Settings.GAME_SCALE, true);
	}
}