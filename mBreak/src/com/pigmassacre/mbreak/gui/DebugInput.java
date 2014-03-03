package com.pigmassacre.mbreak.gui;

import java.util.Random;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Player;

public class DebugInput extends InputAdapter {

	private static Random random = new Random();
	
	private Stage stage;
	private Camera camera;
	
	public DebugInput(Stage stage, Camera camera) {
		this.stage = stage;
		this.camera = camera;
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 coords = new Vector3(screenX, screenY, 0);
		camera.unproject(coords);
		if (stage != null) {
			Ball ball = new Ball(coords.x, coords.y, (float) (random.nextFloat() * Math.PI), new Player());
			stage.addActor(ball);
		}
		return false;
	}
	
}
