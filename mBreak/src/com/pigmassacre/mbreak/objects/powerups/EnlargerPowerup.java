package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;

public class EnlargerPowerup extends Powerup {

//	private float duration = 5f;
	
	public EnlargerPowerup(float x, float y) {
		super(x, y);

		setImage(Assets.getTextureRegion("enlarger"));
		
		setDepth(1 * Settings.GAME_SCALE);
		setWidth(getImage().getRegionWidth() * Settings.GAME_SCALE);
		setHeight(getImage().getRegionHeight() * Settings.GAME_SCALE - getDepth());
	}

	@Override
	protected void onHit(GameActor actor) {
		actor.owner.paddle.setHeight(actor.owner.paddle.getHeight() + 6 * Settings.GAME_SCALE);
	}
	
}
