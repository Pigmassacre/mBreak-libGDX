package com.pigmassacre.mbreak.gui;

import aurelienribon.tweenengine.TweenAccessor;

import com.pigmassacre.mbreak.Settings;

public class SettingsAccessor implements TweenAccessor<Settings> {
	
	public static final int LEVEL_OFFSET = 1;
	
	@Override
	public int getValues(Settings target, int tweenType, float[] returnValues) {
		switch (tweenType) {
	        case LEVEL_OFFSET: 
	        	returnValues[0] = Settings.getLevelYOffset(); 
	        	return 1;
        default: 
        	assert false; 
        	return -1;
		}
	}

	@Override
	public void setValues(Settings target, int tweenType, float[] newValues) {
		switch (tweenType) {
	        case LEVEL_OFFSET: 
	        	Settings.setLevelYOffset(newValues[0]); 
	        	break;
	        default: 
	        	assert false; 
	        	break;
		}
	}
	
}
