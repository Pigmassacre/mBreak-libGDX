package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Flash extends Effect {

	private Color startColor, currentColor, finalColor;
	
	private boolean add, followParent = false;
	
	private float tickAmount;
	
	public Flash(GameActor parent, float duration, float tickAmount) {
		super(parent, duration);
		
		this.startColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		this.currentColor = this.startColor;
		this.finalColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
		
		add = finalColor.a > currentColor.a;
		
		image = parent.image;
		
		setX(parent.getX());
		setY(parent.getY());
		setWidth(parent.getWidth());
		setHeight(parent.getHeight());
		
		this.tickAmount = tickAmount;
	}
	
	public Flash(GameActor parent, float duration, float tickAmount, boolean followParent) {
		this(parent, duration, tickAmount);
		this.followParent = followParent;
	}
	
	public Flash(GameActor parent, float duration, Color startColor, Color finalColor, float tickAmount) {
		super(parent, duration);
		
		this.startColor = startColor;
		this.currentColor = this.startColor;
		this.finalColor = finalColor;
		
		add = finalColor.a > currentColor.a;
		
		image = parent.image;
		
		this.tickAmount = tickAmount;
	}
	
	@Override
	public void act(float delta) {
		if (followParent)
			super.act(delta);
		
		if (add) {
			if (currentColor.a + tickAmount * delta < finalColor.a)
				currentColor.a += tickAmount * delta;
			else
				destroy();
		} else {
			if (currentColor.a - tickAmount * delta > finalColor.a)
				currentColor.a -= tickAmount * delta;
			else
				destroy();
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = new Color(batch.getColor());
		batch.setColor(currentColor);
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}
