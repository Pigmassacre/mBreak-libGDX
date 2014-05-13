package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GameActor extends Actor {

	public Player owner;

	private TextureAtlas atlas;
	protected TextureRegion image;

	public Rectangle rectangle;
	protected Shadow shadow;

	protected float stateTime;

	public Group effectGroup;

	public GameActor() {
		rectangle = new Rectangle();
		stateTime = 0f;
		effectGroup = new Group();
	}

	@Override
	public void setX(float x) {
		super.setX(x);
		rectangle.x = x;
	}

	@Override
	public void setY(float y) {
		super.setY(y);
		rectangle.y = y;
	}

	@Override
	public void setWidth(float width) {
		super.setWidth(width);
		rectangle.width = width;
	}

	@Override
	public void setHeight(float height) {
		super.setHeight(height);
		rectangle.height = height;
	}

	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
	}

	public void destroy() {
		remove();
		clear();
		if (shadow != null) {
			shadow.remove();
			shadow.clear();
		}
	}

	public void onHitObject(GameActor object) {
		if (object instanceof Ball) {
			onHitBall((Ball) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitBall((Ball) object);
			}
		} else if (object instanceof Paddle) {
			onHitPaddle((Paddle) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitPaddle((Paddle) object);
			}
		} else if (object instanceof Block) {
			onHitBlock((Block) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitBlock((Block) object);
			}
		}
	}

	public void onHitBall(Ball ball) {
		System.out.println(toString() + ": hit ball: " + ball);
	}

	public void onHitPaddle(Paddle paddle) {
		System.out.println(toString() + ": hit paddle: " + paddle);
	}

	public void onHitBlock(Block block) {
		System.out.println(toString() + ": hit block: " + block);
	}

	public enum WallSide {
		LEFT, RIGHT, UP, DOWN;
	}

	public void onHitWall(WallSide side) {

	}
	
	@Override
	public void act(float delta) {
		effectGroup.act(delta);
	}

}
