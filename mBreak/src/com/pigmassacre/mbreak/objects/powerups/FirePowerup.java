package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Settings;

public class FirePowerup extends Powerup {

	public FirePowerup(float x, float y) {
		super(x, y);

		image = getAtlas().findRegion("fire");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	}

}
