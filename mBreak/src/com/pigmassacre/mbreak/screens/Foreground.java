package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

/**
 * Takes care of all the background assets. The background assets will be picked
 * from the {@link TextureAtlas} that matches the id given.
 * 
 * @author pigmassacre
 *
 */
public class Foreground extends Actor {

	private TextureAtlas atlas;
	private TextureRegion verticalWall, horizontalWall;
	private TextureRegion topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner;
	
	public Foreground(String id) {
		TextureRegion background = getAtlas().findRegion(id + "/floor");
		verticalWall = getAtlas().findRegion(id + "/wall_vertical");
		horizontalWall = getAtlas().findRegion(id + "/wall_horizontal");
		topLeftCorner = getAtlas().findRegion(id + "/corner_top_left");
		bottomLeftCorner = getAtlas().findRegion(id + "/corner_top_right");
		topRightCorner = getAtlas().findRegion(id + "/corner_top_right");
		bottomRightCorner = getAtlas().findRegion(id + "/corner_top_left");
		
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
		batch.draw(horizontalWall, getX(), getY() - horizontalWall.getRegionHeight() * Settings.GAME_SCALE, horizontalWall.getRegionWidth() * Settings.GAME_SCALE, horizontalWall.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(horizontalWall, getX(), getY() + getHeight(), horizontalWall.getRegionWidth() * Settings.GAME_SCALE, horizontalWall.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWall, getX() - verticalWall.getRegionWidth() * Settings.GAME_SCALE, getY(), verticalWall.getRegionWidth() * Settings.GAME_SCALE, verticalWall.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(verticalWall, getX() + getWidth(), getY(), verticalWall.getRegionWidth() * Settings.GAME_SCALE, verticalWall.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(topLeftCorner, getX() - topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() + getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomLeftCorner, getX() - bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, getY() - bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(topRightCorner, getX() + getWidth(), getY() + getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
		batch.draw(bottomRightCorner, getX() + getWidth(), getY() - bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
	}
	
}
