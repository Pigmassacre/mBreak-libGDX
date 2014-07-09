package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Shadow;

/**
 * Takes care of all the background assets. The background assets will be picked
 * from the {@link TextureAtlas} that matches the id given.
 * 
 * @author pigmassacre
 *
 */
public class Foreground extends Actor {

	private TextureAtlas atlas;
	private TextureRegion verticalWallLeft, verticalWallRight, horizontalWallTop, horizontalWallBottom;
	private TextureRegion topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner;
	
	public Foreground(String id) {
		TextureRegion background = getAtlas().findRegion(id + "/floor");
		verticalWallLeft = getAtlas().findRegion(id + "/wall_vertical_left");
		verticalWallRight = getAtlas().findRegion(id + "/wall_vertical_right");
		horizontalWallTop = getAtlas().findRegion(id + "/wall_horizontal_top");
		horizontalWallBottom = getAtlas().findRegion(id + "/wall_horizontal_bottom");
		topLeftCorner = getAtlas().findRegion(id + "/corner_top_left");
		bottomLeftCorner = getAtlas().findRegion(id + "/corner_bottom_left");
		topRightCorner = getAtlas().findRegion(id + "/corner_top_right");
		bottomRightCorner = getAtlas().findRegion(id + "/corner_bottom_right");
		
		setX((Gdx.graphics.getWidth() - background.getRegionWidth() * Settings.GAME_SCALE) / 2);
		setY((Gdx.graphics.getHeight() - background.getRegionHeight() * Settings.GAME_SCALE) / 2);
		setWidth(background.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(background.getRegionHeight() * Settings.GAME_SCALE);
	}
	
	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(horizontalWallBottom, getX(), getY() - horizontalWallBottom.getRegionHeight() * Settings.GAME_SCALE, horizontalWallBottom.getRegionWidth() * Settings.GAME_SCALE, horizontalWallBottom.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(horizontalWallTop, getX(), getY() + getHeight(), horizontalWallTop.getRegionWidth() * Settings.GAME_SCALE, horizontalWallTop.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWallLeft, getX() - verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, getY(), verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, verticalWallLeft.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWallRight, getX() + getWidth(), getY(), verticalWallRight.getRegionWidth() * Settings.GAME_SCALE, verticalWallRight.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(topLeftCorner, getX() - topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() + getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomLeftCorner, getX() - bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() - bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(topRightCorner, getX() + getWidth(), getY() + getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomRightCorner, getX() + getWidth(), getY() - bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
	}
	
}
