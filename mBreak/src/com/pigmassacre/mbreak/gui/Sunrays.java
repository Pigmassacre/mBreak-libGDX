package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.pigmassacre.mbreak.Settings;

public class Sunrays extends Widget {
	
	private TextureAtlas atlas;
	private TextureRegion image;
	
	private float rotation;
	
	private Widget target;
	private float offsetX, offsetY;
	
	public Sunrays() {
		image = getAtlas().findRegion("cheatysunrays");
		setColor(1f, 1f, 1f, 0.25f);
//		setColor(0f, 0f, 0f, 1f);
	}
	
	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
	}
	
	public void attachTo(Widget widget, float offsetX, float offsetY) {
		target = widget;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	@Override
	public float getWidth() {
		return image.getRegionWidth() * Settings.GAME_SCALE;
	}
	
	@Override
	public float getHeight() {
		return image.getRegionHeight() * Settings.GAME_SCALE;
	}
	
	public void act(float delta) {
		setX(target.getX() + target.getWidth() / 2 - getWidth() / 2 + offsetX);
		setY(target.getY() + target.getHeight() / 2 - getHeight() / 2 + offsetY);
		
		rotation += delta * 6;
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
		batch.draw(image, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), 1f, 1f, rotation);
		batch.setColor(temp);
	}
	
}
