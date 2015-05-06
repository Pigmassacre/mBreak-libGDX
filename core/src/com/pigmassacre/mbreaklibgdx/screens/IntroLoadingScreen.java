package com.pigmassacre.mbreaklibgdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.pigmassacre.mbreaklibgdx.Assets;
import com.pigmassacre.mbreaklibgdx.MBreak;
import com.pigmassacre.mbreaklibgdx.gui.TextItem;

public class IntroLoadingScreen extends AbstractScreen {

	TextItem loadingTextItem;
	
	public IntroLoadingScreen(MBreak game) {
		super(game);
		Assets.loadAssetManager();
		Assets.loadFonts();
		Assets.getAssetManager().finishLoading();
		Assets.loadMenuAssets();
		loadingTextItem = new TextItem("Now Loading");
		loadingTextItem.setColor(1f, 1f, 1f, 1f);
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
		loadingTextItem.setY((Gdx.graphics.getHeight() + loadingTextItem.getHeight()) / 2);
		stage.addActor(loadingTextItem);
		backgroundColor = new Color(36 / 255f, 36 / 255f, 36 / 255f, 1f);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
		if (Assets.getAssetManager().update()) {
			game.setScreen(new SplashScreen(game));
			dispose();
		}
		
		loadingTextItem.setText("Now Loading " + (int) (Assets.getAssetManager().getProgress() * 100) + "%");
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
	}
	
}
