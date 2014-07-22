package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.pigmassacre.mbreak.Settings;

public class Particle extends GameActor implements Poolable {
	
	public static final Pool<Particle> particlePool = new Pool<Particle>(250) {

		protected Particle newObject() {
			return new Particle();
		};

	};
	
	private final Color shadowBlendColor = new Color(0.4f, 0.4f, 0.4f, 1.0f);
	
	public float angle, speed, retardation;
	public float alphaStep;
	
	public Particle() {
		alive = false;
		image = Assets.getTextureRegion("particle");
	}
	
	public void init(float x, float y, float width, float height, float angle, float speed, float retardation, float alphaStep, Color color) {
		rectangle = new Rectangle(x, y, width, height);
		setX(x);
		setY(y);
		setZ(3);
		setWidth(width);
		setHeight(height);
		
		this.angle = angle;
		this.speed = speed;
		this.retardation = retardation;
		
		this.alphaStep = alphaStep;
		
		setColor(color);
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, true);
		shadow.setColor(color.cpy().mul(shadowBlendColor));
		
		Groups.particleGroup.addActor(this);
		
		alive = true;
	}

	@Override
	public void reset() {
		if (shadow != null) {
			Shadow.shadowPool.free(shadow);
		}
		alive = false;
		remove();
		clear();
	}
	
	@Override
	public void act(float delta) {
		speed -= retardation * delta;
		if (speed <= 0) {
			particlePool.free(this);
			return;
		}
		
		if (alphaStep > 0) {
			if (getColor().a - alphaStep * delta < 0) {
				particlePool.free(this);
				return;
			} else {
				getColor().a -= alphaStep * delta;
			}
		}
		
		setX(getX() + MathUtils.cos(angle) * speed * delta);
		setY(getY() + MathUtils.sin(angle) * speed * delta);
		
		if (rectangle.x + rectangle.width <= Settings.LEVEL_X
				|| rectangle.x >= Settings.LEVEL_MAX_X
				|| rectangle.y <= Settings.LEVEL_Y
				|| rectangle.y + rectangle.height >= Settings.LEVEL_MAX_Y) {
			particlePool.free(this);
			return;
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (alive) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(image, getX(), getY() + getZ(), getWidth(), getHeight());
			batch.setColor(temp);
			super.draw(batch, parentAlpha);
		}
	}

}
