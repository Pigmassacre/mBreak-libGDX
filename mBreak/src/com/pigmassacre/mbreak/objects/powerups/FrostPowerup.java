package com.pigmassacre.mbreak.objects.powerups;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.effects.FrostEffect;

public class FrostPowerup extends Powerup {

	private static final int FRAME_COLS = 1;
	private static final int FRAME_ROWS = 3;

	private TextureRegion[] frames;

	private float centerYGrace = 0.25f * Settings.GAME_SCALE;
	
	private static final float FROST_EFFECT_DURATION = 5f;

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
		actor.effectGroup.addActor(new FrostEffect(actor, FROST_EFFECT_DURATION));
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
	}

}
