package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.DebugInput;
import com.pigmassacre.mbreak.gui.Traversal;
import com.pigmassacre.mbreak.objects.Groups;

public class GameScreen extends AbstractScreen {

	public GameScreen(MBreak game) {
		super(game);
		stage.addActor(Groups.ballGroup);
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
	}
	
}
