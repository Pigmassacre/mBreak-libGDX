package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.objects.effects.Effect;
import com.pigmassacre.mbreak.objects.powerups.Powerup;

public class Player extends Actor {

	private String name;
	
	private Group currentPowerups;
	
	private float powerupOffset = 2f * Settings.GAME_SCALE;
	
	public Paddle paddle;
	
	public Player(String name) {
		super();
		this.name = name;
		currentPowerups = new Group();
	}
	
	@Override
	public String toString() {
		return "Player: " + name;
	}
	
	public interface PowerupCommand {
		
		public Powerup execute(float x, float y);
		
	}
	
	public void addPowerup(PowerupCommand powerupCommand, Effect effect) {
//		float x, y;
//		if (getX() < Gdx.graphics.getWidth() / 2) {
//			float maxX = getX();
//			for (Actor actor : currentPowerups.getChildren()) {
//				Powerup powerup = (Powerup) actor;
//				if (powerup.getX() + powerup.getWidth() > maxX) {
//					maxX = powerup.getX() + powerup.getWidth() + powerupOffset;
//				}
//			}
//			x = maxX;
//			y = getY();
//		} else {
//			float minX = getX();
//			for (Actor actor : currentPowerups.getChildren()) {
//				Powerup powerup = (Powerup) actor;
//				if (powerup.getX() - powerup.getWidth() - powerupOffset < minX) {
//					minX = powerup.getX() - powerup.getWidth() - powerupOffset;
//				}
//			}
//			x = minX;
//			y = getY();
//		}
//		
//		Powerup powerup = powerupCommand.execute(x, y);
//		effect.connectedPowerups.add(powerup);
//		currentPowerups.addActor(powerup);
	}

	@Override
	public void act(float delta) {
		currentPowerups.act(delta);
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		currentPowerups.draw(batch, parentAlpha);
	}
	
}
