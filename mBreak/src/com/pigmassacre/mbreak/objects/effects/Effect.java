package com.pigmassacre.mbreak.objects.effects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.pigmassacre.mbreak.objects.Ball;
import com.pigmassacre.mbreak.objects.Block;
import com.pigmassacre.mbreak.objects.GameActor;
import com.pigmassacre.mbreak.objects.Groups;
import com.pigmassacre.mbreak.objects.Paddle;
import com.pigmassacre.mbreak.objects.Player;

public class Effect extends GameActor {

	protected GameActor parent;
	protected Player realOwner;
	
	private float duration;
	
	private float stateTime;
	
//	private List<Powerup> connectedPowerups;
	
	public Effect(Effect effect) {
		this(effect.parent, effect.duration);
	}
	
	public Effect(GameActor parent, float duration) {
		this.parent = parent;
		this.realOwner = this.parent.owner;
		this.duration = duration;
		
		stateTime = 0;
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		parent.effectGroup.addActor(this);
		
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
	
	public Effect clone() {
		return new Effect(this);
	}
	
}
