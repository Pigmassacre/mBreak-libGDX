package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.pigmassacre.mbreak.Settings;

public class Particle extends GameActor implements Poolable {
	
	public static final Pool<Particle> particlePool = new Pool<Particle>() {

		protected Particle newObject() {
			return new Particle();
		};

	};
	
	private final Color SHADOW_BLEND_COLOR = new Color(0.4f, 0.4f, 0.4f, 1.0f);
	
	public float angle, speed, retardation;
	public float alphaStep;
	
//	public boolean alive;
	
	private static TextureRegion image = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas")).findRegion("particle");
	
	public Particle() {
		alive = false;
	}
	
	public void init(float x, float y, float width, float height, float angle, float speed, float retardation, float alphaStep, Color color) {
		rectangle = new Rectangle(x, y, width, height);
		super.image = image;
		setX(x);
		setY(y);
		setWidth(width);
		setHeight(height);
		
		this.angle = angle;
		this.speed = speed;
		this.retardation = retardation;
		
		this.alphaStep = alphaStep;
		
		setColor(color);
		shadow = Shadow.shadowPool.obtain();
		shadow.init(this, true);
		shadow.setColor(color.cpy().mul(SHADOW_BLEND_COLOR));
		shadow.getColor().a = 0.5f;
		
		Groups.particleGroup.addActor(this);
		
		alive = true;
	}

	@Override
	public void reset() {
		speed = 0;
		setX(0);
		setY(0);
		alive = false;
		remove();
	}
	
	@Override
	public void act(float delta) {
		speed -= retardation * delta;
		if (speed <= 0) {
			particlePool.free(this);
		}
		
		if (alphaStep > 0) {
			if (getColor().a - alphaStep * delta < 0) {
				particlePool.free(this);
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
		}
	}
	
	private Color temp;
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (alive) {
			temp = batch.getColor();
			batch.setColor(getColor());
			batch.draw(image, getX(), getY(), getWidth(), getHeight());
			batch.setColor(temp);
		}
	}

}
