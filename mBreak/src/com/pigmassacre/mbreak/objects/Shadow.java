package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Group;
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
		setZ(0);
		offsetX = 0f * Settings.GAME_SCALE;
		offsetY = -0f * Settings.GAME_SCALE;
	}
	
	public void init(GameActor parentActor, boolean linger) {
		this.parentActor = parentActor;
		image = parentActor.image;
		
		setColor(0f, 0f, 0f, 0.5f);
		setZ(parentActor.getZ());
		setDepth(0);
		
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
	}
	
	@Override
	public float getX() {
		return parentActor.getX() + offsetX * parentActor.getZ();
	}
	
	@Override
	public float getY() {
		return parentActor.getY();
	}
	
	@Override
	public float getWidth() {
		return parentActor.getWidth();
	}
	
	@Override
	public float getHeight() {
		return parentActor.getHeight() - parentActor.getDepth();
	}
	
//	@Override
//	public void act(float delta) {
//		super.act(delta);
//		stateTime += delta;
//		offsetX = MathUtils.sin(stateTime) * 0.5f * Settings.GAME_SCALE;
//		offsetY = MathUtils.sin(stateTime * 2) * -0.5f * Settings.GAME_SCALE;
//	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
//		float zDropOff = 0f;
		//  + (parentActor.getZ() / zDropOff) / 2
		batch.draw(parentActor.image, getX(), getY() + Settings.getLevelYOffset(), getWidth(), getHeight() + parentActor.getDepth());
		batch.setColor(temp);
	}

}
