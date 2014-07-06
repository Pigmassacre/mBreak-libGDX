package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;

public class Residue extends GameActor implements Poolable {

	public static final Pool<Residue> residuePool = new Pool<Residue>() {

		protected Residue newObject() {
			return new Residue();
		};

	};
	
	private float fadeoutTime = 1f;
	
	private float lingerTime;
	
	public Residue() {
		alive = false;
	}
	
	public void init(GameActor parentActor) {
		if (parentActor.image != null) {
			image = new TextureRegion(parentActor.image);
			setX(parentActor.getX());
			setY(parentActor.getY());
			setWidth(parentActor.getWidth());
			setHeight(parentActor.getHeight());
			setColor(parentActor.getColor());
			lingerTime = 1f;
			Groups.residueGroup.addActor(this);
		} else {
			residuePool.free(this);
		}
	}
	
	@Override
	public void reset() {
		alive = false;
		remove();
		clear();
	}
	
	@Override
	public void act(float delta) {
		lingerTime -= delta;
		if (lingerTime <= 0) {
			getColor().a -= fadeoutTime * delta;
			if (getColor().a < 0) {
				getColor().clamp();
				residuePool.free(this);
			}
			getColor().clamp();
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
