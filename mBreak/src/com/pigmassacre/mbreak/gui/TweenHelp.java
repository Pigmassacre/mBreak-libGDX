package com.pigmassacre.mbreak.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.Gdx;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

public class TweenHelp {
	
	private static Random random = new Random();

	private static List<String> setupChoices(boolean left, boolean right, boolean up, boolean down) {
		List<String> choices = new ArrayList<String>();
		if (left)
			choices.add("left");
		if (right)
			choices.add("right");
		if (up)
			choices.add("up");
		if (down)
			choices.add("down");
		return choices;
	}
	
	public static void setupSingleItemTween(Item item, TweenManager tweenManager, TweenEquation ease, float duration, boolean left, boolean right, boolean up, boolean down) {
		List<String> choices = setupChoices(left, right, up, down);
		
		String choice = choices.get(random.nextInt(choices.size()));
		if (choice == "left") {
			Tween.from(item, WidgetAccessor.POSITION_X, duration).target(-item.getWidth())
				.ease(ease)
				.start(tweenManager);
		} else if (choice == "right") {
			Tween.from(item, WidgetAccessor.POSITION_X, duration).target(Gdx.graphics.getWidth() + item.getWidth())
				.ease(ease)
				.start(tweenManager);
		} else if (choice == "up") {
			Tween.from(item, WidgetAccessor.POSITION_Y, duration).target(-item.getHeight())
				.ease(ease)
				.start(tweenManager);
		} else if (choice == "down") {
			Tween.from(item, WidgetAccessor.POSITION_Y, duration).target(Gdx.graphics.getHeight() + item.getHeight())
				.ease(ease)
				.start(tweenManager);
		}
	}
	
}
