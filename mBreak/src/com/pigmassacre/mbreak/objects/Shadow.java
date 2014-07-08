package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.pigmassacre.mbreak.Settings;

public class Shadow extends GameActor implements Poolable {

	public static final Pool<Shadow> shadowPool = new Pool<Shadow>() {

		protected Shadow newObject() {
			return new Shadow();
		};

	};
	
	private float offsetX, offsetY;
	
	public boolean linger;
	
	public Shadow() {
		alive = false;
	}
	
	public void init(GameActor parentActor, boolean linger) {
		this.parentActor = parentActor;
		image = parentActor.image;
		
		setColor(0f, 0f, 0f, 0.75f);
		
		offsetX = 0 * Settings.GAME_SCALE;
		offsetY = -2f * Settings.GAME_SCALE;
		
		this.linger = linger;
		
		alive = true;
		
		Groups.shadowGroup.addActor(this);
	}
	
	@Override
	public void reset() {
		if (linger) {
			Residue residue = Residue.residuePool.obtain();
			residue.init(this);
		}
		alive = false;
		parentActor = null;
		remove();
		clear();
	}
	
	@Override
	public float getX() {
		return parentActor.getX() + offsetX;
	}
	
	@Override
	public float getY() {
		return parentActor.getY() + offsetY;
	}
	
	@Override
	public float getWidth() {
		return parentActor.getWidth();
	}
	
	@Override
	public float getHeight() {
		return parentActor.getHeight();
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (parentActor.image != null){
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(parentActor.image, getX(), getY(), getWidth(), getHeight());
			batch.setColor(temp);
		}
	}

}
