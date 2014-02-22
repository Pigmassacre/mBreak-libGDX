package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Logo extends Actor {
	
	private static final int FRAME_COLS = 1;
	private static final int FRAME_ROWS = 7;
	
	TextureAtlas atlas;
	
	Animation logoAnimation;
	TextureRegion logoSheet;
	TextureRegion[] logoFrames;
	TextureRegion currentFrame;
	
	float stateTime, waitTime;
	
	public Logo() {
		super();
		
		atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		logoSheet = atlas.findRegion("mBreak_title");
		TextureRegion[][] temp = logoSheet.split(logoSheet.getRegionWidth() / FRAME_COLS, logoSheet.getRegionHeight() / FRAME_ROWS);
		logoFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) + 1];

		int index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			for (int j = 0; j < FRAME_ROWS; j++) {
				logoFrames[index++] = temp[j][i]; // i, j??? wtf y no work but j, i work???
			}
		}
		logoFrames[index++] = temp[0][0]; // Add the first frame again.
		
		logoAnimation = new Animation(0.075f, logoFrames);
		logoAnimation.setPlayMode(Animation.LOOP_PINGPONG);
		
		stateTime = 0f;
		waitTime = 0f;
		
		setX((Gdx.graphics.getWidth() - getWidth()) / 2);
		setY((Gdx.graphics.getHeight() / 2));
	}
	
	@Override
	public float getWidth() {
		if (currentFrame == null) {
			return logoFrames[0].getRegionWidth() * 2 * Settings.GAME_SCALE;
		}
		return currentFrame.getRegionWidth() * 2 * Settings.GAME_SCALE;
	}
	
	@Override
	public float getHeight() {
		if (currentFrame == null) {
			System.out.println("height " + logoFrames[0].getRegionHeight() * 6);
			return logoFrames[0].getRegionHeight() * 2 * Settings.GAME_SCALE;
		}
		return currentFrame.getRegionHeight() * 2 * Settings.GAME_SCALE;
	}
	
	public void act(float delta) {
		setX((Gdx.graphics.getWidth() - getWidth()) / 2);
		setY((Gdx.graphics.getHeight() / 2));
		
		if (logoAnimation.getKeyFrameIndex(stateTime) == logoFrames.length - 1 || logoAnimation.getKeyFrameIndex(stateTime) == 0) {
			waitTime += Gdx.graphics.getDeltaTime();
			if (waitTime > 1.55f) {
				stateTime += Gdx.graphics.getDeltaTime();
			}
		} else {
			waitTime = 0f;
			stateTime += Gdx.graphics.getDeltaTime();
		}
	}
	
	public void draw(Batch batch, float parentAlpha) {
		currentFrame = logoAnimation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, getX(), getY(), getWidth(), getHeight());
	}
		
}
