package com.pigmassacre.mbreak.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.pigmassacre.mbreak.Settings;

public class Item extends Widget {
	
	public interface ItemCallback {
		public void execute(Object data);
	}
	
	ItemCallback callback;
	
	ShapeRenderer shapeRenderer;

	Color shadowColor = new Color(0.196f, 0.196f, 0.196f, 1.0f);
	Color selectedColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	Color chosenColor = new Color(0.784f, 0.784f, 0.784f, 1.0f);
	Color disabledColor = new Color(0.294f, 0.294f, 0.294f, 0.941f);

	float shadowOffsetX, shadowOffsetY;
	
	float offsetX, offsetY, maxOffsetX, maxOffsetY;

	float offsetXRetreatSpeed, offsetYRetreatSpeed;
	
	public boolean selected = false, chosen = false, disabled = false;
	
	float stateTime;
	
	Rectangle rectangle;

	public Item() {
		shapeRenderer = new ShapeRenderer();
		
		setWidth(16 * Settings.GAME_SCALE);
		setHeight(16 * Settings.GAME_SCALE);
		
		setShadowOffsetX(0 * Settings.GAME_SCALE);
		setShadowOffsetY(-1 * Settings.GAME_SCALE);

		offsetX = 0f;
		offsetY = 0f;
		maxOffsetX = 2 * Settings.GAME_SCALE;
		maxOffsetY = 2 * Settings.GAME_SCALE;
		offsetXRetreatSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		offsetYRetreatSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		stateTime = 0;
		
		setColor(0.5f, 0.5f, 0.5f, 1.0f);
	}
	
	public void setCallback(ItemCallback callback) {
		this.callback = callback;
	}
	
	public void executeCallback() {
		if (callback != null)
			this.callback.execute(this);
	}
	
	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public float getMaxOffsetX() {
		return maxOffsetX;
	}

	public void setMaxOffsetX(float maxOffsetX) {
		this.maxOffsetX = maxOffsetX;
	}

	public float getMaxOffsetY() {
		return maxOffsetY;
	}

	public void setMaxOffsetY(float maxOffsetY) {
		this.maxOffsetY = maxOffsetY;
	}
	
	public float getShadowOffsetX() {
		return shadowOffsetX;
	}
	
	public float getShadowOffsetY() {
		return shadowOffsetY;
	}
	
	public void setShadowOffsetX(float x) {
		this.shadowOffsetX = x;
	}
	
	public void setShadowOffsetY(float y) {
		this.shadowOffsetY = y;
	}
	
	public boolean isPointerOverItem(float x, float y) {
		return x >= rectangle.getX() && x <= rectangle.getX() + rectangle.getWidth() && y <= rectangle.getY() && y >= rectangle.getY() - rectangle.getHeight();
	}

	public void act(float delta) {
		if (selected) {
			offsetY = (float) ((Math.sin(stateTime * 0.0075) + 1) / 2.0f) * maxOffsetY;
			stateTime += delta * 1000;
		} else {
			stateTime = 0;
			if (offsetY < 0) {
				offsetY += offsetYRetreatSpeed * delta;
				if (offsetY > 0)
					offsetY = 0;
			} else if (offsetY > 0) {
				offsetY -= offsetYRetreatSpeed * delta;
				if (offsetY < 0)
					offsetY = 0;
			}
		}
	
		if (offsetX < -maxOffsetX) {
			offsetX = -maxOffsetX;
		} else if (offsetX > maxOffsetX) {
			offsetX = maxOffsetX;
		}

		if (offsetY < -maxOffsetY) {
			offsetY = -maxOffsetY;
		} else if (offsetY > maxOffsetY) {
			offsetY = maxOffsetY;
		}
		
		rectangle.setX(getX() + offsetX);
		rectangle.setY(getY() + offsetY);
	}
	
}