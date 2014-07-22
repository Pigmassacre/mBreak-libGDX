package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.pigmassacre.mbreak.Settings;

public class Block extends GameActor {	

	protected float health, maxHealth;
	private Color originalColor;
	
	public Block(float x, float y, Player owner, Color color) {
		super();
		
		image = Assets.getTextureRegion("block");
		
		setDepth(2 * Settings.GAME_SCALE);
		
		setX(x);
		setY(y);
		setWidth(image.getRegionWidth() * Settings.GAME_SCALE);
		setHeight(image.getRegionHeight()* Settings.GAME_SCALE - getDepth());
		
		rectangle.set(getX(), getY(), getWidth(), getHeight());
				
		this.owner = owner;
		
		maxHealth = 20;
		health = maxHealth;
		
		setColor(color);
		originalColor = color;
		
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, false);
		
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

//	public float startTime = MathUtils.random() * 100000f;
	
	@Override
	public void act(float delta) {
		super.act(delta);
		
//		stateTime += delta;
//		setZ(((MathUtils.sin(startTime + stateTime * 10) + 1) / 2) * 5); 
		
		if (health <= 0) {
			destroy();
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		temp = new Color(batch.getColor());
		batch.setColor(getColor());
		batch.draw(image, getX(), getY() - getDepth() + getZ(), getWidth(), getHeight() + getDepth());
		batch.setColor(temp);
	}
	
}
