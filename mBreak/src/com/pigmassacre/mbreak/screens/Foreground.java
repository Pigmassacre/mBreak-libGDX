package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Assets;
import com.pigmassacre.mbreak.objects.Shadow;

/**
 * Takes care of all the background assets. The background assets will be picked
 * from the {@link TextureAtlas} that matches the id given.
 * 
 * @author pigmassacre
 *
 */
public class Foreground extends Actor {

	private TextureRegion verticalWallLeft, verticalWallRight, horizontalWallBottom;
	private TextureRegion topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner;
	
	public Foreground(String id) {
		TextureRegion background = Assets.getTextureRegion(id + "/floor");
		verticalWallLeft = Assets.getTextureRegion(id + "/wall_vertical_left");
		verticalWallRight = Assets.getTextureRegion(id + "/wall_vertical_right");
		horizontalWallBottom = Assets.getTextureRegion(id + "/wall_horizontal_bottom");
		topLeftCorner = Assets.getTextureRegion(id + "/corner_top_left");
		bottomLeftCorner = Assets.getTextureRegion(id + "/corner_bottom_left");
		topRightCorner = Assets.getTextureRegion(id + "/corner_top_right");
		bottomRightCorner = Assets.getTextureRegion(id + "/corner_bottom_right");
		
		setX((Gdx.graphics.getWidth() - background.getRegionWidth() * Settings.GAME_SCALE) / 2);
		setY((Gdx.graphics.getHeight() - background.getRegionHeight() * Settings.GAME_SCALE) / 2);
		setWidth(background.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(background.getRegionHeight() * Settings.GAME_SCALE);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(topLeftCorner, getX() - topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() + getHeight() + Settings.getLevelYOffset(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(topRightCorner, getX() + getWidth(), getY() + getHeight() + Settings.getLevelYOffset(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWallLeft, getX() - verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, getY() - Settings.getLevelYOffset(), verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, verticalWallLeft.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWallRight, getX() + getWidth(), getY() - Settings.getLevelYOffset(), verticalWallRight.getRegionWidth() * Settings.GAME_SCALE, verticalWallRight.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomLeftCorner, getX() - bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() + Settings.getLevelYOffset() - 1 * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomRightCorner, getX() + getWidth(), getY() + Settings.getLevelYOffset() - Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(horizontalWallBottom, getX(), getY() + Settings.getLevelYOffset() - 1 * Settings.GAME_SCALE, horizontalWallBottom.getRegionWidth() * Settings.GAME_SCALE, horizontalWallBottom.getRegionHeight() * Settings.GAME_SCALE);
	}
	
}
