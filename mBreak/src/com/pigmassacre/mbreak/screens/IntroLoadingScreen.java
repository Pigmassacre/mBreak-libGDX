package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.TextItem;

public class IntroLoadingScreen extends AbstractScreen {

	TextItem loadingTextItem;
	
	public IntroLoadingScreen(MBreak game) {
		super(game);
		Assets.loadFonts();
		Assets.getAssetManager().finishLoading();
		Assets.loadMenuAssets();
		loadingTextItem = new TextItem("Now Loading");
		loadingTextItem.setColor(1f, 1f, 1f, 1f);
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
		loadingTextItem.setY((Gdx.graphics.getHeight() + loadingTextItem.getHeight()) / 2);
		stage.addActor(loadingTextItem);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Assets.getAssetManager().update()) {
			game.setScreen(new IntroScreen(game));
			dispose();
		}
		
		loadingTextItem.setString("Now Loading " + (int) (Assets.getAssetManager().getProgress() * 100) + "%");
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
	}
	
}
