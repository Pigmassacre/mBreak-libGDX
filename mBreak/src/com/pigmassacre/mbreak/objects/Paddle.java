package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.pigmassacre.mbreak.Settings;

public class Paddle extends Actor {

	private TextureAtlas atlas;
	private TextureRegion image;
	
	private Player owner;
	
	public Rectangle rectangle;
	private float maxHeight, minHeight, maxWidth, minWidth;
	
	private float acceleration, retardation, maxSpeed, velocityX, velocityY;
	
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
		
		image = getAtlas().findRegion("paddle");
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		acceleration = 3.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		retardation = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxSpeed = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		
		velocityY = 0f;
		
		stabilizeSpeed = 0.1f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxNudgeDistance = 2.5f * Settings.GAME_SCALE;
		
		touchGraceSize = getHeight() / 8;
		moveUp = false;
		moveDown = false;
		
		new Shadow(this, this.image, false);
		
		Groups.paddleGroup.addActor(this);
	}
	
	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
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
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(temp);
	}
}
