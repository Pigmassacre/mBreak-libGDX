package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pigmassacre.mbreak.Settings;

public class Ball extends Actor {
	
	private TextureAtlas atlas;
	private TextureRegion image;
	
	private Sound sound;
	
	private boolean collided;
	
	private Player owner;
	
	private Rectangle rectangle;
	
	private float angle;
	public float speed, maxSpeed, tickSpeed, speedStep, speedHandled;
	private float leastAllowedVerticalAngle = 0.32f;
	
	public Ball(float x, float y, float angle, Player owner) {
		super();

		image = getAtlas().findRegion("ball");
		
		sound = Gdx.audio.newSound(Gdx.files.internal("sound/ball.ogg"));
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		setX(x);
		setY(y);
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
		
		rectangle.setWidth(getWidth());
		rectangle.setHeight(getHeight());
		
		this.angle = angle;
		
		speed = 1.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxSpeed = 5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		speedStep = 0.75f * Settings.GAME_FPS * Settings.GAME_SCALE;
		
		this.owner = owner;
		
		Groups.ballGroup.addActor(this);
		System.out.println(Groups.ballGroup.getChildren().toString());
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
			
			collided = false;
			
			checkCollisionBalls();
			
			if (angle > (Math.PI / 2) - leastAllowedVerticalAngle && angle < Math.PI / 2) {
				angle = (float) ((Math.PI / 2) - leastAllowedVerticalAngle);
			} else if (angle < (Math.PI / 2) + leastAllowedVerticalAngle && angle > Math.PI / 2) {
				angle = (float) ((Math.PI / 2) + leastAllowedVerticalAngle);
			} else if (angle > ((3 * Math.PI) / 2) - leastAllowedVerticalAngle && angle < ((3 * Math.PI) / 2)) {
				angle = (float) (((3 * Math.PI) / 2) - leastAllowedVerticalAngle);
			} else if (angle < ((3 * Math.PI) / 2) + leastAllowedVerticalAngle && angle > ((3 * Math.PI) / 2)) {
				angle = (float) (((3 * Math.PI) / 2) + leastAllowedVerticalAngle);
			} else if (angle == Math.PI / 2) {
				angle = (float) ((Math.PI / 2) - leastAllowedVerticalAngle);
			} else if (angle == (3 * Math.PI) / 2) {
				angle = (float) (((3 * Math.PI) / 2) + leastAllowedVerticalAngle);
			}
			
			if (angle > 2 * Math.PI) {
				angle -= 2 * Math.PI;
			} else if (angle < 0) {
				angle += 2 * Math.PI;
			}
			
			if (speed > maxSpeed)
				speed = maxSpeed;
			
			setX((float) (getX() + (Math.cos(angle) * tickSpeed * delta)));
			setY((float) (getY() + (Math.sin(angle) * tickSpeed * delta)));
			
			if (getX() < 0) {
				angle = (float) (Math.PI - angle);
				setX(0);
				collided = true;
			} else if (getX() + getWidth() > Gdx.graphics.getWidth()) {
				angle = (float) (Math.PI - angle);
				setX(Gdx.graphics.getWidth() - getWidth());
				collided = true;
			}
			
			if (getY() < 0) {
				angle = -angle;
				setY(0);
				collided = true;
			} else if (getY() + getHeight() > Gdx.graphics.getHeight()) {
				angle = -angle;
				setY(Gdx.graphics.getHeight() - getHeight());
				collided = true;
			}

			if (collided) {
				sound.play();
			}
			
			speedHandled += tickSpeed;
		}
	}
	
	public void checkCollisionBalls() {
		Ball ball;
		double deltaX, deltaY;
		float rX, rY, rWidth, rHeight, bX, bY, bWidth, bHeight;
		
		for (Actor actor : Groups.ballGroup.getChildren().items) {
			if (actor instanceof Ball) {
				ball = (Ball) actor;
				if (Intersector.overlaps(ball.rectangle, this.rectangle) && ball != this) {
					collided = true;
					rX = this.rectangle.getX();
					rY = this.rectangle.getY();
					rWidth = this.rectangle.getWidth();
					rHeight = this.rectangle.getHeight();
					
					bX = ball.rectangle.getX();
					bY = ball.rectangle.getY();
					bWidth = ball.rectangle.getWidth();
					bHeight = ball.rectangle.getHeight();
					
					if (rY <= bY + bHeight && rY + rHeight > bY + bHeight) {
						if (bX - rX > (rY + rHeight) - (bY + bHeight)) {
							setX(bX - rWidth - 1);
						} else if (rX + rWidth - (bX + bWidth) > (rY + rHeight) - (bY + bHeight)) {
							setX(bX + bWidth + 1);
						} else {
							setY(bY + bHeight + 1);
						}
					} else if (rY + rHeight >= bY && rY < bY) {
						if (bX - rX > bY - rY) {
							setX(bX - rWidth - 1);
						} else if (rX + rWidth - (bX + bWidth) > bY - rY) {
							setX(bX + bWidth + 1);
						} else {
							setY(bY - rHeight - 1);
						}
					} else if (rX + rWidth >= bX && rX < bX) {
						setX(bX - rWidth - 1);
					} else if (rX <= bX + bWidth && rX + rWidth > bX + bWidth) {
						setX(bX + bWidth + 1);
					}

					deltaX = rX + (rWidth / 2) - (bX + (bWidth / 2));
					deltaY = rY + (rHeight / 2) - (bY + (bHeight / 2));
					angle = (float) Math.atan2(deltaY, deltaX);

					deltaX = bX + (bWidth / 2) - (rX + (rWidth / 2));
					deltaY = bY + (bHeight / 2) - (rY + (rHeight / 2));
					ball.angle = (float) Math.atan2(deltaY, deltaX);
				}
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		Color c = new Color(batch.getColor());
		batch.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(c);
	}
	
}
