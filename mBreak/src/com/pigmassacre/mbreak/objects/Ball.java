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
	
	private float traceTime, traceRate;
	
	private Shadow shadow;
	
	public Ball(float x, float y, float angle, Player owner, Color color) {
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
		
		traceTime = 0f;
		traceRate = 0.00053f * Settings.GAME_FPS;
		
		setColor(color);
		
		shadow = new Shadow(this, image, false);
		
		Groups.ballGroup.addActor(this);
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
			
			checkCollisionBlocks();
			checkCollisionBalls();
			checkCollisionPaddles();
			
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
			
			if (getX() < Settings.LEVEL_X) {
				angle = (float) (Math.PI - angle);
				setX(Settings.LEVEL_X);
				collided = true;
			} else if (getX() + getWidth() > Settings.LEVEL_MAX_X) {
				angle = (float) (Math.PI - angle);
				setX(Settings.LEVEL_MAX_X - getWidth());
				collided = true;
			}
			
			if (getY() < Settings.LEVEL_Y) {
				angle = -angle;
				setY(Settings.LEVEL_Y);
				collided = true;
			} else if (getY() + getHeight() > Settings.LEVEL_MAX_Y) {
				angle = -angle;
				setY(Settings.LEVEL_MAX_Y - getHeight());
				collided = true;
			}

			if (collided) {
				sound.play();
			}
			
			speedHandled += tickSpeed;
		}
		
		traceTime += delta;
		if (traceTime > traceRate) {
			new Trace(this, this.image);
			traceTime = 0f;
		}
	}
	
	private void hitLeftSideOfPaddle(Paddle paddle) {
		float paddleCenter = paddle.rectangle.getY() + (paddle.rectangle.getHeight() / 2);
		float distanceFromPaddleCenter = paddleCenter - (rectangle.getY() + (rectangle.getHeight() / 2));
		float maxDistance = paddleCenter - (paddle.rectangle.getY() + paddle.rectangle.getHeight() + rectangle.getHeight());
		float normalizedDistance = distanceFromPaddleCenter / maxDistance;
		angle = (float) (Math.PI - normalizedDistance * (Math.PI / 2 - leastAllowedVerticalAngle));
		setX(paddle.rectangle.getX() - rectangle.getWidth() - 1);
	}
	
	private void hitRightSideOfPaddle(Paddle paddle) {
		float paddleCenter = paddle.rectangle.getY() + (paddle.rectangle.getHeight() / 2);
		float distanceFromPaddleCenter = paddleCenter - (rectangle.getY() + (rectangle.getHeight() / 2));
		float maxDistance = paddleCenter - (paddle.rectangle.getY() + paddle.rectangle.getHeight() + rectangle.getHeight());
		float normalizedDistance = distanceFromPaddleCenter / maxDistance;
		angle = (float) (normalizedDistance * (Math.PI / 2 - leastAllowedVerticalAngle));
		setX(paddle.getX() + paddle.getWidth() + 1);
	}
	
	private void checkCollisionPaddles() {
		Paddle paddle;
		float rX, rY, rWidth, rHeight, bX, bY, bWidth, bHeight;
		
		for (Actor actor : Groups.paddleGroup.getChildren().items) {
			if (actor instanceof Paddle) {
				paddle = (Paddle) actor;
				if (Intersector.overlaps(paddle.rectangle, this.rectangle)) {
					collided = true;
					rX = this.rectangle.getX();
					rY = this.rectangle.getY();
					rWidth = this.rectangle.getWidth();
					rHeight = this.rectangle.getHeight();
					
					bX = paddle.rectangle.getX();
					bY = paddle.rectangle.getY();
					bWidth = paddle.rectangle.getWidth();
					bHeight = paddle.rectangle.getHeight();
					
					if (rY <= bY + bHeight && rY + rHeight > bY + bHeight) {
						if (bX - rX > (rY + rHeight) - (bY + bHeight)) {
							hitLeftSideOfPaddle(paddle);
						} else if (rX + rWidth - (bX + bWidth) > (rY + rHeight) - (bY + bHeight)) {
							hitRightSideOfPaddle(paddle);
						} else {
							if (angle > Math.PI)
								angle = -angle;
							
							setY(bY + bHeight + 1);
						}
					} else if (rY + rHeight >= bY && rY < bY) {
						if (bX - rX > bY - rY) {
							hitLeftSideOfPaddle(paddle);
						} else if (rX + rWidth - (bX + bWidth) > bY - rY) {
							hitRightSideOfPaddle(paddle);
						} else {
							if (angle < Math.PI) 
								angle = -angle;
							
							setY(bY - rHeight - 1);
						}
					} else if (rX + rWidth >= bX && rX < bX) {
						hitLeftSideOfPaddle(paddle);
					} else if (rX <= bX + bWidth && rX + rWidth > bX + bWidth) {
						hitRightSideOfPaddle(paddle);
					}
				}
			}
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
	
	public void checkCollisionBlocks() {
		Block block;
		double deltaX, deltaY;
		float rX, rY, rWidth, rHeight, bX, bY, bWidth, bHeight;
		
		for (Actor actor : Groups.blockGroup.getChildren().items) {
			if (actor instanceof Block) {
				block = (Block) actor;
				if (Intersector.overlaps(block.rectangle, this.rectangle)) {
					block.damage(10);
					collided = true;
					rX = this.rectangle.getX();
					rY = this.rectangle.getY();
					rWidth = this.rectangle.getWidth();
					rHeight = this.rectangle.getHeight();
					
					bX = block.rectangle.getX();
					bY = block.rectangle.getY();
					bWidth = block.rectangle.getWidth();
					bHeight = block.rectangle.getHeight();
					
					if (rY <= bY + bHeight && rY + rHeight > bY + bHeight) {
						if (bX - rX > (rY + rHeight) - (bY + bHeight)) {
							setX(bX - rWidth - 1);
							angle = (float) (Math.PI - angle);
						} else if (rX + rWidth - (bX + bWidth) > (rY + rHeight) - (bY + bHeight)) {
							setX(bX + bWidth + 1);
							angle = (float) (Math.PI - angle);
						} else {
							setY(bY + bHeight + 1);
							angle = -angle;
						}
					} else if (rY + rHeight >= bY && rY < bY) {
						if (bX - rX > bY - rY) {
							setX(bX - rWidth - 1);
							angle = (float) (Math.PI - angle);
						} else if (rX + rWidth - (bX + bWidth) > bY - rY) {
							setX(bX + bWidth + 1);
							angle = (float) (Math.PI - angle);
						} else {
							setY(bY - rHeight - 1);
							angle = -angle;
						}
					} else if (rX + rWidth >= bX && rX < bX) {
						setX(bX - rWidth - 1);
						angle = (float) (Math.PI - angle);
					} else if (rX <= bX + bWidth && rX + rWidth > bX + bWidth) {
						setX(bX + bWidth + 1);
						angle = (float) (Math.PI - angle);
					}
				}
			}
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}
