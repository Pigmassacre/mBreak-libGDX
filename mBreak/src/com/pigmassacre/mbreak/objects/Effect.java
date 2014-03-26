package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Effect extends GameActor {

	private Actor parent;
	
	private float duration;
	
	private Rectangle rectangle;
	
	private float stateTime;
	
//	private List<Powerup> connectedPowerups;
	
	public Effect(Actor parent, float duration) {
		this.parent = parent;
		// store player owner also
		this.duration = duration;
		
		stateTime = 0;
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		// store self in parent effect group
		
//		connectedPowerups = new ArrayList<Powerup>();
		
		Groups.effectGroup.addActor(this);
	}
	
	public void onHitBall(Ball ball) {
		
	}
	
	public void onHitBlock(Block block) {
		
	}
	
	public void onHitPaddle(Paddle paddle) {
		
	}
	
	private enum WallSide {
		LEFT, RIGHT, UP, DOWN;
	}
	
	public void onHitWall(WallSide side) {
		
	}
	
	@Override
	public void act(float delta) {
		setX(parent.getX() + ((parent.getWidth() - getWidth()) / 2));
		setY(parent.getY() + ((parent.getHeight() - getHeight()) / 2));
		
		stateTime += delta;
		if (stateTime >= duration)
			destroy();
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (image != null)
			batch.draw(image, getX(), getY(), getWidth(), getHeight());
	}
	
	@Override
	public void destroy() {
		super.destroy();
//		for (Powerup powerup : connectedPowerups)
//			powerup.destroy(false);
		onKill();
	}
	
	public void onKill() {
		
	}
	
}
