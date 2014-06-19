package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.pigmassacre.mbreak.Settings;

public class Shadow extends GameActor implements Poolable {

	public static final Pool<Shadow> shadowPool = new Pool<Shadow>() {

		protected Shadow newObject() {
			return new Shadow();
		};

	};
	
	private GameActor parentActor;
	
	private float offsetX, offsetY;
	
	public boolean linger;
	private float lingerTime;
	
	private float alphaStep = 0.19f * Settings.GAME_FPS;
	
	public boolean alive;
	
	public Shadow() {
		alive = true;
	}
	
	public void init(GameActor parent, boolean linger) {
		parentActor = parent;
		
		setColor(0f, 0f, 0f, 0.5f);
		
		offsetX = 1 * Settings.GAME_SCALE;
		offsetY = -2 * Settings.GAME_SCALE;
		
		this.linger = linger;
		lingerTime = 0.1f * Settings.GAME_FPS;
		
		Groups.shadowGroup.addActor(this);
	}
	
	@Override
	public void reset() {
		parentActor = null;
		alive = false;
		remove();
	}
	
	@Override
	public float getX() {
		return super.getX() /*+ parentActor.getX()*/ + offsetX;
	}
	
	@Override
	public float getY() {
		return super.getY() /*+ parentActor.getY()*/ + offsetY;
	}
	
	@Override
	public float getWidth() {
		return parentActor.getWidth();
	}
	
	@Override
	public float getHeight() {
		return parentActor.getHeight();
	}
	
	@Override
	public void act(float delta) {
		if (parentActor.alive) {
			setX(parentActor.getX());
			setY(parentActor.getY());
		}
		
		if (linger && !parentActor.alive) {
			lingerTime -= delta;
			if (lingerTime <= 0) {
				getColor().a -= alphaStep * delta;
				if (getColor().a < 0) {
					shadowPool.free(this);
				}
			}
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (parentActor.image != null) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(parentActor.image, getX(), getY(), getWidth(), getHeight());
			batch.setColor(temp);
		}
	}

}
