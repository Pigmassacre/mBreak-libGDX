package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.Assets;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.effects.SpeedEffect;

public class MultiballPowerup extends Powerup {

	private float duration = 5f;
	
	public MultiballPowerup(float x, float y) {
		super(x, y);

		image = Assets.getTextureRegion("multiball");
		
		setDepth(1 * Settings.GAME_SCALE);
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE - getDepth());
	}

	@Override
	protected void onHit(GameActor actor) {
//		applyEffectToAllBalls(actor, new Powerup.EffectCommand() {
//
//			@Override
//			public void execute(GameActor actor) {
//				new SpeedEffect(actor, duration);				
//			}
//			
//		});
	}
	
}
