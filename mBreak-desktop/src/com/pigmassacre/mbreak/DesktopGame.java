package com.pigmassacre.mbreak;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
	public static void main (String[] args) {
	     new LwjglApplication(new Game(), "mBreak", Settings.getScreenWidth(), Settings.getScreenHeight(), true);
	}
}