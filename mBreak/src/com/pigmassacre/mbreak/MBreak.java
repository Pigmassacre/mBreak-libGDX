package com.pigmassacre.mbreak;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.pigmassacre.mbreak.screens.StartLoadingScreen;

public class MBreak extends Game {

	public static final String LOG = "mBreak";
	
	private FPSLogger fpsLogger;
	
	private final Settings settings;
	
	public MBreak() {
		settings = new Settings();
	}

	@Override
	public void create() {
		Gdx.app.log(MBreak.LOG, "Creating game on " + Gdx.app.getType());
		fpsLogger = new FPSLogger();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(MBreak.LOG, "Resizing game to: " + width + " x " + height);
		super.resize(width, height);
		
		Gdx.input.setCatchBackKey(true);
		if (getScreen() == null)
			setScreen(new StartLoadingScreen(this));
	}

	@Override
	public void render() {
		super.render();
		
		if (settings.getDebugMode())
			fpsLogger.log();
	}

	@Override
	public void pause() {
		super.pause();
		Gdx.app.log(MBreak.LOG, "Pausing game");
	}

	@Override
	public void resume() {
		super.resume();
		Gdx.app.log(MBreak.LOG, "Resuming game");
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(MBreak.LOG, "Disposing game");
	}

}
