package com.pigmassacre.mbreaklibgdx;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigmassacre.mbreaklibgdx.gui.ActorAccessor;
import com.pigmassacre.mbreaklibgdx.gui.Item;
import com.pigmassacre.mbreaklibgdx.gui.Item.ItemCallback;
import com.pigmassacre.mbreaklibgdx.gui.TextItem;
import com.pigmassacre.mbreaklibgdx.objects.Ball;
import com.pigmassacre.mbreaklibgdx.objects.Groups;
import com.pigmassacre.mbreaklibgdx.objects.Level;
import com.pigmassacre.mbreaklibgdx.objects.Player;
import com.pigmassacre.mbreaklibgdx.objects.powerups.Powerup;
import com.pigmassacre.mbreaklibgdx.screens.AbstractScreen;
import com.pigmassacre.mbreaklibgdx.screens.GameScreen;

public enum Debugger {
	INSTANCE;

	private Stage debugStage;
	private TweenManager tweenManager;

	private AbstractScreen screen;
	private Stage screenStage;

	private Rectangle levelRectangle;

	private DebugInputAdapter inputAdapter;

	public void setup(AbstractScreen screen, Stage stage) {
		if (debugStage == null) {
			debugStage = new Stage(new ScreenViewport());
			System.out.println("new");
		}

		if (tweenManager == null) {
			tweenManager = new TweenManager();
		}

		this.screen = screen;
		this.screenStage = stage;

		levelRectangle = new Rectangle(Settings.LEVEL_X, Settings.LEVEL_Y, Settings.LEVEL_WIDTH, Settings.LEVEL_HEIGHT);

		if (Settings.getDebugMode()) {
			showFPSCounter();
		}

		this.inputAdapter = new DebugInputAdapter();
	}

	public DebugInputAdapter getInputAdapter() {
		return inputAdapter;
	}

	public void render() {
		tweenManager.update(Gdx.graphics.getDeltaTime());
		debugStage.act(Gdx.graphics.getDeltaTime());
		debugStage.draw();
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
		debugTextItem.setScale(debugTextItem.getScaleX() * 0.75f, debugTextItem.getScaleY() * 0.75f);
		debugTextItem.setColor(1f, 1f, 1f, 0.75f);
		debugTextItem.setX((Gdx.graphics.getWidth() - debugTextItem.getWidth()) / 2);
		debugTextItem.setY(Gdx.graphics.getHeight() + debugTextItem.getHeight());
		debugStage.addActor(debugTextItem);

		debugTextSequence = Timeline.createSequence()
				.push(Tween.to(debugTextItem, ActorAccessor.POSITION_Y, 1f)
						.target(Gdx.graphics.getHeight() - 2f * debugTextItem.getScaleY())
						.ease(TweenEquations.easeOutExpo))
				.pushPause(0.25f)
				.push(Tween.to(debugTextItem, ActorAccessor.POSITION_Y, 1f)
						.target(Gdx.graphics.getHeight() + debugTextItem.getHeight())
						.ease(TweenEquations.easeInExpo))
				.setCallback(new TweenCallback() {

					@Override
					public void onEvent(int type, BaseTween<?> source) {
						debugTextItem.remove();
					}

				})
				.start(tweenManager);
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
					fpsCounterTextItem.setText(Integer.toString(Gdx.graphics.getFramesPerSecond()));
				}

			});
		}

		if (!debugStage.getActors().contains(fpsCounterTextItem, true)) {
			debugStage.addActor(fpsCounterTextItem);
		}
	}

	private void hideFPSCounter() {
		fpsCounterTextItem.remove();
	}

	private class DebugInputAdapter extends InputAdapter {

		private boolean leftPlayer = true;

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			if (Settings.getDebugMode() && screenStage != null && screen instanceof GameScreen) {
				Vector3 coords = screenStage.getCamera().unproject(new Vector3(screenX, screenY, 0));
				if ((pointer == 0 || button == Input.Buttons.LEFT) && levelRectangle.contains(coords.x, coords.y)) {
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
			switch (keycode) {
				case Input.Keys.F3:
					Settings.setDebugMode(!Settings.getDebugMode());

					if (Settings.getDebugMode()) {
						createDebugMessage("Debug Mode ON");
						showFPSCounter();
						if (screen instanceof GameScreen) {
							Gdx.input.setCursorCatched(false);
						}
					} else {
						createDebugMessage("Debug Mode OFF");
						hideFPSCounter();
						if (screen instanceof GameScreen) {
							Gdx.input.setCursorCatched(true);
						}
					}

					break;
				case Input.Keys.R:
					if (Settings.getDebugMode() && screen instanceof GameScreen) {
						SnapshotArray<Actor> array = Groups.ballGroup.getChildren();
						Actor[] items = array.begin();
						for (int i = 0, n = array.size; i < n; i++) {
							((Ball) items[i]).reset();
						}
						array.end();
					}
					break;
				case Input.Keys.P:
					if (Settings.getDebugMode() && screen instanceof GameScreen) {
						if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
							for (int i = 0; i < 10; i++) {
								Level.getCurrentLevel().spawnPowerup();
							}
						} else {
							Level.getCurrentLevel().spawnPowerup();
						}

					}
					break;
				case Input.Keys.O:
					if (Settings.getDebugMode() && screen instanceof GameScreen) {
						SnapshotArray<Actor> array = Groups.powerupGroup.getChildren();
						Object[] items = array.begin();
						for (int i = 0, n = array.size; i < n; i++) {
							Powerup powerup = (Powerup) items[i];
							powerup.destroy();
						}
						array.end();
					}
					break;
				case Input.Keys.M:
					if (Settings.getDebugMode()) {
						if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
							screen.timeScale += 1f;
						} else {
							screen.timeScale += 0.1f;
						}
						createDebugMessage("Set timescale to " + screen.timeScale);
					}
					break;
				case Input.Keys.N:
					if (Settings.getDebugMode()) {
						if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
							screen.timeScale -= 1f;
						} else {
							screen.timeScale -= 0.1f;
						}
						createDebugMessage("Set timescale to " + screen.timeScale);
					}
					break;
				case Input.Keys.B:
					if (Settings.getDebugMode()) {
						screen.timeScale = 1f;
						createDebugMessage("Reset timescale to " + screen.timeScale);
					}
					break;
				case Input.Keys.Z:
					if (Settings.getDebugMode()) {
						MusicHandler.prev();
						createDebugMessage("Now Playing: " + MusicHandler.getNameOfCurrentSong());
					}
					break;
				case Input.Keys.X:
					if (Settings.getDebugMode()) {
						MusicHandler.next();
						createDebugMessage("Now Playing: " + MusicHandler.getNameOfCurrentSong());
					}
					break;
			}
			return false;
		}

	}

}
