package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.pigmassacre.mbreak.Settings;

public class Logo extends Widget {
	
	private static final int FRAME_COLS = 1;
	private static final int FRAME_ROWS = 7;
	
	private TextureAtlas atlas;
	
	Animation logoAnimation;
	TextureRegion logoSheet;
	TextureRegion[] logoFrames;
	TextureRegion currentFrame;
	
	float animationStateTime, waitTime, stateTime;
	
	private float scaleX, scaleY, rotation;
	
	public Logo() {
		super();
		
		logoSheet = getAtlas().findRegion("mBreak_title");
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
		
		animationStateTime = 0f;
		waitTime = 0f;
		
		scaleX = 1f;
		scaleY = 1f;
		rotation = 0f;
	}
	
	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
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
			return logoFrames[0].getRegionHeight() * 2 * Settings.GAME_SCALE;
		}
		return currentFrame.getRegionHeight() * 2 * Settings.GAME_SCALE;
	}
	
	public void act(float delta) {
		if (logoAnimation.getKeyFrameIndex(animationStateTime) == logoFrames.length - 1 || logoAnimation.getKeyFrameIndex(animationStateTime) == 0) {
			waitTime += delta;
			if (waitTime > 1.55f) {
				animationStateTime += delta;
			}
		} else {
			waitTime = 0f;
			animationStateTime += delta;
		}
		stateTime += delta;
		rotation = 2 * MathUtils.sin(stateTime * 2);
		scaleX = scaleY = MathUtils.clamp(2 + MathUtils.sin(stateTime * 4), 0.75f, 1.25f);
	}
	
	public void draw(Batch batch, float parentAlpha) {
		currentFrame = logoAnimation.getKeyFrame(animationStateTime, true);
		batch.draw(currentFrame, getX(), getY(), getWidth() / 2, getHeight() / 2, getWidth(), getHeight(), scaleX, scaleY, rotation);
	}
		
}
