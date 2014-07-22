package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Assets;

/**
 * Takes care of all the background assets. The background assets will be picked
 * from the {@link TextureAtlas} that matches the id given.
 * 
 * @author pigmassacre
 *
 */
public class Background extends Actor {

	private TextureRegion background, horizontalWallTop;
	
	public Background(String id) {
		horizontalWallTop = Assets.getTextureRegion(id + "/wall_horizontal_top");
		background = Assets.getTextureRegion(id + "/floor");
		
		setX((Gdx.graphics.getWidth() - background.getRegionWidth() * Settings.GAME_SCALE) / 2);
		setY((Gdx.graphics.getHeight() - background.getRegionHeight() * Settings.GAME_SCALE) / 2 - 3 * Settings.GAME_SCALE);
		setWidth(background.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(background.getRegionHeight() * Settings.GAME_SCALE);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(horizontalWallTop, getX(), getY() + getHeight(), horizontalWallTop.getRegionWidth() * Settings.GAME_SCALE, horizontalWallTop.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(background, getX(), getY(), getWidth(), getHeight());
	}
	
}
