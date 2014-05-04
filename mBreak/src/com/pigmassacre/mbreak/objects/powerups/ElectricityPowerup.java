package com.pigmassacre.mbreak.objects.powerups;

import com.pigmassacre.mbreak.Settings;

public class ElectricityPowerup extends Powerup {

	public ElectricityPowerup(float x, float y) {
		super(x, y);

		image = getAtlas().findRegion("electricity");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	}

}
