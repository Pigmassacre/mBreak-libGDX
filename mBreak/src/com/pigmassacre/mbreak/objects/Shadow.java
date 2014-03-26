package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Shadow extends GameActor {

	private Actor parent;
	
	private float offsetX, offsetY;
	
	private boolean linger;
	private float lingerTime;
	
	private float alphaStep = (float) (0.19 * Settings.GAME_SCALE);
	
	public Shadow(Actor parent, TextureRegion image, boolean linger) {
		this.parent = parent;
		
		setColor(0f, 0f, 0f, 0.5f);
		
		this.image = new TextureRegion(image);
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	
		offsetX = 1 * Settings.GAME_SCALE;
		offsetY = -2 * Settings.GAME_SCALE;
		
		this.linger = linger;
		lingerTime = 0.025f * Settings.GAME_FPS;
		
		Groups.shadowGroup.addActor(this);
	}
	
	@Override
	public void act(float delta) {
		if (linger) {
			lingerTime -= delta;
			if (lingerTime <= 0) {
				getColor().a -= alphaStep * delta;
				if (getColor().a < 0) {
					remove();
					clear();
				}
				getColor().clamp();
			}
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
		batch.draw(image, parent.getX() + offsetX, parent.getY() + offsetY, getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}
