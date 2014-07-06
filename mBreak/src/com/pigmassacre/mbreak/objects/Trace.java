package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pigmassacre.mbreak.Settings;

public class Trace extends GameActor {

	private float alphaStep = 0.06f * Settings.GAME_FPS;
	
	private Shadow shadow;
	private Color shadowBlendColor = new Color(0.4f, 0.4f, 0.4f, 1.0f);
	
	public Trace(GameActor parentActor, TextureRegion image) {
		this.parentActor = parentActor;
		setColor(new Color(parentActor.getColor()));
		this.image = image;
		
		setX(parentActor.getX());
		setY(parentActor.getY());
		setWidth(parentActor.getWidth());
		setHeight(parentActor.getHeight());
		
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);
		shadow.getColor().mul(shadowBlendColor);
		
		Groups.traceGroup.addActor(this);
	}

	public void destroy() {
		remove();
		clear();
		shadow.remove();
		shadow.clear();
	}
	
	@Override
	public void act(float delta) {
		if (alphaStep > 0) {
			getColor().a -= alphaStep * delta;
			shadow.getColor().a -= alphaStep / 2 * delta;
			if (getColor().a - (alphaStep * delta) < 0) {
				destroy();
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
