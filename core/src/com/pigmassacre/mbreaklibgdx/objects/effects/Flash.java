package com.pigmassacre.mbreaklibgdx.objects.effects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.pigmassacre.mbreaklibgdx.Settings;
import com.pigmassacre.mbreaklibgdx.objects.GameActor;
import com.pigmassacre.mbreaklibgdx.objects.GrayscaleShader;
import com.pigmassacre.mbreaklibgdx.objects.Paddle;

public class Flash extends Effect {

	private Color currentColor, finalColor;
	
	private boolean add;
	private boolean followParent = false;
	
	public Flash(GameActor parentActor, float duration) {
		super(parentActor, duration);
		
		this.currentColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
		this.finalColor = new Color(1.0f, 1.0f, 1.0f, 0.0f);
		
		add = finalColor.a > currentColor.a;
		
		setDepth(parentActor.getDepth());
		setX(parentActor.getX());
		setY(parentActor.getY());
		setZ(parentActor.getZ());
		setWidth(parentActor.getWidth());
		setHeight(parentActor.getHeight());
	}
	
	public Flash(GameActor parentActor, float duration, boolean followParent) {
		this(parentActor, duration);
		this.followParent = followParent;
	}
	
	public Flash(GameActor parentActor, float duration, Color startColor, Color finalColor) {
		super(parentActor, duration);
		
		this.currentColor = startColor;
		this.finalColor = finalColor;
		
		add = finalColor.a > currentColor.a;
	}
	
	@Override
	public void act(float delta) {
		if (followParent) {
			super.act(delta);
			setWidth(parentActor.getWidth());
			setHeight(parentActor.getHeight());
		}
		
		if (add) {
			if (currentColor.a + (delta / duration) < finalColor.a) {
				currentColor.a += delta / duration;
			} else {
				destroy();
			}
		} else {
			if (currentColor.a - (delta / duration) > finalColor.a) {
				currentColor.a -= delta / duration;
			} else {
				destroy();
			}
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = batch.getColor();
		batch.setShader(GrayscaleShader.grayscaleShader);
		batch.setColor(currentColor);
		if (followParent) {
			if (parentActor instanceof Paddle) {
				Paddle paddle = (Paddle) parentActor;
				paddle.drawImages(batch, parentAlpha, 0, 0, 0);
			} else {
				batch.draw(parentActor.getImage(), parentActor.getX(), parentActor.getY() + Settings.getLevelYOffset() + parentActor.getZ(), parentActor.getWidth(), parentActor.getHeight() + parentActor.getDepth());
			}
		} else {
			batch.draw(parentActor.getImage(), getX(), getY() + Settings.getLevelYOffset() + getZ(), getWidth(), getHeight() + getDepth());
		}
		batch.setColor(temp);
		batch.setShader(null);
	}
	
}
