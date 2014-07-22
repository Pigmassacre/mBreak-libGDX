package com.pigmassacre.mbreak.screens;

import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.objects.Assets;

public class GameLoadingScreen extends AbstractScreen {

	public GameLoadingScreen(MBreak game) {
		super(game);
		Assets.loadGameAssets();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Assets.getAssetManager().update()) {
			game.setScreen(new GameScreen(game));
		}
	}

}
