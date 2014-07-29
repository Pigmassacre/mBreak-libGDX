package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.pigmassacre.mbreak.MBreak;

public class PauseScreen extends AbstractScreen {
	
	AbstractScreen pausedScreen;
	
	public PauseScreen(MBreak game, AbstractScreen pausedScreen) {
		super(game);
		Gdx.input.setCursorCatched(false);
		this.pausedScreen = pausedScreen;
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
	}
	
	@Override
	public void show() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
				case Keys.BACK:
					game.setScreen(pausedScreen);
					break;
				}
				return false;
			}
			
		});
		super.show();
	}
	
	@Override
	public void hide() {
		super.hide();
		dispose();
	}
	
}
