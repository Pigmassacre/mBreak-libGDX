package com.pigmassacre.mbreak.objects.effects;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Player;

public class EnlargerEffect extends Effect {

	private Player effectOwner;
//	private float heightChange;
	
	public EnlargerEffect(GameActor parentActor, float duration) {
		super(parentActor, duration);
		effectOwner = parentActor.owner;
//		float oldHeight = effectOwner.paddle.getHeight();
		effectOwner.paddle.addHeight(6 * Settings.GAME_SCALE);
//		heightChange = oldHeight - effectOwner.paddle.getHeight();
	}
	
	@Override
	public void destroy() {
		effectOwner.paddle.addHeight(-6 * Settings.GAME_SCALE);
		super.destroy();
	}

}
