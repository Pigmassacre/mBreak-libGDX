package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Player;
import com.pigmassacre.mbreak.objects.powerups.ElectricityPowerup;
import com.pigmassacre.mbreak.objects.powerups.FirePowerup;
import com.pigmassacre.mbreak.objects.powerups.FrostPowerup;
import com.pigmassacre.mbreak.objects.powerups.Powerup;
import com.pigmassacre.mbreak.objects.powerups.SpeedPowerup;
import com.pigmassacre.mbreak.screens.AbstractScreen;

public class DebugInput extends InputAdapter {

	private AbstractScreen screen;
	private Stage stage;
	
	private Rectangle rectangle;
	
	public DebugInput(AbstractScreen screen, Stage stage) {
		this.screen = screen;
		this.stage = stage;
		rectangle = new Rectangle(Settings.LEVEL_X, Settings.LEVEL_Y, Settings.LEVEL_WIDTH, Settings.LEVEL_HEIGHT);
	}
	
	private boolean leftPlayer = true;
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 coords = new Vector3(screenX, screenY, 0);
		stage.getCamera().unproject(coords);
		if (stage != null && (pointer == 0 || button == Buttons.LEFT) && rectangle.contains(coords.x, coords.y)) {
			Ball ball = new Ball();
			Player player;
			if (leftPlayer) {
				player = (Player) Groups.playerGroup.getChildren().first();
			} else {
				player = (Player) Groups.playerGroup.getChildren().peek();
			}
			leftPlayer = !leftPlayer;
			ball.init(coords.x, coords.y, (float) (MathUtils.random() * 2 * Math.PI), player, player.getColor());
		}
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.R:
			// Reset all active balls.
			SnapshotArray<Actor> array = Groups.ballGroup.getChildren();
			Actor[] items = array.begin();
			for (int i = 0, n = array.size; i < n; i++) {
				Actor item = items[i];
				((Ball) item).reset();
			}
			array.end();
			break;
		case Keys.P:
			float powerupWidth = Settings.LEVEL_WIDTH / 2 - 8 * Settings.GAME_SCALE;
			float powerupHeight = Settings.LEVEL_HEIGHT - 8 * Settings.GAME_SCALE;
			float powerupX = Settings.LEVEL_X + powerupWidth / 2;
			float powerupY = Settings.LEVEL_Y;
			Powerup powerup;
			switch(MathUtils.random(3)) {
			case 0:
				powerup = new FirePowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
				break;
			case 1:
				powerup = new SpeedPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
				break;
			case 2:
				powerup = new ElectricityPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
				break;
			case 3:
				powerup = new FrostPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
				break;
			}
			break;
		case Keys.M:
			screen.timeScale += 0.1f;
			break;
		case Keys.N:
			screen.timeScale -= 0.1f;
			break;
		case Keys.B:
			screen.timeScale = 1f;
			break;
		}
		return true;
	}
	
}
