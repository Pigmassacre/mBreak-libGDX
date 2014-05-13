package com.pigmassacre.mbreak.objects.effects;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Paddle;

public class FrostEffect extends Effect {

	private static final float PADDLE_FREEZE_DURATION = 2f;
	private static final float PADDLE_FREEZE_SPEED_REDUCTION = 2f * Settings.GAME_FPS * Settings.GAME_SCALE;
	
	public FrostEffect(GameActor parent, float duration) {
		super(parent, duration);
		if (parent instanceof Paddle) {
			((Paddle) parent).maxSpeed -= PADDLE_FREEZE_SPEED_REDUCTION;
		}
	}

	@Override
	public void onHitPaddle(Paddle paddle) {
		if (paddle.owner != owner) {
			new FrostEffect(paddle, PADDLE_FREEZE_DURATION);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		System.out.println("time left: " + stateTime);
	}
	
	@Override
	public void onDestroy() {
		if (parentActor instanceof Paddle) {
			((Paddle) parentActor).maxSpeed += PADDLE_FREEZE_SPEED_REDUCTION;
		}
	}
	
}
