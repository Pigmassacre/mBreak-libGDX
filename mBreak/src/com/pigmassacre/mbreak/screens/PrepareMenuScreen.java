package com.pigmassacre.mbreak.screens;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.DebugInput;
import com.pigmassacre.mbreak.gui.GridMenu;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.RectItem;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.gui.Traversal;
import com.pigmassacre.mbreak.gui.TweenHelp;
import com.pigmassacre.mbreak.gui.WidgetAccessor;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;
import com.pigmassacre.mbreak.objects.Groups;

public class PrepareMenuScreen extends AbstractScreen {

	public PrepareMenuScreen(MBreak game) {
		super(game);

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
		traversal.menus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, WidgetAccessor.POSITION_XY, 0.5f).target(-textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		textItem = new TextItem("Start");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				start();
			}
			
		});
		stage.addActor(textItem);
		menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() - textItem.getWidth() - textItem.getHeight() + textItem.getWidth() / 2);
		menu.setY(textItem.getHeight());
		menu.add(textItem);
		traversal.menus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, WidgetAccessor.POSITION_XY, 0.5f).target(Gdx.graphics.getWidth() + textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		GridMenu colorMenu = new GridMenu(3);
		RectItem colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 1.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		
		colorMenu.setX(colorMenu.getWidth());
		colorMenu.setY(colorMenu.getHeight() * 2);
		colorMenu.cleanup();

		for (Item item : colorMenu.items) {
			TweenHelp.setupSingleItemTween(item, getTweenManager(), TweenEquations.easeOutExpo, 0.5f, true, true, true, true);
		}
		
		traversal.menus.add(colorMenu);
		stage.addActor(colorMenu);
		
		colorMenu = new GridMenu(3);
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 1.0f, 0.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 1.0f, 1.0f);
		stage.addActor(colorItem);
		colorMenu.add(colorItem);
		
		colorMenu.setX(Gdx.graphics.getWidth() - 2 * colorMenu.getWidth());
		colorMenu.setY(colorMenu.getHeight() * 2);
		colorMenu.cleanup();

		for (Item item : colorMenu.items) {
			TweenHelp.setupSingleItemTween(item, getTweenManager(), TweenEquations.easeOutExpo, 0.5f, true, true, true, true);
		}
		
		traversal.menus.add(colorMenu);
		stage.addActor(colorMenu);
	}
	
	public void back() {
		game.setScreen(new MainMenuScreen(game, null));
	}
	
	public void start() {
		game.setScreen(new GameScreen(game));
	}
	
	@Override
	public void show() {
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
		super.show();
	}

}
