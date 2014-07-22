package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.Settings;

public class Paddle extends GameActor {
	
	private float maxHeight, minHeight, maxWidth, minWidth;
	
	private float acceleration, retardation, velocityX, velocityY;
	public float maxSpeed;
	public final float defaultMaxSpeed;
	private float centerX;
	
	private float stabilizeSpeed, maxNudgeDistance;
	
	private Color hitEffectStartColor, hitEffectFinalColor;
	private float hitEffectTickAmount;
	
	public boolean moveUp, moveDown;
	public Rectangle touchRectangle;
	private float touchGraceSize;
	public int keyUp, keyDown;
	private float touchY;
	
	public Paddle(Player owner) {
		this.owner = owner;
		
		setColor(new Color(owner.getColor()));
		
		image = Assets.getTextureRegion("paddle");
		
		setDepth(2 * Settings.GAME_SCALE);
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE - getDepth());
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		acceleration = 1.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		retardation = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		defaultMaxSpeed = maxSpeed = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		
		velocityY = 0f;
		
		centerX = getX();
		
		stabilizeSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxNudgeDistance = 2.5f * Settings.GAME_SCALE;
		
		touchGraceSize = getHeight() / 8;
		moveUp = false;
		moveDown = false;
		
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);
		
		Groups.paddleGroup.addActor(this);
	}
	
	@Override
	public void setX(float x) {
		super.setX(x);
		rectangle.setX(getX());
	}
	
	@Override
	public void setY(float y) {
		super.setY(y);
		rectangle.setY(getY());
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		moveUp = false; 
		moveDown = false;
		if (Gdx.app.getType() == ApplicationType.Android) {
			for (int i = 0; i < 10; i++) {
				if (touchRectangle.contains(Gdx.input.getX(i), Gdx.input.getY(i)) && Gdx.input.isTouched(i)) {
					touchY = Gdx.graphics.getHeight() - Gdx.input.getY(i);
					moveUp = getY() + (getHeight() / 2) < touchY - touchGraceSize;
					moveDown = getY() + (getHeight() / 2) > touchY + touchGraceSize;
					if (moveUp || moveDown)
						break;
				}
			}
		} else {
			moveUp = Gdx.input.isKeyPressed(keyUp);
			moveDown = Gdx.input.isKeyPressed(keyDown);
		}
		
		if (maxSpeed > 0) {
			if (moveDown) {
				velocityY -= acceleration;
				if (velocityY < -maxSpeed)
					velocityY = -maxSpeed;
			}
			if (moveUp) {
				velocityY += acceleration;
				if (velocityY > maxSpeed)
					velocityY = maxSpeed;
			}
			if (!moveUp && !moveDown) {
				if (velocityY > 0) {
					velocityY -= retardation;
					if (velocityY < 0)
						velocityY = 0;
				} else if (velocityY < 0) {
					velocityY += retardation;
					if (velocityY > 0)
						velocityY = 0;
				}
			}
		} else {
			 if (velocityY > 0) {
				velocityY -= retardation;
				if (velocityY < 0)
					velocityY = 0;
			} else if (velocityY < 0) {
				velocityY += retardation;
				if (velocityY > 0)
					velocityY = 0;
			}
		}
			 
		setY(getY() + velocityY * delta);
		
		if (getY() + getHeight() > Settings.LEVEL_MAX_Y) {
			setY(Settings.LEVEL_MAX_Y - getHeight());
		} else if (getY() < Settings.LEVEL_Y) {
			setY(Settings.LEVEL_Y);
		}
		
		
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = new Color(batch.getColor());
		batch.setColor(getColor());
		batch.draw(image, getX(), getY() - getDepth() + getZ(), getWidth(), getHeight() + getDepth());
		batch.setColor(temp);
		super.draw(batch, parentAlpha);
	}
}
