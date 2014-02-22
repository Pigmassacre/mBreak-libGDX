package com.pigmassacre.mbreak;

import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Game implements ApplicationListener {

	Stage stage;
	
	FPSLogger fpsLogger;
	
	Logo logo;
	TextItem[] introMessage;
	float stateTime;
	
	TextItem versionMessage;
	
	Music music;
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void create() {
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		fpsLogger = new FPSLogger();
		
		logo = new Logo();
		stage.addActor(logo);
		
		createIntroMessage();
		
		versionMessage = new TextItem("v0.9");
		versionMessage.setOriginX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setOriginY(versionMessage.getHeight() + versionMessage.getHeight());
		stage.addActor(versionMessage);
		
		music = Gdx.audio.newMusic(Gdx.files.internal("music/title/goluigi-nonuniform.ogg"));
		music.play();
	}
	
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
			introMessage[i].setOriginX(((Gdx.graphics.getWidth() - sum) / 2) + offset);
			introMessage[i].setOriginY(logo.getY() - introMessage[i].getHeight());
			offset += introMessage[i].getWidth();
			
			if (blinkBegin <= i && i <= blinkEnd) {
				introMessage[i].blink = true;
			}
		}
		
		stateTime = 0f;
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void render() {
		fpsLogger.log();
		stage.act(Gdx.graphics.getDeltaTime());
		
		stateTime += Gdx.graphics.getDeltaTime() * 1000;
		
		int sum = 0;
		for (int i = 0; i < introMessage.length; i++) {
			sum += introMessage[i].getWidth();
		}
		
		float offset = 0;
		for (int i = 0; i < introMessage.length; i++) {
			float heightDifferentiator = i * 64;
			float sinScale = 0.0075f;

			float sin = 0.3f * 3f;
			sin *= Math.tan((stateTime + heightDifferentiator) * (sinScale / 4.0));
			sin *= Math.sin((stateTime + heightDifferentiator) * (sinScale / 16.0));
			sin *= Math.sin((stateTime + heightDifferentiator) * (sinScale / 8.0));
			sin *= Math.sin((stateTime + heightDifferentiator) * sinScale);

			introMessage[i].setY(sin * 2.0f * 3f);
			introMessage[i].setOriginX(((Gdx.graphics.getWidth() - sum) / 2) + offset);
			introMessage[i].setOriginY(logo.getY() - introMessage[i].getHeight());
			offset += introMessage[i].getWidth();
		}
		
		versionMessage.setOriginX(Gdx.graphics.getWidth() - versionMessage.getWidth() - versionMessage.getHeight());
		versionMessage.setOriginY(versionMessage.getHeight() + versionMessage.getHeight());
		
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		stage.draw();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}
