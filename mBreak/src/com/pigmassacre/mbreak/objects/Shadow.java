package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.powerups.FirePowerup;

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
		offsetX = 0 * Settings.GAME_SCALE;
		offsetY = -2f * Settings.GAME_SCALE;
	}
	
	public void init(GameActor parentActor, boolean linger) {
		this.parentActor = parentActor;
		image = parentActor.image;
		
		setColor(0f, 0f, 0f, 0.75f);
		
		this.linger = linger;
		
		alive = true;
		
		Groups.shadowGroup.addActor(this);
	}
	
	@Override
	protected void setParent(Group parent) {
		super.setParent(parent);
	}
	
	@Override
	public void reset() {
		if (linger) {
			Residue residue = Residue.residuePool.obtain();
			residue.init(this);
		}
		alive = false;
		remove();
		clear();
//		parentActor = null;
//		image = null;
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
//		if (parentActor.image != null) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(parentActor.image, getX(), getY(), getWidth(), getHeight());
			batch.setColor(temp);
//		}
	}

}
