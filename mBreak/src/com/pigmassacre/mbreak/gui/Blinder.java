package com.pigmassacre.mbreak.gui;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Expo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.utils.Array;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;

public class Blinder extends Widget {

	private int ROWS, COLS;
	
	private TextureRegion image;
	private Array<BlinderPart> blinderParts;
	
	public Blinder() {
		image = Assets.getTextureRegion("square");
	}
	
	public Blinder(TextureRegion image, TweenManager tweenManager) {
		int n = (int) Math.pow(4, 4);
		COLS = (int) Math.ceil(Math.sqrt(n));
		ROWS = (int) Math.ceil(n / (double) COLS);
		
		TextureRegion[][] splitImages = image.split((int) (Gdx.graphics.getWidth() / (float)ROWS), (int) (Gdx.graphics.getHeight() / (float)COLS));
		
		blinderParts = new Array<BlinderPart>();
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				BlinderPart blinderPart = new BlinderPart(splitImages[col][row]);
				blinderPart.setColor(36/255f, 36/255f, 36/255f, 1f);
				blinderPart.setWidth(splitImages[col][row].getRegionWidth());
				blinderPart.setHeight(splitImages[col][row].getRegionHeight());
				blinderPart.setX(getX() + blinderPart.getWidth() * row);
				blinderPart.setY(getY() + blinderPart.getHeight() * col);
				blinderParts.add(blinderPart);
				Timeline.createSequence()
				.push(Tween.to(blinderPart, ActorAccessor.SCALE_XY, 0.5f)
						.target(0f, 0f)
						.ease(Expo.OUT)
						.delay(MathUtils.random() * 0.5f))
				.setUserData(blinderPart)
				.setCallback(new TweenCallback() {
					
					@Override
					public void onEvent(int type, BaseTween<?> source) {
						executeCallback();
						BlinderPart blinderPart = (BlinderPart) source.getUserData();
						blinderPart.remove();
						
					}
					
				})
				.start(tweenManager);
			blinderParts.add(blinderPart);
			}
		}
	}
	
	public void setup(TweenManager tweenManager) {
		int n = (int) Math.pow(4, 4);
		COLS = (int) Math.ceil(Math.sqrt(n));
		ROWS = (int) Math.ceil(n / (double) COLS);
		
		blinderParts = new Array<BlinderPart>();
		float delay = 0f;
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				BlinderPart blinderPart = new BlinderPart(image);
				blinderPart.setColor(36/255f, 36/255f, 36/255f, 1f);
				blinderPart.setWidth(Gdx.graphics.getWidth() / (float)ROWS);
				blinderPart.setHeight(Gdx.graphics.getHeight() / (float)COLS);
				blinderPart.setX(getX() + blinderPart.getWidth() * row);
				blinderPart.setY(getY() + blinderPart.getHeight() * col);
				Timeline.createSequence()
					.push(Tween.to(blinderPart, ActorAccessor.SCALE_XY, 0.5f)
							.target(0f, 0f)
							.ease(Expo.OUT)
							.delay(delay))
					.setUserData(blinderPart)
					.setCallback(new TweenCallback() {
						
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							executeCallback();
							BlinderPart blinderPart = (BlinderPart) source.getUserData();
							blinderPart.remove();
							
						}
						
					})
					.start(tweenManager);
				blinderParts.add(blinderPart);
			}
			delay += 0.035f;
		}
	}

	public interface SplashCallback {
		
		public void execute(Blinder splash);
		
	}
	
	private SplashCallback callback;
	
	public void setCallback(SplashCallback callback) {
		this.callback = callback;
	}
	
	private void executeCallback() {
		if (callback != null) {
			callback.execute(this);
		}
	}
	
	@Override
	public float getWidth() {
		return image.getRegionWidth() * Settings.GAME_SCALE;
	}
	
	@Override
	public float getHeight() {
		return image.getRegionHeight() * Settings.GAME_SCALE;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		for (BlinderPart blinderPart : blinderParts) {
			blinderPart.draw(batch, parentAlpha);
		}
	}
	
	private class BlinderPart extends Widget {
		
		private TextureRegion image;
		
		public BlinderPart(TextureRegion image) {
			this.image = image;
		}
		
		private Color temp;
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(image, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), getScaleX(), getScaleY(), 0);
			batch.setColor(temp);
		}
		
	}
		
}
