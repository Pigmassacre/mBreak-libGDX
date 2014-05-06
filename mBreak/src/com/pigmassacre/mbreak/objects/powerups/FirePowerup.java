package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.effects.FireEffect;

public class FirePowerup extends Powerup {

	private float duration = 10f;
	
	public FirePowerup(float x, float y) {
		super(x, y);

		image = getAtlas().findRegion("fire");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	}

	@Override
	protected void onHit(GameActor actor) {
		actor.effectGroup.addActor(new FireEffect(actor, duration));
//		applyEffectToAllBalls(actor, new FireEffect(actor, duration));
	}
	
}
