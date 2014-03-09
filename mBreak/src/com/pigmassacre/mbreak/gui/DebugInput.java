package com.pigmassacre.mbreak.gui;

import java.util.Random;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Player;

public class DebugInput extends InputAdapter {

	private static Random random = new Random();
	
	private Stage stage;
	
	private Rectangle rectangle;
	
	public DebugInput(Stage stage) {
		this.stage = stage;
		rectangle = new Rectangle(Settings.LEVEL_X, Settings.LEVEL_Y, Settings.LEVEL_WIDTH, Settings.LEVEL_HEIGHT);
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 coords = new Vector3(screenX, screenY, 0);
		stage.getCamera().unproject(coords);
		if (stage != null && (pointer == 0 || button == Buttons.LEFT) && rectangle.contains(coords.x, coords.y)) {
			new Ball(coords.x, coords.y, (float) (random.nextFloat() * 2 * Math.PI), new Player("test"), new Color(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1.0f));
		}
		return false;
	}
	
}
