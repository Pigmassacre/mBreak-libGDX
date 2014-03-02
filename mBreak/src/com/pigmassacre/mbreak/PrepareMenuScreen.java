package com.pigmassacre.mbreak;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.pigmassacre.mbreak.Item.ItemCallback;

public class PrepareMenuScreen extends AbstractScreen {

	public PrepareMenuScreen(MBreak game) {
		super(game);
	
		List<Menu> allMenus = new ArrayList<Menu>();
		
		TextItem textItem = new TextItem("Back");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				back();
			}
			
		});
		textItem.selected = true;
		stage.addActor(textItem);
		Menu menu = new ListMenu();
		menu.setX(textItem.getHeight() + textItem.getWidth() / 2);
		menu.setY(textItem.getHeight());
		menu.add(textItem);
		allMenus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, WidgetAccessor.POSITION_XY, 1.0f).target(-textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());
		
		textItem = new TextItem("Start");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				// TODO: START GAME!
			}
			
		});
		stage.addActor(textItem);
		menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() - textItem.getWidth() - textItem.getHeight() + textItem.getWidth() / 2);
		menu.setY(textItem.getHeight());
		menu.add(textItem);
		allMenus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, WidgetAccessor.POSITION_XY, 1.0f).target(Gdx.graphics.getWidth() + textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());
		
		Traversal.menus = allMenus;
	}
	
	public void back() {
		game.setScreen(new MainMenuScreen(game, null));
	}

}
