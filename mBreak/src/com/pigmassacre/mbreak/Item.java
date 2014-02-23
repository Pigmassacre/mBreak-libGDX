package com.pigmassacre.mbreak;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class Item extends Widget {
	
	ShapeRenderer shapeRenderer;

	Color shadowColor = new Color(0.196f, 0.196f, 0.196f, 1.0f);
	Color selectedColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
	Color chosenColor = new Color(0.784f, 0.784f, 0.784f, 1.0f);
	Color disabledColor = new Color(0.294f, 0.294f, 0.294f, 0.941f);

	float shadowOffsetX, shadowOffsetY;
	
	float offsetX, offsetY, maxOffsetX, maxOffsetY;
	float offsetXRetreatSpeed, offsetYRetreatSpeed;
	
	float borderSizeSelected = 2 * Settings.GAME_SCALE;
	float borderSizeChosen = 2 * Settings.GAME_SCALE;
	
	boolean selected = false, chosen = false, disabled = false;
	
	Rectangle rectangle, selectedRectangle, chosenRectangle, shadowRectangle;
	
	float stateTime;

	public Item() {
		shapeRenderer = new ShapeRenderer();
		
		setWidth(16 * Settings.GAME_SCALE);
		setHeight(16 * Settings.GAME_SCALE);
		
		setShadowOffsetX(0 * Settings.GAME_SCALE);
		setShadowOffsetY(-1 * Settings.GAME_SCALE);

		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		selectedRectangle = new Rectangle(getX() - (borderSizeSelected / 2), getY() - (borderSizeSelected / 2), getWidth() + borderSizeSelected, getHeight() + borderSizeSelected);
		chosenRectangle = new Rectangle(getX() - (borderSizeChosen / 2), getY() - (borderSizeChosen / 2), getWidth() + borderSizeChosen, getHeight() + borderSizeChosen);
		shadowRectangle = new Rectangle(getX() + getShadowOffsetX(), getY() + getShadowOffsetY(), getWidth(), getHeight());

		offsetX = 0f;
		offsetY = 0f;
		maxOffsetX = 2 * Settings.GAME_SCALE;
		maxOffsetY = 2 * Settings.GAME_SCALE;
		offsetXRetreatSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		offsetYRetreatSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		stateTime = 0;
	}
	
	float getOffsetX() {
		return offsetX;
	}

	void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}
	
	float getOffsetY() {
		return offsetY;
	}

	void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
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
		selectedRectangle.setX(getX() - (borderSizeSelected / 2) + offsetX);
		selectedRectangle.setY(getY() - (borderSizeSelected / 2) + offsetY);
		chosenRectangle.setX(getX() - (borderSizeChosen / 2) + offsetX);
		chosenRectangle.setY(getY() - (borderSizeChosen / 2) + offsetY);
		shadowRectangle.setX(getX() + getShadowOffsetX() + offsetX);
		shadowRectangle.setY(getY() + getShadowOffsetY() + offsetY);
	}
			
	public void draw(Batch batch, float parentAlpha) {
		batch.end();
		
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());
        shapeRenderer.translate(getX(), getY(), 0);
		
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(shadowColor);
		shapeRenderer.rect(shadowRectangle.getX(), shadowRectangle.getY(), shadowRectangle.getWidth(), shadowRectangle.getHeight());
		shapeRenderer.end();

		if (chosen) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(chosenColor);
			shapeRenderer.rect(chosenRectangle.getX(), chosenRectangle.getY(), chosenRectangle.getWidth(), chosenRectangle.getHeight());
			shapeRenderer.end();

			if (selected) {
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
				shapeRenderer.setColor(selectedColor);
				shapeRenderer.rect(selectedRectangle.getX(), selectedRectangle.getY(), selectedRectangle.getWidth(), selectedRectangle.getHeight());
				shapeRenderer.end();
			}
		} else if (selected) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(selectedColor);
			shapeRenderer.rect(selectedRectangle.getX(), selectedRectangle.getY(), selectedRectangle.getWidth(), selectedRectangle.getHeight());
			shapeRenderer.end();
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(getColor());
		shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
		shapeRenderer.end();

		if (disabled) {
			shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
			shapeRenderer.setColor(disabledColor);
			shapeRenderer.rect(rectangle.getX(), rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
			shapeRenderer.end();
		}
		
		batch.begin();
	}
	
}
