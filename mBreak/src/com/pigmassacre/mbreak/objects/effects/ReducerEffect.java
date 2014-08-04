package com.pigmassacre.mbreak.objects.effects;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Player;

public class ReducerEffect extends Effect {

	private Player effectOwner;
//	private float heightChange;
	
	public ReducerEffect(GameActor parentActor, float duration) {
		super(parentActor, duration);
		effectOwner = owner;
//		float oldHeight = effectOwner.paddle.getHeight();
		effectOwner.paddle.addHeight(-6 * Settings.GAME_SCALE);
//		heightChange = oldHeight - effectOwner.paddle.getHeight();
	}
	
	@Override
	public void destroy() {
		effectOwner.paddle.addHeight(6 * Settings.GAME_SCALE);
		super.destroy();
	}

}
