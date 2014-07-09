package com.pigmassacre.mbreak.screens;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.DebugInput;
import com.pigmassacre.mbreak.objects.Block;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Paddle;
import com.pigmassacre.mbreak.objects.Player;
import com.pigmassacre.mbreak.objects.powerups.ElectricityPowerup;
import com.pigmassacre.mbreak.objects.powerups.FirePowerup;
import com.pigmassacre.mbreak.objects.powerups.FrostPowerup;
import com.pigmassacre.mbreak.objects.powerups.Powerup;
import com.pigmassacre.mbreak.objects.powerups.SpeedPowerup;

public class GameScreen extends AbstractScreen {

	Paddle leftPaddle, rightPaddle;
	Background background;
	Foreground foreground;

	public GameScreen(MBreak game) {
		super(game);

		background = new Background("planks");
		foreground = new Foreground("planks");

		Settings.LEVEL_WIDTH = background.getWidth();
		Settings.LEVEL_HEIGHT = background.getHeight();
		Settings.LEVEL_X = (Gdx.graphics.getWidth() - Settings.LEVEL_WIDTH) / 2;
		Settings.LEVEL_Y = (Gdx.graphics.getHeight() - Settings.LEVEL_HEIGHT) / 2;
		Settings.LEVEL_MAX_X = Settings.LEVEL_X + Settings.LEVEL_WIDTH;
		Settings.LEVEL_MAX_Y = Settings.LEVEL_Y + Settings.LEVEL_HEIGHT;

		Player leftPlayer = new Player("left");
		leftPlayer.setX(2.5f * Settings.GAME_SCALE);
		leftPlayer.setY(2.5f * Settings.GAME_SCALE);
		Groups.playerGroup.addActor(leftPlayer);
		leftPlayer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		leftPaddle = new Paddle(leftPlayer);
		leftPaddle.setX(Settings.LEVEL_X + leftPaddle.getWidth() * 4);
		leftPaddle.setY((Gdx.graphics.getHeight() - leftPaddle.getHeight()) / 2);

		leftPaddle.touchRectangle = new Rectangle(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		leftPaddle.keyUp = Keys.W;
		leftPaddle.keyDown = Keys.S;

		Block tempBlock = new Block(0, 0, new Player("temp"), new Color(Color.BLACK));
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < (int) Settings.LEVEL_HEIGHT / tempBlock.getHeight(); y++) {
				new Block(Settings.LEVEL_X + x * tempBlock.getWidth(), Settings.LEVEL_Y + (y * tempBlock.getHeight()), leftPlayer,
						leftPlayer.getColor());
			}
		}

		Player rightPlayer = new Player("right");
		rightPlayer.setX(Gdx.graphics.getWidth() - 5f * Settings.GAME_SCALE - 2.5f * Settings.GAME_SCALE);
		rightPlayer.setY(Gdx.graphics.getHeight() - 5f * Settings.GAME_SCALE - 2.5f * Settings.GAME_SCALE);
		Groups.playerGroup.addActor(rightPlayer);
		rightPlayer.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		rightPaddle = new Paddle(rightPlayer);
		rightPaddle.setX(Settings.LEVEL_MAX_X - rightPaddle.getWidth() * 5);
		rightPaddle.setY((Gdx.graphics.getHeight() - rightPaddle.getHeight()) / 2);

		rightPaddle.touchRectangle = new Rectangle(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight());
		rightPaddle.keyUp = Keys.UP;
		rightPaddle.keyDown = Keys.DOWN;

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < (int) Settings.LEVEL_HEIGHT / tempBlock.getHeight(); y++) {
				new Block(Settings.LEVEL_MAX_X - ((x + 1) * tempBlock.getWidth()), Settings.LEVEL_Y + (y * tempBlock.getHeight()),
						rightPlayer, rightPlayer.getColor());
			}
		}

		stage.addActor(background);
		stage.addActor(Groups.playerGroup);
		stage.addActor(Groups.residueGroup);
		stage.addActor(Groups.shadowGroup);
		stage.addActor(Groups.traceGroup);
		stage.addActor(Groups.blockGroup);
		stage.addActor(Groups.powerupGroup);
		stage.addActor(Groups.ballGroup);
		stage.addActor(Groups.paddleGroup);
		stage.addActor(Groups.particleGroup);
		stage.addActor(foreground);
	}

	@Override
	public void show() {
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
		getInputMultiplexer().addProcessor(new DebugInput(stage));
		super.show();
	}

	private void back() {
		game.setScreen(new PrepareMenuScreen(game));
	}

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
	}

}
