package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.effects.EnlargerEffect;

public class EnlargerPowerup extends Powerup {

	private float duration = 5f;
	
	public EnlargerPowerup(float x, float y) {
		super(x, y);

		setImage(Assets.getTextureRegion("enlarger"));
		
		setDepth(1 * Settings.GAME_SCALE);
		setWidth(getImage().getRegionWidth() * Settings.GAME_SCALE);
		setHeight(getImage().getRegionHeight() * Settings.GAME_SCALE - getDepth());
	}

	@Override
	protected void onHit(GameActor actor) {
		new EnlargerEffect(actor, duration);
	}
	
}
