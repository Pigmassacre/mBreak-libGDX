package com.pigmassacre.mbreaklibgdx.objects.effects;

import com.pigmassacre.mbreaklibgdx.Settings;
import com.pigmassacre.mbreaklibgdx.objects.GameActor;
import com.pigmassacre.mbreaklibgdx.objects.Player;

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
