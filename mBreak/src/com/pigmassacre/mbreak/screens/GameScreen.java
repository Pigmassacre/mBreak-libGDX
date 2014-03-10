package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.DebugInput;
import com.pigmassacre.mbreak.gui.Traversal;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Paddle;
import com.pigmassacre.mbreak.objects.Player;

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
		leftPlayer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		leftPaddle = new Paddle(leftPlayer);
		leftPaddle.setX(Settings.LEVEL_X + leftPaddle.getWidth() * 4);
		leftPaddle.setY((Gdx.graphics.getHeight() - leftPaddle.getHeight()) / 2);
		
		leftPaddle.upRectangle = new Rectangle(0, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		leftPaddle.downRectangle = new Rectangle(0, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		leftPaddle.keyUp = Keys.W;
		leftPaddle.keyDown = Keys.S;
		
		Player rightPlayer = new Player("right");
		rightPlayer.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		rightPaddle = new Paddle(leftPlayer);
		rightPaddle.setX(Settings.LEVEL_MAX_X - rightPaddle.getWidth() * 5);
		rightPaddle.setY((Gdx.graphics.getHeight() - rightPaddle.getHeight()) / 2);
		
		rightPaddle.upRectangle = new Rectangle(Gdx.graphics.getWidth() / 2, 0, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		rightPaddle.downRectangle = new Rectangle(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		rightPaddle.keyUp = Keys.UP;
		rightPaddle.keyDown = Keys.DOWN;
		
		stage.addActor(background);
		stage.addActor(Groups.shadowGroup);
		stage.addActor(Groups.traceGroup);
		stage.addActor(Groups.ballGroup);
		stage.addActor(Groups.paddleGroup);
		stage.addActor(foreground);
	}
	
	@Override
	public void show() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
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
		Groups.ballGroup.clear();
		Groups.traceGroup.clear();
		Groups.paddleGroup.clear();
	}
	
}
