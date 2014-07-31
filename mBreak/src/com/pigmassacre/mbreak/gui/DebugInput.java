package com.pigmassacre.mbreak.gui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pigmassacre.mbreak.MusicHandler;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Block;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Player;
import com.pigmassacre.mbreak.objects.powerups.ElectricityPowerup;
import com.pigmassacre.mbreak.objects.powerups.EnlargerPowerup;
import com.pigmassacre.mbreak.objects.powerups.FirePowerup;
import com.pigmassacre.mbreak.objects.powerups.FrostPowerup;
import com.pigmassacre.mbreak.objects.powerups.MultiballPowerup;
import com.pigmassacre.mbreak.objects.powerups.Powerup;
import com.pigmassacre.mbreak.objects.powerups.ReducerPowerup;
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
		if (Settings.getDebugMode()) {
			showFPSCounter();
		}
	}
	
	private boolean leftPlayer = true;
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (Settings.getDebugMode()) {
			Vector3 coords = new Vector3(screenX, screenY, 0);
			stage.getCamera().unproject(coords);
			if (stage != null && (pointer == 0 || button == Buttons.LEFT) && rectangle.contains(coords.x, coords.y)) {
				Ball ball = Ball.ballPool.obtain();
				Player player;
				if (leftPlayer) {
					player = (Player) Groups.playerGroup.getChildren().first();
				} else {
					player = (Player) Groups.playerGroup.getChildren().peek();
				}
				leftPlayer = !leftPlayer;
				ball.init(coords.x, coords.y, (float) (MathUtils.random() * 2 * Math.PI), player);
			}
		}
		return false;
	}
	
	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.F3:
			Settings.setDebugMode(!Settings.getDebugMode());
			
			if (Settings.getDebugMode()) {
				createDebugMessage("Debug Mode ON");
				Gdx.input.setCursorCatched(false);
				showFPSCounter();
			} else {
				createDebugMessage("Debug Mode OFF");
				Gdx.input.setCursorCatched(true);
				hideFPSCounter();
			}
			
			break;
		case Keys.R:
			if (Settings.getDebugMode()) {
				SnapshotArray<Actor> array = Groups.ballGroup.getChildren();
				Actor[] items = array.begin();
				for (int i = 0, n = array.size; i < n; i++) {
					((Ball) items[i]).reset();
				}
				array.end();
			}
			break;
		case Keys.P:
			if (Settings.getDebugMode()) {
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					for (int i = 0; i < 10; i++) {
						spawnRandomPowerup();
					}
				} else {
					spawnRandomPowerup();
				}
				
			}
			break;
		case Keys.M:
			if (Settings.getDebugMode()) {
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					screen.timeScale += 1f;
				} else {
					screen.timeScale += 0.1f;
				}
				createDebugMessage("Set timescale to " + screen.timeScale);
			}
			break;
		case Keys.N:
			if (Settings.getDebugMode()) {
				if (Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) {
					screen.timeScale -= 1f;
				} else {
					screen.timeScale -= 0.1f;
				}
				createDebugMessage("Set timescale to " + screen.timeScale);
			}
			break;
		case Keys.B:
			if (Settings.getDebugMode()) {
				screen.timeScale = 1f;
				createDebugMessage("Reset timescale to " + screen.timeScale);
			}
			break;
		case Keys.Z:
			if (Settings.getDebugMode()) {
				MusicHandler.prev();
				createDebugMessage("Now Playing: " + MusicHandler.getNameOfCurrentSong());
			}
			break;
		case Keys.X:
			if (Settings.getDebugMode()) {
				MusicHandler.next();
				createDebugMessage("Now Playing: " + MusicHandler.getNameOfCurrentSong());
			}
			break;
		}
		return true;
	}
	
	private void spawnRandomPowerup() {
		float powerupWidth = Settings.LEVEL_WIDTH / 2 - 4f * Settings.GAME_SCALE;
		float powerupHeight = Settings.LEVEL_HEIGHT - 8f * Settings.GAME_SCALE;
		float powerupX = Settings.LEVEL_X + powerupWidth / 2f;
		float powerupY = Settings.LEVEL_Y;
		Powerup powerup;
		switch(MathUtils.random(6)) {
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
		case 4:
			powerup = new EnlargerPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
			break;
		case 5:
			powerup = new ReducerPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
			break;
		case 6:
			powerup = new MultiballPowerup(powerupX + MathUtils.random() * powerupWidth, powerupY + MathUtils.random() * powerupHeight);
			break;
		}
	}
	
	private static TextItem debugTextItem;
	private static Timeline debugTextSequence;
	
	private void createDebugMessage(CharSequence string) {
		if (debugTextItem != null) {
			debugTextItem.remove();
		}
		if (debugTextSequence != null) {
			debugTextSequence.free();
		}
		
		debugTextItem = new TextItem(string);
//		debugTextItem.setScale(debugTextItem.getScaleX() * 0.75f, debugTextItem.getScaleY() * 0.75f);
		debugTextItem.setColor(1f, 1f, 1f, 0.75f);
		debugTextItem.setX((Gdx.graphics.getWidth() - debugTextItem.getWidth()) / 2);
		debugTextItem.setY(Gdx.graphics.getHeight() + debugTextItem.getHeight());
		stage.addActor(debugTextItem);

		debugTextSequence = Timeline.createSequence()
			.push(Tween.to(debugTextItem, ActorAccessor.POSITION_Y, 2f)
					.target(Gdx.graphics.getHeight() - 2f * debugTextItem.getScaleY())
					.ease(TweenEquations.easeOutExpo))
			.pushPause(0.25f)
			.push(Tween.to(debugTextItem, ActorAccessor.POSITION_Y, 2f)
					.target(Gdx.graphics.getHeight() + debugTextItem.getHeight())
					.ease(TweenEquations.easeInExpo))
					.setCallback(new TweenCallback() {
						
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							debugTextItem.remove();
						}
						
					})
			.start(screen.getTweenManager());
	}
	
	private static TextItem fpsCounterTextItem;
	
	private void showFPSCounter() {
		if (fpsCounterTextItem == null) {
			fpsCounterTextItem = new TextItem(Integer.toString(Gdx.graphics.getFramesPerSecond()));
			fpsCounterTextItem.setColor(1f, 1f, 1f, 0.75f);
			fpsCounterTextItem.setX(4 * Settings.GAME_SCALE);
			fpsCounterTextItem.setY(Gdx.graphics.getHeight() - 2 * Settings.GAME_SCALE);
			fpsCounterTextItem.setActCallback(new ItemCallback() {
				
				@Override
				public void execute(Item data) {
					fpsCounterTextItem.setString(Integer.toString(Gdx.graphics.getFramesPerSecond()));
				}
				
			});
		}
		
		if (!stage.getActors().contains(fpsCounterTextItem, true)) {
			stage.addActor(fpsCounterTextItem);
		}
	}
	
	private void hideFPSCounter() {
		fpsCounterTextItem.remove();
	}
	
}
