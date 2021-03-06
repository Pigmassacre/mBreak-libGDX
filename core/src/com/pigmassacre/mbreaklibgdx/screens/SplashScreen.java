package com.pigmassacre.mbreaklibgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.pigmassacre.mbreaklibgdx.MBreak;
import com.pigmassacre.mbreaklibgdx.gui.Splash;
import com.pigmassacre.mbreaklibgdx.gui.Splash.SplashCallback;

public class SplashScreen extends AbstractScreen {

	private Splash splash;
	
	public SplashScreen(MBreak game) {
		super(game);
		splash = new Splash();
		splash.setX(Gdx.graphics.getWidth() / 2 - splash.getWidth() / 2);
		splash.setY(Gdx.graphics.getHeight() / 2 - splash.getHeight() / 2);
		splash.setup(getTweenManager());
		splash.setCallback(new SplashCallback() {
			
			@Override
			public void execute(Splash splash) {
				startIntroScreen();
			}
			
		});
		stage.addActor(splash);
		backgroundColor = new Color(36 / 255f, 36 / 255f, 36 / 255f, 1f);
	}
	
	private void setupTimer() {
		Timer.instance().clear();
		Timer.instance().scheduleTask(new Task() {
			
			@Override
			public void run() {
				game.setScreen(new IntroScreen(game));
			}
			
		}, 0.1f);
	}
	
	private void startIntroScreen() {
		setupTimer();
	}

	@Override
	public void hide() {
		super.hide();
		dispose();
	}
	
	@Override
	protected void registerInputProcessors() {
		getInputMultiplexer().addProcessor(new IntroInputProcessor());
	}

	private class IntroInputProcessor extends InputAdapter {

		@Override
		public boolean keyDown(int keycode) {
			switch(keycode) {
				case Keys.ENTER:
				case Keys.ESCAPE:
					game.setScreen(new IntroScreen(game));
					break;
			}
			return false;
		}

		@Override
		public boolean touchDown(int screenX, int screenY, int pointer, int button) {
			game.setScreen(new IntroScreen(game));
			return false;
		}

	}

}
