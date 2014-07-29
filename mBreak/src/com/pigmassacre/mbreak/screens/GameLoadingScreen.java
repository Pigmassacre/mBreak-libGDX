package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.Logo;
import com.pigmassacre.mbreak.gui.Sunrays;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.objects.Assets;

public class GameLoadingScreen extends AbstractScreen {

	Logo logo;
	Sunrays sunrays;
	
	TextItem loadingTextItem;
	Color leftColor, rightColor;
	
	public GameLoadingScreen(MBreak game, Logo givenLogo, Sunrays givenSunrays, Color leftColor, Color rightColor) {
		super(game);
		
		Gdx.input.setCursorCatched(!Settings.getDebugMode());
		
		if (givenSunrays == null) {
			sunrays = new Sunrays();
		} else {
			sunrays = givenSunrays;
		}
		stage.addActor(sunrays);
		
		if (givenLogo == null) {
			logo = new Logo();
			logo.setX((Gdx.graphics.getWidth() - logo.getWidth()) / 2);
			logo.setY(Gdx.graphics.getHeight() / 2 - logo.getHeight() / 2 + Settings.getLevelYOffset() + logo.getHeight() / 6);
		} else {
			logo = givenLogo;
		}
		sunrays.attachTo(logo, 0, -logo.getHeight() / 6);
		stage.addActor(logo);
		
		this.leftColor = leftColor;
		this.rightColor = rightColor;
		
		loadingTextItem = new TextItem("Now Loading");
		loadingTextItem.setColor(1f, 1f, 1f, 1f);
		loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
		loadingTextItem.setY(logo.getY() - loadingTextItem.getHeight());
		stage.addActor(loadingTextItem);
		
		Assets.loadGameAssets();
	}
	
	private boolean finishedLoading = false, finishedTweening = false;
	
	@Override
	public void render(float delta) {
		super.render(delta);
		
//		MusicHandler.setVolume(MusicHandler.getVolume() + (0.5f - MusicHandler.getVolume()) * delta);
		
		if (Assets.getAssetManager().update() && !finishedLoading) {
			sunrays.removeTarget();
			Timeline.createSequence()
				.push(Tween.to(logo, ActorAccessor.POSITION_Y, 0.5f).target(Gdx.graphics.getHeight() + logo.getHeight()).ease(TweenEquations.easeInExpo))
//				.setCallback(new TweenCallback() {
//					
//					@Override
//					public void onEvent(int type, BaseTween<?> source) {
//						sunrays.offsetY = Settings.getLevelYOffset();
//					}
//					
//				})
//				.push(Tween.to(sunrays, ActorAccessor.POSITION_Y, 0.5f).target((Gdx.graphics.getHeight() - sunrays.getHeight()) / 2 - sunrays.offsetY + Settings.getLevelYOffset())
//						.ease(TweenEquations.easeOutExpo))
				.setCallback(new TweenCallback() {
					
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						finishedTweening = true;
					}
					
				})
				.start(getTweenManager());
			loadingTextItem.remove();
			finishedLoading = true;
		}
		
		if (!finishedLoading) {
			loadingTextItem.setString("Now Loading " + (int) (Assets.getAssetManager().getProgress() * 100) + "%");
			loadingTextItem.setX((Gdx.graphics.getWidth() - loadingTextItem.getWidth()) / 2);
		}
	}

	@Override
	public void postRender(float delta) {
		if (finishedTweening) {
			game.setScreen(new GameScreen(game, sunrays, leftColor, rightColor));
			dispose();
		}
	}
	
}
