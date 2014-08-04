package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;

public class Paddle extends GameActor {

	private float acceleration, retardation, velocityY;
	public float maxSpeed;
	public final float defaultMaxSpeed;

	public boolean moveUp, moveDown;
	public Rectangle touchRectangle;
	private float touchGraceSize;
	public int keyUp, keyDown;
	private float touchY;

	private TextureRegion topImage, middleImage, bottomImage;
	private float topHeight, middleHeight, bottomHeight;
	private float smallestHeight, largestHeight;
	
	public float actualHeight, actualWidth;

	public Paddle(Player owner) {
		super();
		this.owner = owner;
		owner.paddle = this;

		setColor(new Color(owner.getColor()));

		topImage = Assets.getTextureRegion("paddle_top");
		middleImage = Assets.getTextureRegion("paddle_middle");
		bottomImage = Assets.getTextureRegion("paddle_bottom");
		smallestHeight = 12f * Settings.GAME_SCALE;
		largestHeight = smallestHeight * 4f;

		setDepth(2 * Settings.GAME_SCALE);
		setZ(2 * Settings.GAME_SCALE);
		setWidth(middleImage.getRegionWidth() * Settings.GAME_SCALE);
		topHeight = topImage.getRegionHeight() * Settings.GAME_SCALE;
		bottomHeight = bottomImage.getRegionHeight() * Settings.GAME_SCALE;
		middleHeight = 26 * Settings.GAME_SCALE - topHeight - bottomHeight + getDepth();
		setHeight(topHeight + middleHeight + bottomHeight - getDepth());
		actualWidth = getWidth();
		actualHeight = getHeight();

		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());

		acceleration = 1.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		retardation = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;
		defaultMaxSpeed = maxSpeed = 5.5f * Settings.GAME_FPS * Settings.GAME_SCALE;

		velocityY = 0f;

		touchGraceSize = getHeight() / 8;
		moveUp = false;
		moveDown = false;

		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);

		Groups.paddleGroup.addActor(this);
	}

	@Override
	public void setHeight(float height) {
		actualHeight = height;
		if (height < smallestHeight) {
			height = smallestHeight;
		} else if (height > largestHeight) {
			height = largestHeight;
		}
		middleHeight = height - topHeight - bottomHeight + getDepth();
		float oldHeight = getHeight();
		super.setHeight(height);
		setY(getY() + ((oldHeight - getHeight()) / 2));
	}
	
	public void addHeight(float height) {
		setHeight(actualHeight + height);
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

		Level.getCurrentLevel().checkCollision(this);
	}

	@Override
	public void onHitWall(WallSide side) {
		switch (side) {
		case UP:
			setY(Settings.LEVEL_MAX_Y - getHeight());
			break;
		case DOWN:
			setY(Settings.LEVEL_Y);
			break;
		default:
			break;
		}
	}
	
	private Color temp;

	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = new Color(batch.getColor());
		batch.setColor(getColor());
		drawImages(batch, parentAlpha, 0, 0, 0);
		batch.setColor(temp);
		batch.end();
		batch.begin();
		super.draw(batch, parentAlpha);
	}
	
	public void drawImages(Batch batch, float parentAlpha, float offsetX, float offsetY, float offsetHeight) {
		batch.draw(topImage, getX() + offsetX, getY() + Settings.getLevelYOffset() + getZ() + bottomHeight + middleHeight + offsetY + offsetHeight, getWidth(), topHeight);
		batch.draw(middleImage, getX() + offsetX, getY() + Settings.getLevelYOffset() + getZ() + bottomHeight + offsetY, getWidth(), middleHeight + offsetHeight);
		batch.draw(bottomImage, getX() + offsetX, getY() + Settings.getLevelYOffset() + getZ() + offsetY, getWidth(), bottomHeight);
	}
	
}
