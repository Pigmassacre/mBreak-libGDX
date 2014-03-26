package com.pigmassacre.mbreak.objects;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.pigmassacre.mbreak.Settings;

public class Block extends GameActor {	

	private Player owner;
	
	public Rectangle rectangle;
	
	private Shadow shadow;
	
	protected float health, maxHealth;
	private Color originalColor;
	
	public Block(float x, float y, Player owner, Color color) {
		super();

		Random random = new Random();
		if (random.nextFloat() <= 0.33)
			image = getAtlas().findRegion("block_normal", 1);
		else if (random.nextFloat() <= 0.66)
			image = getAtlas().findRegion("block_normal", 2);
		else
			image = getAtlas().findRegion("block_normal", 3);
		
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight() * Settings.GAME_SCALE);
		
		rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
		
		setX(x);
		setY(y);
				
		this.owner = owner;
		
		maxHealth = 20;
		health = maxHealth;
		
		setColor(color);
		originalColor = color;
		
		shadow = new Shadow(this, image, false);
		
		Groups.blockGroup.addActor(this);
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
	
	public void damage(float damage) {
		health -= damage;
		getColor().r = originalColor.r * (health / maxHealth);
		getColor().g = originalColor.g * (health / maxHealth);
		getColor().b = originalColor.b * (health / maxHealth);
	}
	
	public void onHit(float damage) {
		damage(damage);
	}
	
	public void destroy() {
		super.destroy();
		shadow.remove();
		shadow.clear();
	}
	
	@Override
	public void act(float delta) {
		if (health <= 0)
			destroy();
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = new Color(batch.getColor());
		batch.setColor(getColor());
		batch.draw(image, getX(), getY(), getWidth(), getHeight());
		batch.setColor(temp);
	}
	
}
