package com.pigmassacre.mbreak;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;

public class IntroScreen extends AbstractScreen {

	Logo logo;
	TextItem[] introMessage;
	float stateTime;
	
	TextItem versionMessage;
	
	Music music;
	
	float introTime, endTime;
	
	public IntroScreen(MBreak game) {
		super(game);
		
		logo = new Logo();
		logo.setX((Gdx.graphics.getWidth() - logo.getWidth()) / 2);
		System.out.println(logo.getX());
		logo.setY((Gdx.graphics.getHeight() / 2));
		stage.addActor(logo);
		Tween.from(logo, WidgetAccessor.POSITION_Y, 1.0f).target(Gdx.graphics.getHeight() + logo.getHeight())
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());
		
		createIntroMessage();
		
		versionMessage = new TextItem("alpha 1");
		versionMessage.setX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setY(versionMessage.getHeight() + versionMessage.getHeight());
		versionMessage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		stage.addActor(versionMessage);
		Tween.from(versionMessage, WidgetAccessor.POSITION_XY, 1.0f).target(Gdx.graphics.getWidth(), -versionMessage.getHeight())
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());

		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/title/goluigi-nonuniform.ogg"));
		music.setLooping(true);
		music.play();
		
		introTime = 0;
		endTime = 1.0f;
	}
	
	@SuppressWarnings("incomplete-switch")
	private void createIntroMessage() {
		CharSequence string = null;
		int blinkBegin = 0;
		int blinkEnd = 0;
		switch(Gdx.app.getType()) {
		   case Android:
			   string = "TOUCH to start";
			   blinkBegin = 0;
			   blinkEnd = 4;
			   break;
		   case Desktop:
			   string = "Press ENTER to start";
			   blinkBegin = 6;
			   blinkEnd = 11;
			   break;
		}
		
		introMessage = new TextItem[string.length()];
		
		for (int i = 0; i < string.length(); i++) {
			introMessage[i] = new TextItem(string.subSequence(i, i + 1));
			introMessage[i].setMaxOffsetY(10 * Settings.GAME_SCALE);
			introMessage[i].setColor(1.0f, 1.0f, 1.0f, 1.0f);
			stage.addActor(introMessage[i]);
		}
		
		int sum = 0;
		for (int i = 0; i < introMessage.length; i++) {
			sum += introMessage[i].getWidth();
		}
		
		float offset = 0;
		for (int i = 0; i < string.length(); i++) {
			introMessage[i].setX(((Gdx.graphics.getWidth() - sum) / 2) + offset);
			introMessage[i].setY(logo.getY() - introMessage[i].getHeight());
			offset += introMessage[i].getWidth();
			
			if (blinkBegin <= i && i <= blinkEnd) {
				introMessage[i].blink = true;
			}
			
			Tween.from(introMessage[i], WidgetAccessor.POSITION_Y, 1.0f).target(-introMessage[i].getHeight())
				.ease(TweenEquations.easeOutBounce)
				.start(getTweenManager());
		}
		
		stateTime = 0f;
	}

	@Override
	public void render(float delta) {
		if (Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(new MainMenuScreen(game, logo));
		}
		
		stateTime += Gdx.graphics.getDeltaTime() * 1000;
		
		for (int i = 0; i < introMessage.length; i++) {
			float differentiator = i * 64;
			float sinScale = 0.0075f;

			float sin = 0.3f * Settings.GAME_SCALE;
			sin *= Math.tan((stateTime + differentiator) * (sinScale / 4.0));
			sin *= Math.sin((stateTime + differentiator) * (sinScale / 16.0));
			sin *= Math.sin((stateTime + differentiator) * (sinScale / 8.0));
			sin *= Math.sin((stateTime + differentiator) * sinScale);

			introMessage[i].setOffsetY(sin * 2.0f * 3f);
		}
		
		versionMessage.setX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setY(versionMessage.getHeight() + versionMessage.getHeight());
		
		if (introTime < endTime)
			introTime += delta;
		if (introTime >= endTime)
			super.render(delta);
	}

	@Override
	public void pause() {
		super.pause();
		music.pause();
	}

	@Override
	public void resume() {
		super.resume();
		music.play();
	}
	
}
