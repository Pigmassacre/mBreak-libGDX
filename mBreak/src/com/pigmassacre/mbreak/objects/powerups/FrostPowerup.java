package com.pigmassacre.mbreak.objects.powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Particle;
import com.pigmassacre.mbreak.objects.effects.FrostEffect;

public class FrostPowerup extends Powerup {

	private static final int FRAME_COLS = 1;
	private static final int FRAME_ROWS = 3;

	private TextureRegion[] frames;

	private float centerYGrace = 0.25f * Settings.GAME_SCALE;
	
	private static final float FROST_EFFECT_DURATION = 5f;
	
	public static final float PARTICLE_SPAWN_RATE = 0.6f;
	public static final int PARTICLE_LEAST_SPAWN_AMOUNT = 2;
	public static final int PARTICLE_MAXIMUM_SPAWN_AMOUNT = 2;
	
	private float particleSpawnTime = 0f;

	public FrostPowerup(float x, float y) {
		super(x, y);

		TextureRegion sheet = getAtlas().findRegion("frost");
		TextureRegion[][] temp = sheet.split(sheet.getRegionWidth() / FRAME_COLS, sheet.getRegionHeight() / FRAME_ROWS);
		frames = new TextureRegion[(FRAME_COLS * FRAME_ROWS) + 1];

		int index = 0;
		for (int i = 0; i < FRAME_COLS; i++) {
			for (int j = 0; j < FRAME_ROWS; j++) {
				frames[index++] = temp[j][i];
			}
		}
		image = frames[0];

		setWidth((sheet.getRegionWidth() / FRAME_COLS) * Settings.GAME_SCALE);
		setHeight((sheet.getRegionHeight() / FRAME_ROWS) * Settings.GAME_SCALE);
	}

	@Override
	protected void onHit(GameActor actor) {
		new FrostEffect(actor, FROST_EFFECT_DURATION);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);

		if (offsetY < -centerYGrace) {
			image = frames[0];
		} else if (offsetY > centerYGrace) {
			image = frames[2];
		} else {
			image = frames[1];
		}
		
		particleSpawnTime += delta;
		if (particleSpawnTime >= PARTICLE_SPAWN_RATE) {
			particleSpawnTime = 0f;
			
			for (int i = 0; i < MathUtils.random(PARTICLE_LEAST_SPAWN_AMOUNT, PARTICLE_MAXIMUM_SPAWN_AMOUNT); i++) {
				float width = MathUtils.random(2.25f * Settings.GAME_SCALE, 3f * Settings.GAME_SCALE);
				float angle = MathUtils.random(0, 2 * MathUtils.PI);
				float speed = MathUtils.random(0.2f * Settings.GAME_FPS * Settings.GAME_SCALE, 0.35f * Settings.GAME_FPS * Settings.GAME_SCALE);
				float retardation = speed / 52f;
				Color tempColor = new Color(MathUtils.random(0, 0.2f), MathUtils.random(0.5f, 1f), MathUtils.random(0.85f, 1f), 1f);
				Particle particle = Particle.particlePool.obtain();
				particle.init(getX() + getWidth() / 2 + offsetX, getY() + getHeight() / 2 + offsetY, width, width, angle, speed, retardation, 0.03f * Settings.GAME_FPS, tempColor);
			}
		}
	}

}
