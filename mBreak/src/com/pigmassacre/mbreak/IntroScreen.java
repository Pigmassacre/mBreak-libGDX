package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class IntroScreen extends AbstractScreen {

	Logo logo;
	TextItem[] introMessage;
	float stateTime;
	
	TextItem versionMessage;
	
	Music music;
	
	public IntroScreen(MBreak game) {
		super(game);
		logo = new Logo(getAtlas());
		stage.addActor(logo);
		
		createIntroMessage();
		
		versionMessage = new TextItem("v0.9");
		versionMessage.setX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setY(versionMessage.getHeight() + versionMessage.getHeight());
		stage.addActor(versionMessage);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/title/goluigi-nonuniform.ogg"));
		music.setLooping(true);
		music.play();
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
		}
		
		stateTime = 0f;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		stage.setViewport(width, height, true);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		stateTime += Gdx.graphics.getDeltaTime() * 1000;
		
		for (int i = 0; i < introMessage.length; i++) {
			float differentiator = i * 64;
			float sinScale = 0.0075f;

			float sin = 0.3f * 3f;
			sin *= Math.tan((stateTime + differentiator) * (sinScale / 4.0));
			sin *= Math.sin((stateTime + differentiator) * (sinScale / 16.0));
			sin *= Math.sin((stateTime + differentiator) * (sinScale / 8.0));
			sin *= Math.sin((stateTime + differentiator) * sinScale);

			introMessage[i].setOffsetY(sin * 2.0f * 3f);
		}
		
		versionMessage.setX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setY(versionMessage.getHeight() + versionMessage.getHeight());
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

	@Override
	public void dispose() {
		super.dispose();
		stage.dispose();
	}
	
}
