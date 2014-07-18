package com.pigmassacre.mbreak.objects.powerups;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Flash;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Shadow;
import com.pigmassacre.mbreak.objects.effects.Effect;

public class Powerup extends GameActor {

	private float startTime;
//	protected float offsetX, offsetY;
//	protected float maxOffsetX, maxOffsetY;
	private float maxZ;
	
	private float flashTickAmount;
	private float flashDuration;
	
	public boolean isDisplay;
	
	public Powerup(float x, float y) {
		setDepth(2);
		setX(x);
		setY(y);
		setZ(3);

		isDisplay = false;
		
		startTime = TimeUtils.millis() * 0.00001f;
		
		maxZ = 5;
//		offsetX = 0f;
//		offsetY = 0f;
//		maxOffsetX = 0f * Settings.GAME_SCALE;
//		maxOffsetY = 0f * Settings.GAME_SCALE;
		
		flashTickAmount = 18f * Settings.GAME_FPS;
		flashDuration = 1f;
		
		Groups.powerupGroup.addActor(this);
		
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);
		
		new Flash(this, flashDuration, flashTickAmount, true);
	}
	
	public Powerup(float x, float y, float width, float height) {
		this(x, y);
		setWidth(width);
		setWidth(height);
		rectangle.set(getX(), getY(), getWidth(), getHeight());
	}

	public void hit(GameActor actor) {
		onHit(actor);
		destroy();
	}
	
	protected void onHit(GameActor actor) {
		
	}
	
	protected void applyEffect(GameActor touchingActor, Effect effect) {
		
	}
	
	public interface EffectCommand {

		public void execute(GameActor actor);
		
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
		if (isDisplay) {
//			offsetX = offsetY = 0;
		} else {
			stateTime += delta;
			setZ(((MathUtils.sinDeg(startTime + stateTime * 250f) + 1) / 2) * maxZ);
//			offsetX = (float) Math.sin(startTime + stateTime * 0.0075) * maxOffsetX;
//			offsetY = (float) Math.sin(startTime + stateTime * 0.0075) * maxOffsetY;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
//		System.out.println(getZ());
		batch.draw(image, getX(), getY() - getDepth() + getZ(), getWidth(), getHeight() + getDepth());
	}
	
}
