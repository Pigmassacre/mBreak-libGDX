package com.pigmassacre.mbreak.screens;

import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.objects.Assets;

public class StartLoadingScreen extends AbstractScreen {

	public StartLoadingScreen(MBreak game) {
		super(game);
		Assets.loadMenuAssets();
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Assets.getAssetManager().update()) {
			game.setScreen(new SplashScreen(game));
		}
	}

}
