package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.MusicHandler;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.DebugInput;
import com.pigmassacre.mbreak.gui.GameActorAccessor;
import com.pigmassacre.mbreak.gui.Sunrays;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Block;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Paddle;
import com.pigmassacre.mbreak.objects.Player;

public class GameScreen extends AbstractScreen {

	Player leftPlayer, rightPlayer;
	Paddle leftPaddle, rightPaddle;
	
	Sunrays sunrays;
	
	float oldSpeed;
	
	public GameScreen(MBreak game, Sunrays givenSunrays, Color leftColor, Color rightColor) {
		super(game);
		
		if (givenSunrays == null) {
			sunrays = new Sunrays();
		} else {
			sunrays = givenSunrays;
		}
		sunrays.setX(Gdx.graphics.getWidth() / 2 - sunrays.getWidth() / 2);
		sunrays.setY(Gdx.graphics.getHeight() / 2 - sunrays.getHeight() / 2);
		sunrays.offsetY = Settings.getLevelYOffset();
		stage.addActor(sunrays);
		
		Settings.LEVEL_WIDTH = Assets.getTextureRegion("glass/floor").getRegionWidth() * Settings.GAME_SCALE;
		Settings.LEVEL_HEIGHT = Assets.getTextureRegion("glass/floor").getRegionHeight() * Settings.GAME_SCALE;
		Settings.LEVEL_X = (Gdx.graphics.getWidth() - Settings.LEVEL_WIDTH) / 2f;
		Settings.LEVEL_Y = (Gdx.graphics.getHeight() - Settings.LEVEL_HEIGHT) / 2f;
		Settings.LEVEL_MAX_X = Settings.LEVEL_X + Settings.LEVEL_WIDTH;
		Settings.LEVEL_MAX_Y = Settings.LEVEL_Y + Settings.LEVEL_HEIGHT;
		
//		System.out.println(Assets.getTextureRegion("glass/floor").getRegionWidth());
//		System.out.println(Assets.getTextureRegion("glass/floor").getRegionWidth() * Settings.GAME_SCALE);
//		System.out.println(Assets.getTextureRegion("glass/floor").getRegionHeight());
//		System.out.println(Assets.getTextureRegion("glass/floor").getRegionHeight() * Settings.GAME_SCALE);

		Level.setCurrentLevel("glass");
		
		leftPlayer = new Player("left");
		leftPlayer.setX(2.5f * Settings.GAME_SCALE);
		leftPlayer.setY(2.5f * Settings.GAME_SCALE);
		leftPlayer.setColor(leftColor);
		Groups.playerGroup.addActor(leftPlayer);
		
		float delay = 0.25f;
		float z;
		Block tempBlock = new Block(0, 0, new Player("temp"), new Color(Color.BLACK));
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < (int) Settings.LEVEL_HEIGHT / tempBlock.getHeight(); y++) {
				Block block = new Block(Settings.LEVEL_X + x * tempBlock.getWidth(), Settings.LEVEL_MAX_Y - tempBlock.getHeight() - (y * tempBlock.getHeight()), leftPlayer,
						leftPlayer.getColor());
				z = block.getZ();
				block.setZ(350 * Settings.GAME_SCALE);
				Tween.to(block, GameActorAccessor.Z, 2f)
					.target(z)
					.ease(TweenEquations.easeOutExpo)
					.delay(delay)
					.start(getTweenManager());
				delay += 0.025f;
			}
		}
		
		leftPaddle = new Paddle(leftPlayer);
		leftPaddle.setX(Settings.LEVEL_X + leftPaddle.getWidth() * 4);
		leftPaddle.setY((Gdx.graphics.getHeight() - leftPaddle.getHeight()) / 2);
		leftPaddle.touchRectangle = new Rectangle(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		leftPaddle.keyUp = Keys.W;
		leftPaddle.keyDown = Keys.S;

		z = leftPaddle.getZ();
		leftPaddle.setZ(1000);
		Tween.to(leftPaddle, GameActorAccessor.Z, 2f)
			.target(z)
			.ease(TweenEquations.easeOutExpo)
			.delay(delay + 0.025f)
			.start(getTweenManager());

		rightPlayer = new Player("right");
		rightPlayer.setX(Gdx.graphics.getWidth() - 5f * Settings.GAME_SCALE - 2.5f * Settings.GAME_SCALE);
		rightPlayer.setY(Gdx.graphics.getHeight() - 5f * Settings.GAME_SCALE - 2.5f * Settings.GAME_SCALE);
		rightPlayer.setColor(rightColor);
		Groups.playerGroup.addActor(rightPlayer);
		
		delay = 0.25f;
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < (int) Settings.LEVEL_HEIGHT / tempBlock.getHeight(); y++) {
				Block block = new Block(Settings.LEVEL_MAX_X - ((x + 1) * tempBlock.getWidth()), Settings.LEVEL_MAX_Y - tempBlock.getHeight() - (y * tempBlock.getHeight()),
						rightPlayer, rightPlayer.getColor());
				z = block.getZ();
				block.setZ(350 * Settings.GAME_SCALE);
				Tween.to(block, GameActorAccessor.Z, 2f)
					.target(z)
					.ease(TweenEquations.easeOutExpo)
					.delay(delay)
					.start(getTweenManager());
				delay += 0.025f;
			}
		}
		
		rightPaddle = new Paddle(rightPlayer);
		rightPaddle.setX(Settings.LEVEL_MAX_X - rightPaddle.getWidth() * 5);
		rightPaddle.setY((Gdx.graphics.getHeight() - rightPaddle.getHeight()) / 2);

		rightPaddle.touchRectangle = new Rectangle(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		rightPaddle.keyUp = Keys.UP;
		rightPaddle.keyDown = Keys.DOWN;
		
		z = rightPaddle.getZ();
		rightPaddle.setZ(1000);
		Tween.to(rightPaddle, GameActorAccessor.Z, 2f)
			.target(z)
			.ease(TweenEquations.easeOutExpo)
			.delay(delay + 0.025f)
			.start(getTweenManager());

		tempBlock.destroy(false);
		
		Ball ball = Ball.ballPool.obtain();
		
		Player startingPlayer;
		float angle;
		if (MathUtils.randomBoolean()) {
			startingPlayer = leftPlayer;
			angle = MathUtils.PI;
		} else {
			startingPlayer = rightPlayer;
			angle = 0f;
		}
		
		ball.init(Settings.LEVEL_X + (Settings.LEVEL_WIDTH - ball.getWidth()) / 2, Settings.LEVEL_Y + (Settings.LEVEL_HEIGHT - ball.getHeight()) / 2, angle, startingPlayer);
		oldSpeed = ball.speed;
		ball.speed = 0f;
		z = ball.getZ();
		ball.setZ(1000);
		Tween.to(ball, GameActorAccessor.Z, 2f)
			.target(z)
			.ease(TweenEquations.easeOutExpo)
			.delay(delay + 1.25f)
			.setUserData(ball)
			.setCallback(new TweenCallback() {
				
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					((Ball) source.getUserData()).speed = oldSpeed;
				}
				
			})
			.start(getTweenManager());
		
		MusicHandler.stop();
		MusicHandler.addSong("music/game/choke.ogg", "choke");
		MusicHandler.addSong("music/game/divine_intervention.ogg", "Divine Intervention");
		MusicHandler.addSong("music/game/socialmoron.ogg", "Social Moron");
		MusicHandler.addSong("music/game/stardstm.ogg", "stardust memories");
		MusicHandler.setShuffle(true);
		MusicHandler.play();
		
		stage.addActor(Level.getCurrentLevel().getBackground());
		stage.addActor(Groups.playerGroup);
		stage.addActor(Groups.residueGroup);
		stage.addActor(Groups.shadowGroup);
		stage.addActor(Groups.traceGroup);
		stage.addActor(Groups.blockGroup);
		stage.addActor(Groups.powerupGroup);
		stage.addActor(Groups.ballGroup);
		stage.addActor(Groups.paddleGroup);
		stage.addActor(Groups.particleGroup);
		stage.addActor(Level.getCurrentLevel().getForeground());
	}
	
	@Override
	public void renderClearScreen(float delta) {
		float leftPlayerBlockCount = 0, rightPlayerBlockCount = 0;
		SnapshotArray<Actor> array = Groups.blockGroup.getChildren();
		Object[] items = array.begin();
		for (int i = 0, n = array.size; i < n; i++) {
			Object item = items[i];
			Block block = (Block) item;
			if (block.owner == leftPlayer) {
				leftPlayerBlockCount++;
			} else {
				rightPlayerBlockCount++;
			}
		}
		array.end();
		
		float strength = 0;
		Player winningPlayer = null;
		if (leftPlayerBlockCount > rightPlayerBlockCount) {
			strength = (leftPlayerBlockCount - rightPlayerBlockCount) / (leftPlayerBlockCount + rightPlayerBlockCount);
			winningPlayer = leftPlayer;
		} else if (leftPlayerBlockCount < rightPlayerBlockCount) {
			strength = (rightPlayerBlockCount - leftPlayerBlockCount) / (leftPlayerBlockCount + rightPlayerBlockCount);
			winningPlayer = rightPlayer;
		} else {
//			Color color = leftPlayer.getColor().cpy();
//			Color temp = rightPlayer.getColor().cpy();
//			color.r += temp.r;
//			if (color.r > 0) {
//				color.r -= 1;
//			}
//			color.g += temp.g;
//			if (color.g > 0) {
//				color.g -= 1;
//			}
//			color.b += temp.b;
//			if (color.b > 0) {
//				color.b -= 1;
//			}
//			color = new Color(1f, 1f, 1f, 1f).sub(leftPlayer.getColor().cpy().mul(0.5f).add(rightPlayer.getColor().cpy().mul(0.5f)));
//			color = leftPlayer.getColor().cpy().mul(0.5f).add(rightPlayer.getColor().cpy().mul(0.5f));
//			color.add(leftPlayer.getColor().r * 0.15f, 0.15f, 0.15f, 0f);
			backgroundColor.lerp(leftPlayer.getColor().cpy().mul(0.5f).add(rightPlayer.getColor().cpy().mul(0.5f)), 0.05f);
		}
		
		if (winningPlayer != null) {
			float lowerColorBound = 0.0f;
			Color color = winningPlayer.getColor().cpy().mul(strength, strength, strength, 1f).add(winningPlayer.getColor().cpy().mul(0.25f));
			if (color.r < lowerColorBound) {
				color.r = lowerColorBound;
			}
			if (color.g < lowerColorBound) {
				color.g = lowerColorBound;
			}
			if (color.b < lowerColorBound) {
				color.b = lowerColorBound;
			}
			if (color.a < lowerColorBound) {
				color.a = lowerColorBound;
			}
			backgroundColor.lerp(color, 0.05f);
		}
		
		super.renderClearScreen(delta);
	}
	
	@Override
	public void show() {
		Gdx.input.setCursorCatched(!Settings.getDebugMode());
		getInputMultiplexer().clear();
		getInputMultiplexer().addProcessor(new InputAdapter() {

			@Override
			public boolean keyDown(int keycode) {
				switch (keycode) {
				case Keys.ESCAPE:
				case Keys.BACK:
					back();
					break;
				}
				return false;
			}

		});
		getInputMultiplexer().addProcessor(new DebugInput(this, stage));
		super.show();
	}

	private void back() {
		game.setScreen(new PauseScreen(game, this));
	}
	
	@Override
	public void pause() {
		super.pause();
//		MusicHandler.pause();
		back();
	}

//	@Override
//	public void resume() {
//		super.resume();
//		MusicHandler.play();
//	}

	@Override
	public void dispose() {
		super.dispose();
		Groups.playerGroup.clear();
		Groups.ballGroup.clear();
		Groups.traceGroup.clear();
		Groups.paddleGroup.clear();
		Groups.blockGroup.clear();
		Groups.shadowGroup.clear();
		Groups.residueGroup.clear();
		Groups.powerupGroup.clear();
		Groups.particleGroup.clear();
		MusicHandler.stop();
		Assets.unloadGameAssets();
		Gdx.input.setCursorCatched(false);
	}

}
