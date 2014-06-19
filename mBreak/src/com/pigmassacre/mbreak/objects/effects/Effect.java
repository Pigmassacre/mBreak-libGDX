package com.pigmassacre.mbreak.objects.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Pool;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Paddle;
import com.pigmassacre.mbreak.objects.Particle;
import com.pigmassacre.mbreak.objects.Player;

public class Effect extends GameActor {

	protected GameActor parentActor;
	protected Player realOwner;

	protected float duration;

	// private List<Powerup> connectedPowerups;

	public Effect(Effect effect) {
		this(effect.parentActor, effect.duration);
	}

	public Effect(GameActor parent, float duration) {
		this.parentActor = parent;
		this.owner = this.parentActor.owner;
		this.realOwner = this.parentActor.owner;
		this.duration = duration;

		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

		// connectedPowerups = new ArrayList<Powerup>();

		this.parentActor.effectGroup.addActor(this);
		// Groups.effectGroup.addActor(this);
	}

	@Override
	public void act(float delta) {
		setX(parentActor.getX() + ((parentActor.getWidth() - getWidth()) / 2));
		setY(parentActor.getY() + ((parentActor.getHeight() - getHeight()) / 2));

		stateTime += delta;
		if (stateTime >= duration) {
			destroy();
		}

		// System.out.println(particlePool.getFree());
	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (image != null) {
			batch.draw(image, getX(), getY(), getWidth(), getHeight());
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		// for (Powerup powerup : connectedPowerups)
		// powerup.destroy(false);
		this.parentActor.effectGroup.removeActor(this);
//		Groups.effectGroup.removeActor(this);
		onDestroy();
	}

	public void onDestroy() {

	}

	@Override
	public void onHitPaddle(Paddle paddle) {
		owner = paddle.owner;
	}

}
