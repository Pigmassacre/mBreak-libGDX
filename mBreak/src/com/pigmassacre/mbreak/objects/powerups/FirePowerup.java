package com.pigmassacre.mbreak.objects.powerups;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Particle;
import com.pigmassacre.mbreak.objects.effects.FireEffect;

public class FirePowerup extends Powerup {

	private static final float FIRE_EFFECT_DURATION = 10f;
	
	public static final float PARTICLE_SPAWN_RATE = 0.1f;
	public static final int PARTICLE_LEAST_SPAWN_AMOUNT = 2;
	public static final int PARTICLE_MAXIMUM_SPAWN_AMOUNT = 4;
	
	private float particleSpawnTime = 0f;
	
	public FirePowerup(float x, float y) {
		super(x, y);

		image = getAtlas().findRegion("fire");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
	}

	@Override
	protected void onHit(GameActor actor) {
		new FireEffect(actor, FIRE_EFFECT_DURATION);
//		applyEffectToAllBalls(actor, new FireEffect(actor, duration));
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
		particleSpawnTime += delta;
		if (particleSpawnTime >= PARTICLE_SPAWN_RATE) {
			particleSpawnTime = 0f;
			
			for (int i = 0; i < MathUtils.random(PARTICLE_LEAST_SPAWN_AMOUNT, PARTICLE_MAXIMUM_SPAWN_AMOUNT); i++) {
				float width = MathUtils.random(1.25f * Settings.GAME_SCALE, 2f * Settings.GAME_SCALE);
				float angle = MathUtils.random(0, 2 * MathUtils.PI);
				float speed = MathUtils.random(0.75f * Settings.GAME_FPS * Settings.GAME_SCALE, 0.9f * Settings.GAME_FPS * Settings.GAME_SCALE);
				float retardation = speed / 12f;
				Color tempColor;
				if (MathUtils.random() > 0.1) {
					tempColor = new Color(MathUtils.random(0.75f, 1f), MathUtils.random(0f, 1f), 0f, 1f);
				} else {
					float temp = MathUtils.random(0, 1f);
					tempColor = new Color(temp, temp, temp, 1f);
				}
				Particle particle = Particle.particlePool.obtain();
				particle.init(getX() + getWidth() / 2, getY() + getHeight() / 2, width, width, angle, speed, retardation, 0.05f * Settings.GAME_FPS, tempColor);
			}
		}
	}
	
}
