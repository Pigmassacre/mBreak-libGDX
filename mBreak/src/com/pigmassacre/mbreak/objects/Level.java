package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.pigmassacre.mbreak.Assets;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.GameActor.DestroyCallback;
import com.pigmassacre.mbreak.objects.powerups.ElectricityPowerup;
import com.pigmassacre.mbreak.objects.powerups.EnlargerPowerup;
import com.pigmassacre.mbreak.objects.powerups.FirePowerup;
import com.pigmassacre.mbreak.objects.powerups.FrostPowerup;
import com.pigmassacre.mbreak.objects.powerups.MultiballPowerup;
import com.pigmassacre.mbreak.objects.powerups.Powerup;
import com.pigmassacre.mbreak.objects.powerups.ReducerPowerup;
import com.pigmassacre.mbreak.objects.powerups.SpeedPowerup;

public class Level extends Actor {

	private static Level currentLevel;
	private Background background;
	private Foreground foreground;
	
	private TextureRegion backgroundImage, horizontalWallTop;
	private TextureRegion verticalWallLeft, verticalWallRight, horizontalWallBottom;
	private TextureRegion topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner;
	
	public Array<Vector2> powerupSpawnPositions = new Array<Vector2>();
	private float powerupSpawnStartTime = 2f;
	private float powerupSpawnWaitTime = 2f;

	public static Level getCurrentLevel() {
		return currentLevel;
	}
	
	public static void setCurrentLevel(String id) {
		currentLevel = new Level(id);
	}
	
	private Level(String id) {
		backgroundImage = Assets.getTextureRegion(id + "/floor");
		
		verticalWallLeft = Assets.getTextureRegion(id + "/wall_vertical_left");
		verticalWallRight = Assets.getTextureRegion(id + "/wall_vertical_right");
		horizontalWallTop = Assets.getTextureRegion(id + "/wall_horizontal_top");
		horizontalWallBottom = Assets.getTextureRegion(id + "/wall_horizontal_bottom");
		topLeftCorner = Assets.getTextureRegion(id + "/corner_top_left");
		bottomLeftCorner = Assets.getTextureRegion(id + "/corner_bottom_left");
		topRightCorner = Assets.getTextureRegion(id + "/corner_top_right");
		bottomRightCorner = Assets.getTextureRegion(id + "/corner_bottom_right");
		
		setX(Settings.LEVEL_X - (Settings.LEVEL_WIDTH - backgroundImage.getRegionWidth() * Settings.GAME_SCALE));
		setY(Settings.LEVEL_Y + Settings.getLevelYOffset() - (Settings.LEVEL_HEIGHT - backgroundImage.getRegionHeight() * Settings.GAME_SCALE));
		setWidth(Settings.LEVEL_WIDTH);
		setHeight(Settings.LEVEL_HEIGHT);
		
		background = new Background();
		foreground = new Foreground();
		
		SpeedPowerup powerup = new SpeedPowerup(0, 0); 
		powerupSpawnPositions = new Array<Vector2>();
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() / 2 - powerup.getWidth() / 2, getY() + getHeight() / 2 - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() * 0.35f - powerup.getWidth() / 2, getY() + getHeight() * 0.8f - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() / 2 - powerup.getWidth() / 2, getY() + getHeight() * 0.9f - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() * 0.65f - powerup.getWidth() / 2, getY() + getHeight() * 0.8f - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() * 0.35f - powerup.getWidth() / 2, getY() + getHeight() * 0.2f - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() / 2 - powerup.getWidth() / 2, getY() + getHeight() * 0.1f - powerup.getHeight() / 2));
		powerupSpawnPositions.add(new Vector2(getX() + getWidth() * 0.65f - powerup.getWidth() / 2, getY() + getHeight() * 0.2f - powerup.getHeight() / 2));
		powerup.destroy();
		
		Timer.instance().scheduleTask(new Task() {
			
			@Override
			public void run() {
				Level.getCurrentLevel().spawnPowerup();
				Timer.instance().scheduleTask(getPowerupTask(), powerupSpawnWaitTime);
			}
			
		}, powerupSpawnStartTime);
	}
	
	private Task getPowerupTask() {
		return new Task() {
			
			@Override
			public void run() {
				Level.getCurrentLevel().spawnPowerup();
				Timer.instance().scheduleTask(getPowerupTask(), powerupSpawnWaitTime);
			}
			
		};
	}
	
	public void spawnPowerup() {
		if (powerupSpawnPositions.size > 0) {
			Vector2 pos = powerupSpawnPositions.get(MathUtils.random(powerupSpawnPositions.size - 1));
			powerupSpawnPositions.removeValue(pos, false);
			Powerup powerup;
			switch(MathUtils.random(6)) {
			case 0:
				powerup = new FirePowerup(pos.x, pos.y);
				break;
			case 1:
				powerup = new SpeedPowerup(pos.x, pos.y);
				break;
			case 2:
				powerup = new ElectricityPowerup(pos.x, pos.y);
				break;
			case 3:
				powerup = new FrostPowerup(pos.x, pos.y);
				break;
			case 4:
				powerup = new EnlargerPowerup(pos.x, pos.y);
				break;
			case 5:
				powerup = new ReducerPowerup(pos.x, pos.y);
				break;
			case 6:
				powerup = new MultiballPowerup(pos.x, pos.y);
				break;
			default:
				powerup = new FirePowerup(pos.x, pos.y);
				break;	
			}
			powerup.setDestroyCallback(new DestroyCallback() {
				
				@Override
				public void execute(GameActor actor, Object data) {
					Level.getCurrentLevel().powerupSpawnPositions.add((Vector2) data);
				}
				
			}, pos);
		}
	}

	public Background getBackground() {
		return background;
	}
	
	public Foreground getForeground() {
		return foreground;
	}
	
	private class Background extends Actor {
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(horizontalWallTop, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), horizontalWallTop.getRegionWidth() * Settings.GAME_SCALE, horizontalWallTop.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(backgroundImage, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY(), backgroundImage.getRegionWidth() * Settings.GAME_SCALE, backgroundImage.getRegionHeight() * Settings.GAME_SCALE);
		}
		
	}
	
	private class Foreground extends Actor {
		
		@Override
		public void draw(Batch batch, float parentAlpha) {
			batch.draw(topLeftCorner, Level.getCurrentLevel().getX() - topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(topRightCorner, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY() + Level.getCurrentLevel().getHeight(), topLeftCorner.getRegionWidth() * Settings.GAME_SCALE, topLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(verticalWallLeft, Level.getCurrentLevel().getX() - verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY(), verticalWallLeft.getRegionWidth() * Settings.GAME_SCALE, verticalWallLeft.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(verticalWallRight, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY(), verticalWallRight.getRegionWidth() * Settings.GAME_SCALE, verticalWallRight.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(bottomLeftCorner, Level.getCurrentLevel().getX() - bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(bottomRightCorner, Level.getCurrentLevel().getX() + Level.getCurrentLevel().getWidth(), Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, bottomLeftCorner.getRegionWidth() * Settings.GAME_SCALE, bottomLeftCorner.getRegionHeight() * Settings.GAME_SCALE);
			batch.draw(horizontalWallBottom, Level.getCurrentLevel().getX(), Level.getCurrentLevel().getY() - 1 * Settings.GAME_SCALE, horizontalWallBottom.getRegionWidth() * Settings.GAME_SCALE, horizontalWallBottom.getRegionHeight() * Settings.GAME_SCALE);
		}
		
	}
	
	public boolean checkCollision(GameActor actor) {
		if (actor instanceof Ball) {
			return checkCollisionBall((Ball) actor);
		}
		return checkCollisionGameActor(actor);
	}
	
	private boolean checkCollisionBall(Ball ball) {
		boolean hit = false;
		if (ball.circle.x - ball.circle.radius < Settings.LEVEL_X) {
			ball.onHitWall(GameActor.WallSide.LEFT);
			hit = true;
		} else if (ball.circle.x + ball.circle.radius > Settings.LEVEL_MAX_X) {
			ball.onHitWall(GameActor.WallSide.RIGHT);
			hit = true;
		}
		if (ball.circle.y - ball.circle.radius < Settings.LEVEL_Y) {
			ball.onHitWall(GameActor.WallSide.DOWN);
			hit = true;
		} else if (ball.circle.y + ball.circle.radius > Settings.LEVEL_MAX_Y) {
			ball.onHitWall(GameActor.WallSide.UP);
			hit = true;
		}
		return hit;
	}
	
	private boolean checkCollisionGameActor(GameActor actor) {
		boolean hit = false;
		if (actor.rectangle.x < Settings.LEVEL_X) {
			actor.onHitWall(GameActor.WallSide.LEFT);
			hit = true;
		} else if (actor.rectangle.x + actor.rectangle.width > Settings.LEVEL_MAX_X) {
			actor.onHitWall(GameActor.WallSide.RIGHT);
			hit = true;
		}
		if (actor.rectangle.y < Settings.LEVEL_Y) {
			actor.onHitWall(GameActor.WallSide.DOWN);
			hit = true;
		} else if (actor.rectangle.y + actor.rectangle.height > Settings.LEVEL_MAX_Y) {
			actor.onHitWall(GameActor.WallSide.UP);
			hit = true;
		}
		return hit;
	}
	
}
