package com.pigmassacre.mbreak.screens;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;

public class Level extends Actor {

	private static Level currentLevel;
	private Background background;
	private Foreground foreground;
	
	private TextureRegion backgroundImage, horizontalWallTop;
	private TextureRegion verticalWallLeft, verticalWallRight, horizontalWallBottom;
	private TextureRegion topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner;

	public static Level getCurrentLevel() {
		return currentLevel;
	}
	
	public static void setCurrentLevel(String id) {
		currentLevel = new Level(id);
	}
	
	private Level(String id) {
		backgroundImage = Assets.getTextureRegion(id + "/floor");
		
		verticalWallLeft = Assets.getTextureRegion(id + "/wall_vertical_left");
		verticalWallRight = Assets.getTextureRegion(id + "/wall_vertical_right");
		horizontalWallTop = Assets.getTextureRegion(id + "/wall_horizontal_top");
		horizontalWallBottom = Assets.getTextureRegion(id + "/wall_horizontal_bottom");
		topLeftCorner = Assets.getTextureRegion(id + "/corner_top_left");
		bottomLeftCorner = Assets.getTextureRegion(id + "/corner_bottom_left");
		topRightCorner = Assets.getTextureRegion(id + "/corner_top_right");
		bottomRightCorner = Assets.getTextureRegion(id + "/corner_bottom_right");
		
		setX(Settings.LEVEL_X - (Settings.LEVEL_WIDTH - backgroundImage.getRegionWidth() * Settings.GAME_SCALE));
		setY(Settings.LEVEL_Y + Settings.getLevelYOffset() - (Settings.LEVEL_HEIGHT - backgroundImage.getRegionHeight() * Settings.GAME_SCALE));
		setWidth(Settings.LEVEL_WIDTH);
		setHeight(Settings.LEVEL_HEIGHT);
		
		background = new Background();
		foreground = new Foreground();
	}

	public Background getBackground() {
		return background;
	}
	
	public Foreground getForeground() {
		return foreground;
	}
	
	private class Background extends Actor {
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(horizontalWallTop, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), horizontalWallTop.getRegionWidth() * Settings.GAME_SCALE, horizontalWallTop.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(backgroundImage, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY(), backgroundImage.getRegionWidth() * Settings.GAME_SCALE, backgroundImage.getRegionHeight() * Settings.GAME_SCALE);
		}
		
	}
	
	private class Foreground extends Actor {
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(topLeftCorner, Level.getCurrentLevel().getX() - topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(topRightCorner, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(verticalWallLeft, Level.getCurrentLevel().getX() - verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY(), verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, verticalWallLeft.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(verticalWallRight, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY(), verticalWallRight.getRegionWidth() * Settings.GAME_SCALE, verticalWallRight.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(bottomLeftCorner, Level.getCurrentLevel().getX() - bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(bottomRightCorner, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(horizontalWallBottom, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, horizontalWallBottom.getRegionWidth() * Settings.GAME_SCALE, horizontalWallBottom.getRegionHeight() * Settings.GAME_SCALE);
		}
		
	}
	
}
