package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Ball extends Actor {
	
	protected TextureAtlas atlas;
	private TextureRegion image;
	
	private Player owner;
	
	private Rectangle rectangle;
	
	private float angle;
	private float speed, maxSpeed, tickSpeed, speedStep, speedHandled;
	
	public Ball(float x, float y, float angle, Player owner) {
		super();

		image = getAtlas().findRegion("ball"); 
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		setX(x);
		setY(y);
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
		
		this.angle = angle;
		
		speed = 10.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxSpeed = 500f * Settings.GAME_FPS * Settings.GAME_SCALE;
		speedStep = 0.75f * Settings.GAME_FPS * Settings.GAME_SCALE;
		
		this.owner = owner;
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
		
		speedHandled = 0f;
		while (speedHandled < speed) {
			if (speed - speedHandled >= speedStep) {
				tickSpeed = speedStep;
			} else {
				tickSpeed = speed - speedHandled;
			}
			
			setX((float) (getX() + (Math.cos(angle) * tickSpeed * delta)));
			setY((float) (getY() + (Math.sin(angle) * tickSpeed * delta)));
			
			if (speed > maxSpeed)
				speed = maxSpeed;
			
			if (getX() < 0) {
				angle = (float) (Math.PI - angle);
				setX(0);
			} else if (getX() + getWidth() > Gdx.graphics.getWidth()) {
				angle = (float) (Math.PI - angle);
				setX(Gdx.graphics.getWidth() - getWidth());
			}
			
			if (getY() < 0) {
				angle = -angle;
				setY(0);
			} else if (getY() > Gdx.graphics.getHeight()) {
				angle = -angle;
				setY(Gdx.graphics.getHeight() - getHeight());
			}
			
			speedHandled += tickSpeed;
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
	}
	
}
