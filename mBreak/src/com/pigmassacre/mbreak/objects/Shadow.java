package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Shadow extends GameActor {

	private GameActor parent;
	
	private float offsetX, offsetY;
	
	private boolean linger;
	private float lingerTime;
	
	private float alphaStep = (float) (0.19 * Settings.GAME_SCALE);
	
	public Shadow(GameActor parent, boolean linger) {
		this.parent = parent;
		
		setColor(0f, 0f, 0f, 0.5f);
		
		offsetX = 1 * Settings.GAME_SCALE;
		offsetY = -2 * Settings.GAME_SCALE;
		
		this.linger = linger;
		lingerTime = 0.025f * Settings.GAME_FPS;
		
		Groups.shadowGroup.addActor(this);
	}
	
	@Override
	public float getX() {
		return super.getX() + parent.getX() + offsetX;
	}
	
	@Override
	public float getY() {
		return super.getY() + parent.getY() + offsetY;
	}
	
	@Override
	public float getWidth() {
		return super.getWidth() + parent.getWidth();
	}
	
	@Override
	public float getHeight() {
		return super.getHeight() + parent.getHeight();
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
		if (this.parent.image != null) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(this.parent.image, getX(), getY(), getWidth(), getHeight());
			batch.setColor(temp);
		}
	}
	
}
