package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class TextItem extends Item {
	
	private BitmapFont font;
	private CharSequence string;
	
	private Color shadowColor;
	
	public boolean blink = false;
	private float blinkRate = 0.75f;
	private float stateBlinkTime;
	
	public TextItem(CharSequence string) {
		super();
		
		font = new BitmapFont(Gdx.files.internal("fonts/ADDLG__.fnt"), Gdx.files.internal("fonts/ADDLG__.png"), false);
		font.setScale(Settings.GAME_SCALE);
		shadowColor = new Color(0.196f, 0.196f, 0.196f, 1.0f);
		
		this.string = string;
		
		shadowOffsetX = 0f;
		shadowOffsetY = -1f * Settings.GAME_SCALE;
		
		stateBlinkTime = 0f;
	}
	
	public void setString(CharSequence string) {
		this.string = string;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public float getWidth() {
		return font.getBounds(string).width;
	}
	
	public float getHeight() {
		return font.getBounds(string).height;
	}
	
	public void act(float delta) {
		super.act(delta);
		
		if (blink) {
			stateBlinkTime += delta;
			if (stateBlinkTime > blinkRate) {
				if (getColor().a == 1.0f) {
					getColor().set(getColor().r, getColor().g, getColor().b, 0.0f);
					shadowColor.set(shadowColor.r, shadowColor.g, shadowColor.b, 0.0f);
					stateBlinkTime = blinkRate / 3f;
				} else {
					getColor().set(getColor().r, getColor().g, getColor().b, 1.0f);
					shadowColor.set(shadowColor.r, shadowColor.g, shadowColor.b, 1.0f);
					stateBlinkTime = 0;
				}
			}
		}
	}
	
	public void draw(Batch batch, float parentAlpha) {
		font.setColor(shadowColor);
		font.draw(batch, string, getX() + getOffsetX() + getShadowOffsetX(), getY() + getOffsetY() + getShadowOffsetY());
		font.setColor(getColor());
		font.draw(batch, string, getX() + getOffsetX(), getY() + getOffsetY());
	}
	
}
