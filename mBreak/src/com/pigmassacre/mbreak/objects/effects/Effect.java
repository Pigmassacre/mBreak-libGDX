package com.pigmassacre.mbreak.objects.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;

public class Effect extends GameActor {

	protected float duration;

//	public List<Powerup> connectedPowerups;

	public Effect(Effect effect) {
		this(effect.parentActor, effect.duration);
	}

	public Effect(GameActor parentActor, float duration) {
		this.parentActor = parentActor;
		this.owner = this.parentActor.owner;
		this.duration = duration;

		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

		stateTime = 0f;
		
//		connectedPowerups = new ArrayList<Powerup>();

		this.parentActor.effectGroup.addActor(this);
	}

	@Override
	public void act(float delta) {
		setWidth(parentActor.getWidth());
		setHeight(parentActor.getHeight());
		setX(parentActor.getX() + ((parentActor.getWidth() - getWidth()) / 2));
		setY(parentActor.getY() + ((parentActor.getHeight() - getHeight()) / 2));
		setZ(parentActor.getZ());
		setDepth(parentActor.getDepth());

		stateTime += delta;
		if (stateTime >= duration) {
			destroy();
		}
		
		if (parentActor.owner == owner) {
			actParticles(delta);
		}
	}
	
	protected void actParticles(float delta) {
		
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (getImage() != null) {
			batch.draw(getImage(), getX(), getY() + Settings.getLevelYOffset() + getZ(), getWidth(), getHeight() + getDepth());
		}
	}

	@Override
	public void destroy() {
		super.destroy();
//		for (Powerup powerup : connectedPowerups) {
//			powerup.destroy();
//		}
//		this.parentActor.effectGroup.removeActor(this);
	}

}
