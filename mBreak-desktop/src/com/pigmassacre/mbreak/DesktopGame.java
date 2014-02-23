package com.pigmassacre.mbreak;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopGame {
	public static void main (String[] args) {
	     new LwjglApplication(new MBreak(), "mBreak", 285 * 4, 160 * 4, true);
	}
}