package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;

public class ImageItem extends RectItem {

	private TextureRegion image;
	
	public ImageItem(String atlasRegion) {
		super();
		
		image = Assets.getTextureRegion(atlasRegion);
	}
	
	protected float getImageWidth() {
		return image.getRegionWidth() * Settings.GAME_SCALE;
	}
	
	protected float getImageHeight() {
		return image.getRegionHeight() * Settings.GAME_SCALE;
	}
	
	public void drawBeforeDisabled(Batch batch, float parentAlpha) {
		batch.draw(image, getX() + (rectangle.getWidth() - getImageWidth()) / 2, 
						  getY() + (rectangle.getHeight() - getImageHeight()) / 2, 
						  getImageWidth(), 
						  getImageHeight());
	}
	
}
