package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.effects.SpeedEffect;

public class SpeedPowerup extends Powerup {

	private float duration = 5f;
	
	public SpeedPowerup(float x, float y) {
		super(x, y);

		image = getAtlas().findRegion("doublespeed");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	}

	@Override
	protected void onHit(GameActor actor) {
		applyEffectToAllBalls(actor, new Powerup.EffectCommand() {

			@Override
			public void execute(GameActor actor) {
				new SpeedEffect(actor, duration);				
			}
			
		});
	}
	
}
