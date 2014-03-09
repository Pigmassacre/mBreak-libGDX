package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Trace extends Actor {

	private Actor parent;
	private Color color;
	private TextureRegion image;
	
	private float alphaStep = 0.06f * Settings.GAME_FPS;
	
	public Trace(Actor parent, TextureRegion image) {
		super();
		this.parent = parent;
		this.color = new Color(parent.getColor());
		this.image = image;
		
		setX(parent.getX());
		setY(parent.getY());
		setWidth(parent.getWidth());
		setHeight(parent.getHeight());
		
		Groups.traceGroup.addActor(this);
	}

	@Override
	public void act(float delta) {
		if (alphaStep > 0) {
			if (color.a - (alphaStep * delta) < 0) {
				remove();
				clear();
			} else {
				color.a -= alphaStep * delta;
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		System.out.println("drawing");
		Color c = new Color(batch.getColor());
		batch.setColor(color);
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(c);
	}
	
}
