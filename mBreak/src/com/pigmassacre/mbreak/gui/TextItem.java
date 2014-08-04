package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;

public class TextItem extends Item {
	
	private BitmapFont font;
	private CharSequence string;
	
	private Color shadowColor;
	
	public boolean blink = false;
	private float blinkRate = 0.75f;
	private float stateBlinkTime;
	
	private boolean wrapped;
	
	private float textSideBounds;

	private HAlignment alignment;
	
	public TextItem(CharSequence string) {
		super();

		font = Assets.getBitmapFont("fonts/ADDLG__.fnt");
		font.setScale(Settings.GAME_SCALE);
		shadowColor = new Color(0.196f, 0.196f, 0.196f, 1.0f);
		
		this.string = string;
		
		shadowOffsetX = 0f;
		shadowOffsetY = -1f * Settings.GAME_SCALE;
		
		textSideBounds = 25f * Settings.GAME_SCALE;
		wrapped = false;
		alignment = HAlignment.LEFT;
		
		stateBlinkTime = 0f;

		float selectionWidthIncrease = 1.0f;
		float selectionHeightIncrease = 1.5f;
		rectangle = new Rectangle(getX() + (getWidth() - getWidth() * selectionWidthIncrease), 
								  getY() + (getHeight() - getHeight() * selectionHeightIncrease), 
								  getWidth() * selectionWidthIncrease, 
								  getHeight() * selectionHeightIncrease);
	}
	
	public float getScaleX() {
		return font.getScaleX();
	}
	
	public float getScaleY() {
		return font.getScaleY();
	}
	
	public void setScale(float scaleXY) {
		font.setScale(scaleXY);
	}
	
	public void setScale(float scaleX, float scaleY) {
		font.setScale(scaleX, scaleY);
	}

	public void setString(CharSequence string) {
		this.string = string;
	}
	
	public void setAlignment(HAlignment alignment) {
		this.alignment = alignment;
	}
	
	public HAlignment getAlignment() {
		return alignment;
	}
	
	public boolean isWrapped() {
		return wrapped;
	}

	public void setWrapped(boolean wrapped) {
		this.wrapped = wrapped;
	}
	
	public float getWrapWidth() {
		return Gdx.graphics.getWidth() - textSideBounds;
	}
	
	public BitmapFont getFont() {
		return font;
	}
	
	public float getWidth() {
		if (wrapped) {
//			return font.getCache().setWrappedText(string, 0, 0, Gdx.graphics.getWidth() - textSideBounds, alignment).width;
			return font.getWrappedBounds(string, Gdx.graphics.getWidth() - textSideBounds).width;
		} else {
			return font.getBounds(string).width;
		}
	}
	
	public float getHeight() {
		if (wrapped) {
//			return font.getCache().setWrappedText(string, 0, 0, Gdx.graphics.getWidth() - textSideBounds, alignment).height;
			return font.getWrappedBounds(string, Gdx.graphics.getWidth() - textSideBounds).height;
		} else {
			return font.getBounds(string).height;
		}
	}
	
	public void setHide(Boolean hide) {
		if (hide) {
			getColor().set(getColor().r, getColor().g, getColor().b, 0.0f);
			shadowColor.set(shadowColor.r, shadowColor.g, shadowColor.b, 0.0f);
		} else {
			getColor().set(getColor().r, getColor().g, getColor().b, 1.0f);
			shadowColor.set(shadowColor.r, shadowColor.g, shadowColor.b, 1.0f);
		}
	}
	
	public void act(float delta) {
		super.act(delta);
		
		if (blink) {
			stateBlinkTime += delta;
			if (stateBlinkTime > blinkRate) {
				if (getColor().a == 1.0f) {
					setHide(true);
					stateBlinkTime = blinkRate / 3f;
				} else {
					setHide(false);
					stateBlinkTime = 0;
				}
			}
		}
	}
	
	public void draw(Batch batch, float parentAlpha) {
		font.setColor(shadowColor);
		if (wrapped) {
			font.drawWrapped(batch, string, getX() + getOffsetX() + getShadowOffsetX(), getY() + getOffsetY() + getShadowOffsetY(), getWrapWidth(), alignment);
		} else {
			font.draw(batch, string, getX() + getOffsetX() + getShadowOffsetX(), getY() + getOffsetY() + getShadowOffsetY());
		}
		if (getSelected()) {
			font.setColor(selectedColor);
		} else {
			font.setColor(getColor());
		}
		if (wrapped) {
			font.drawWrapped(batch, string, getX() + getOffsetX(), getY() + getOffsetY(), getWrapWidth(), alignment);
		} else {
			font.draw(batch, string, getX() + getOffsetX(), getY() + getOffsetY());
		}
	}
	
}
