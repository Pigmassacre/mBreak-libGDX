package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.TextItem;

public class CustomizeScreen extends AbstractScreen {

	private AbstractScreen prepareMenuScreen;
	
	Item back;
	
	public CustomizeScreen(MBreak game, AbstractScreen prepareMenuScreen) {
		super(game);
		
		this.prepareMenuScreen = prepareMenuScreen;

		TextItem textItem = new TextItem("Back");
		back = textItem;
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Item data) {
				back();
			}
			
		});
		textItem.setSelected(true);
		stage.addActor(textItem);
		Menu menu = new ListMenu();
		menu.setX(textItem.getHeight() + textItem.getWidth() / 2f);
		menu.setY(textItem.getHeight());
		menu.add(textItem);
		traversal.menus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, ActorAccessor.POSITION_XY, 0.5f).target(-textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
	}

	protected void registerInputProcessors() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
				case Keys.BACK:
					back();
					break;
				}
				return false;
			}
			
		});
	};
	
	public void back() {
		game.setScreen(prepareMenuScreen);
		dispose();
	}
	
}
