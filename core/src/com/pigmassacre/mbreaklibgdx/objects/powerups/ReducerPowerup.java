package com.pigmassacre.mbreaklibgdx.objects.powerups;

import com.pigmassacre.mbreaklibgdx.Assets;
import com.pigmassacre.mbreaklibgdx.Settings;
import com.pigmassacre.mbreaklibgdx.objects.GameActor;
import com.pigmassacre.mbreaklibgdx.objects.effects.ReducerEffect;

public class ReducerPowerup extends Powerup {

	private float duration = 5f;
	
	public ReducerPowerup(float x, float y) {
		super(x, y);

		setImage(Assets.getTextureRegion("reducer"));
		
		setDepth(1 * Settings.GAME_SCALE);
		setWidth(getImage().getRegionWidth() * Settings.GAME_SCALE);
		setHeight(getImage().getRegionHeight() * Settings.GAME_SCALE - getDepth());
	}

	@Override
	protected void onHit(GameActor actor) {
		new ReducerEffect(actor, duration);
	}
	
}
