package com.pigmassacre.mbreak.objects.powerups;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Shadow;
import com.pigmassacre.mbreak.objects.effects.Flash;

public class Powerup extends GameActor {

	private float startTime;
	private float maxZ, minZ;
	
	public Powerup(float x, float y) {
		super();
		setDepth(1 * Settings.GAME_SCALE);
		setZ(4 * Settings.GAME_SCALE);
		setX(x);
		setY(y);
		maxZ = 2 * Settings.GAME_SCALE;
		minZ = 1;

		startTime = TimeUtils.millis() * 0.00001f;
		
		Groups.powerupGroup.addActor(this);
		
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);

		new Flash(this, 0.33f, true);
	}

	public void hit(GameActor actor) {
		onHit(actor);
		destroy();
	}
	
	protected void onHit(GameActor actor) {
		
	}

	public interface EffectCommand {

		public void execute(GameActor actor);
		
	}
	
	protected void applyEffect(GameActor touchingActor, EffectCommand command) {
		if (touchingActor instanceof Ball) {
			Ball ball = (Ball) touchingActor;
			command.execute(ball);
		}
	}
	
	protected void applyEffectToAllBalls(GameActor touchingActor, EffectCommand command) {
		for (Actor ballActor : Groups.ballGroup.getChildren()) {
			if (ballActor instanceof Ball) {
				Ball ball = (Ball) ballActor;
				command.execute(ball);
			}
		}
		/*
		def share_effect(self, entity, timeout_class, effect_creation_function, *args):
			created_effect = None

			# Add the effect to all the balls. However, if the ball has timeout, make sure it cannot use that balls effect as
			# the effect to connect to the displayed powerup.
			for ball in groups.Groups.ball_group:
				has_timeout = False
				for effect in ball.effect_group:
					if effect.__class__ == timeout_class:
						# Create the effect for the ball.
						if args:
							an_effect = effect_creation_function(ball, args)
						else:
							an_effect = effect_creation_function(ball)
						an_effect.real_owner = entity.owner
						has_timeout = True
						break
				if not has_timeout:
					# Create the effect for the ball.
					if args:
						created_effect = effect_creation_function(ball, args)
					else:
						created_effect = effect_creation_function(ball)
					created_effect.real_owner = entity.owner
						
			if created_effect != None:
				# Add this effect to the owner of the ball.
				entity.owner.effect_group.add(created_effect)

				# Store a powerup of this type in entity owners powerup group, so we can display the powerups collected by a player.
				entity.owner.add_powerup(self.__class__, created_effect)
		 */
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		float newZ = ((MathUtils.sinDeg(startTime + stateTime * 250f) + 1) / 2) * maxZ + minZ;
		if (newZ < minZ) newZ = minZ;
		setZ(newZ);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(getImage(), getX(), getY() + Settings.getLevelYOffset() + getZ(), getWidth(), getHeight() + getDepth());
		super.draw(batch, parentAlpha);
	}
	
}
