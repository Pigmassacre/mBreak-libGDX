package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Trace extends Actor {

	private Actor parent;
	private TextureRegion image;
	
	private float alphaStep = 0.06f * Settings.GAME_FPS;
	
	private Shadow shadow;
	private Color shadowBlendColor = new Color(0.4f, 0.4f, 0.4f, 1.0f);
	
	public Trace(Actor parent, TextureRegion image) {
		this.parent = parent;
		setColor(new Color(parent.getColor()));
		this.image = image;
		
		setX(parent.getX());
		setY(parent.getY());
		setWidth(parent.getWidth());
		setHeight(parent.getHeight());
		
		shadow = new Shadow(this, this.image, false);
		shadow.getColor().mul(shadowBlendColor);
		
		Groups.traceGroup.addActor(this);
	}

	@Override
	public void act(float delta) {
		if (alphaStep > 0) {
			getColor().a -= alphaStep * delta;
			shadow.getColor().a -= alphaStep / 2 * delta;
			if (getColor().a - (alphaStep * delta) < 0) {
				remove();
				clear();
				shadow.remove();
				shadow.clear();
			}
			getColor().clamp();
			shadow.getColor().clamp();
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}