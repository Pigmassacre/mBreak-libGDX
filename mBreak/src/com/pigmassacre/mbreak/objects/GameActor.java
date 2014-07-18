package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GameActor extends Actor {

	public Player owner;
	protected GameActor parentActor;

	private TextureAtlas atlas;
	protected TextureRegion image;

	private float z;
	private float depth;
	
	public Rectangle rectangle;
	protected Shadow shadow;

	protected float stateTime;

	public Group effectGroup;
	
	public boolean alive;

	public GameActor() {
		rectangle = new Rectangle();
		stateTime = 0f;
		effectGroup = new Group();
		alive = true;
		setZ(3);
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
	
	public float getZ() {
		return z;
	}
	
	public void setZ(float z) {
		this.z = z;
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
	
	public float getDepth() {
		return depth;
	}
	
	public void setDepth(float depth) {
		this.depth = depth;
	}

	protected TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
	}

	public void destroy() {
		if (shadow != null) {
			Shadow.shadowPool.free(shadow);
		}
		effectGroup.clear();
		alive = false;
		remove();
		clear();
	}

	public void onHitObject(GameActor object) {
		if (object instanceof Ball) {
			onHitBall((Ball) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitObject(object);
//				((GameActor) effect).onHitBall((Ball) object);
			}
		} else if (object instanceof Paddle) {
			onHitPaddle((Paddle) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitObject(object);
//				((GameActor) effect).onHitPaddle((Paddle) object);
			}
		} else if (object instanceof Block) {
			onHitBlock((Block) object);
			for (Actor effect : effectGroup.getChildren()) {
				((GameActor) effect).onHitObject(object);
//				((GameActor) effect).onHitBlock((Block) object);
			}
		}
	}

	public void onHitBall(Ball ball) {
//		System.out.println(toString() + ": hit ball: " + ball);
	}

	public void onHitPaddle(Paddle paddle) {
//		System.out.println(toString() + ": hit paddle: " + paddle);
	}

	public void onHitBlock(Block block) {
//		System.out.println(toString() + ": hit block: " + block);
	}

	public enum WallSide {
		LEFT, RIGHT, UP, DOWN;
	}

	public void onHitWall(WallSide side) {

	}
	
	@Override
	public void act(float delta) {
		move(delta);
		effectGroup.act(delta);
	}
	
	public void move(float delta) {
		
	}

}
