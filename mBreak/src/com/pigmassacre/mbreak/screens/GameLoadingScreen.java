package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.objects.Assets;

public class GameLoadingScreen extends AbstractScreen {

	TextItem loadingTextItem;
	
	public GameLoadingScreen(MBreak game) {
		super(game);
		Assets.loadGameAssets();
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
			game.setScreen(new GameScreen(game));
		}
		
		loadingTextItem.setString("Now Loading " + (int) (Assets.getAssetManager().getProgress() * 100) + "%");
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
	}

}
