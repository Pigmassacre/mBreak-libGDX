package com.pigmassacre.mbreak;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;

public class SplashScreen extends AbstractScreen {

	float stateTime, endTime;
	
	public SplashScreen(MBreak game) {
		super(game);
		stateTime = 0;
		endTime = 1.5f;
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		stateTime += delta;
		if (stateTime >= endTime)
			game.setScreen(new IntroScreen(game));
	}
	
}
