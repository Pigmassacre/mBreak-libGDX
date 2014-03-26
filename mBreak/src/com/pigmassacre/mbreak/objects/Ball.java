package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.SnapshotArray;
import com.pigmassacre.mbreak.Settings;

public class Ball extends GameActor {
	
	private Sound sound;
	
	private boolean collided;
	
	private Player owner;
	
	private Circle circle;
	
	private float damage;
	
	private float angle;
	public float speed, baseSpeed, maxSpeed, tickSpeed, speedStep, speedHandled;
	private float leastAllowedVerticalAngle = 0.32f;
	
	private float traceTime, traceRate;
	
	private Shadow shadow;
	
	public Ball(float x, float y, float angle, Player owner, Color color) {
		super();

		image = getAtlas().findRegion("ball");
		
		sound = Gdx.audio.newSound(Gdx.files.internal("sound/ball.ogg"));
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
		
		circle = new Circle(x, y, getWidth() / 2);
		
		this.angle = angle;
		
		baseSpeed = speed = 1.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		maxSpeed = 10f * Settings.GAME_FPS * Settings.GAME_SCALE;
		speedStep = 0.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		
		this.owner = owner;
		
		traceTime = 0f;
		traceRate = 0.00053f * Settings.GAME_FPS;
		
		setColor(color);
		
		shadow = new Shadow(this, image, false);
		
		damage = 10;
		
		Groups.ballGroup.addActor(this);
	}
	
	@Override
	public void setX(float x) {
		circle.setX(x);
		super.setX(x - circle.radius);
	}
	
	@Override
	public void setY(float y) {
		circle.setY(y);
		super.setY(y - circle.radius);
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
			
			setX((float) (circle.x + (Math.cos(angle) * tickSpeed * delta)));
			setY((float) (circle.y + (Math.sin(angle) * tickSpeed * delta)));
			
			if (circle.x - circle.radius < Settings.LEVEL_X) {
				angle = (float) (Math.PI - angle);
				setX(Settings.LEVEL_X + circle.radius);
				collided = true;
			} else if (circle.x + circle.radius > Settings.LEVEL_MAX_X) {
				angle = (float) (Math.PI - angle);
				setX(Settings.LEVEL_MAX_X - circle.radius);
				collided = true;
			}
			
			if (circle.y - circle.radius < Settings.LEVEL_Y) {
				angle = -angle;
				setY(Settings.LEVEL_Y + circle.radius);
				collided = true;
			} else if (circle.y + circle.radius > Settings.LEVEL_MAX_Y) {
				angle = -angle;
				setY(Settings.LEVEL_MAX_Y - circle.radius);
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
		angle = (float) Math.atan2(circle.y - (paddle.getY() + paddle.getHeight() / 2), circle.x - (paddle.getX() + paddle.getWidth() / 2));
		setX(paddle.rectangle.getX() - circle.radius - 1);
	}
	
	private void hitRightSideOfPaddle(Paddle paddle) {
		angle = (float) Math.atan2(circle.y - (paddle.getY() + paddle.getHeight() / 2), circle.x - (paddle.getX() + paddle.getWidth() / 2));
		setX(paddle.getX() + paddle.getWidth() + circle.radius + 1);
	}
	
	private void checkCollisionPaddles() {
		Paddle paddle;
		float rX, rY, rRadius, bX, bY, bWidth, bHeight;
		
		for (Actor actor : Groups.paddleGroup.getChildren().items) {
			if (actor instanceof Paddle) {
				paddle = (Paddle) actor;
				if (Intersector.overlaps(this.circle, paddle.rectangle)) {
					collided = true;
					speed += maxSpeed / 8;
					rX = this.circle.x;
					rY = this.circle.y;
					rRadius = this.circle.radius * 2;
					
					bX = paddle.rectangle.getX();
					bY = paddle.rectangle.getY();
					bWidth = paddle.rectangle.getWidth();
					bHeight = paddle.rectangle.getHeight();
					
					if (rY - rRadius <= bY + bHeight && rY + rRadius > bY + bHeight) {
						if (bX - (rX - rRadius) > (rY + rRadius) - (bY + bHeight)) {
							hitLeftSideOfPaddle(paddle);
						} else if (rX + rRadius - (bX + bWidth) > (rY + rRadius) - (bY + bHeight)) {
							hitRightSideOfPaddle(paddle);
						} else {
							if (angle > Math.PI)
								angle = -angle;
							
							setY(bY + bHeight + rRadius + 1);
						}
					} else if (rY + rRadius >= bY && (rY - rRadius) < bY) {
						if (bX - (rX - rRadius) > bY - (rY - rRadius)) {
							hitLeftSideOfPaddle(paddle);
						} else if (rX + rRadius - (bX + bWidth) > bY - (rY - rRadius)) {
							hitRightSideOfPaddle(paddle);
						} else {
							if (angle < Math.PI) 
								angle = -angle;
							
							setY(bY - rRadius - 1);
						}
					} else if (rX + rRadius >= bX && (rX - rRadius) < bX) {
						hitLeftSideOfPaddle(paddle);
					} else if (rX - rRadius <= bX + bWidth && rX + rRadius > bX + bWidth) {
						hitRightSideOfPaddle(paddle);
					}
				}
			}
		}
	}
	
	public void checkCollisionBalls() {
		Ball ball;
		double deltaX, deltaY;
		float rX, rY, rRadius, bX, bY, bRadius;
		
		for (Actor actor : Groups.ballGroup.getChildren().items) {
			if (actor instanceof Ball) {
				ball = (Ball) actor;
				if (Intersector.overlaps(ball.circle, this.circle) && ball != this) {
					collided = true;
					rX = this.circle.x;
					rY = this.circle.y;
					rRadius = this.circle.radius;
					
					bX = ball.circle.x;
					bY = ball.circle.y;
					bRadius = ball.circle.radius;
					
					if (rY <= bY && rY > bY) {
						if (bX - rX > rY - bY) {
							setX(bX - rRadius - 1);
						} else if (rX - bX > rY - bY) {
							setX(bX + bRadius + 1);
						} else {
							setY(bY + bRadius + 1);
						}
					} else if (rY >= bY && rY < bY) {
						if (bX - rX > bY - rY) {
							setX(bX - rRadius - 1);
						} else if (rX - bX > bY - rY) {
							setX(bX + bRadius + 1);
						} else {
							setY(bY - rRadius - 1);
						}
					} else if (rX >= bX && rX < bX) {
						setX(bX - rRadius - 1);
					} else if (rX <= bX && rX > bX ) {
						setX(bX + bRadius + 1);
					}

					deltaX = rX - bX;
					deltaY = rY - bY;
					angle = (float) Math.atan2(deltaY, deltaX);

					deltaX = bX - rX;
					deltaY = bY - rY;
					ball.angle = (float) Math.atan2(deltaY, deltaX);
				}
			}
		}
	}
	
	public void checkCollisionBlocks() {
		Block block;
		float rX = this.circle.x, rY = this.circle.y, rRadius = this.circle.radius, bX = 0, bY = 0, bWidth = 0, bHeight = 0;
		
		for (Actor actor : Groups.blockGroup.getChildren().items) {
			if (actor instanceof Block) {
				block = (Block) actor;
				if (Intersector.overlaps(this.circle, block.rectangle)) {
					block.damage(damage);
					collided = true;
					bX = block.rectangle.getX();
					bY = block.rectangle.getY();
					bWidth = block.rectangle.getWidth();
					bHeight = block.rectangle.getHeight();
				}
			}
		}
		if (collided) {
			speed = baseSpeed;
			if (rY - rRadius <= bY + bHeight && rY + rRadius > bY + bHeight) {
				if (bX - (rX - rRadius) > (rY + rRadius) - (bY + bHeight)) {
					setX(bX - rRadius - 1);
					angle = (float) (Math.PI - angle);
				} else if (rX + rRadius - (bX + bWidth) > (rY + rRadius) - (bY + bHeight)) {
					setX(bX + bWidth + rRadius + 1);
					angle = (float) (Math.PI - angle);
				} else {
					setY(bY + bHeight + rRadius + 1);
					angle = -angle;
				}
			} else if (rY + rRadius >= bY && (rY - rRadius) < bY) {
				if (bX - (rX - rRadius) > bY - (rY - rRadius)) {
					setX(bX - rRadius - 1);
					angle = (float) (Math.PI - angle);
				} else if (rX + rRadius - (bX + bWidth) > bY - (rY - rRadius)) {
					setX(bX + bWidth + rRadius + 1);
					angle = (float) (Math.PI - angle);
				} else {
					setY(bY - rRadius - 1);
					angle = -angle;
				}
			} else if (rX + rRadius >= bX && (rX - rRadius) < bX) {
				setX(bX - rRadius - 1);
				angle = (float) (Math.PI - angle);
			} else if (rX - rRadius <= bX + bWidth && rX + rRadius > bX + bWidth) {
				setX(bX + bWidth + rRadius + 1);
				angle = (float) (Math.PI - angle);
			}
		}
		
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setColor(getColor());
		batch.draw(image, circle.x - circle.radius, circle.y - circle.radius, getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}
