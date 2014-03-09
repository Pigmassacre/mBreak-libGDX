package com.pigmassacre.mbreak.screens;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Logo;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.gui.Traversal;
import com.pigmassacre.mbreak.gui.WidgetAccessor;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;

public class MainMenuScreen extends AbstractScreen {
	
	Logo logo;
	
	public MainMenuScreen(MBreak game, Logo givenLogo) {
		super(game);
		
		if (givenLogo == null) {
			logo = new Logo();
			logo.setX((Gdx.graphics.getWidth() - logo.getWidth()) / 2);
			logo.setY(Gdx.graphics.getHeight() + logo.getHeight());
		} else {
			logo = givenLogo;
		}
		stage.addActor(logo);
		Tween.to(logo, WidgetAccessor.POSITION_XY, 0.5f).target(logo.getX(), (Gdx.graphics.getHeight() / 2) + logo.getHeight() * 0.75f)
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		Menu menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		traversal.menus.add(menu);
		
		TextItem textItem;
		textItem = new TextItem("Start");
		textItem.selected = true;
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				startPrepareMenu(); 
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Options");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				// TODO: Start options menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Help");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				// TODO: Start help menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Quit");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				quit();
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		stage.addActor(menu);
	}

	public void startPrepareMenu() {
		game.setScreen(new PrepareMenuScreen(game));
	}
	
	public void quit() {
		Gdx.app.exit();
	}
	
	@Override
	public void show() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.BACK:
					for (Menu menu : traversal.menus) {
						if (menu.items.get(menu.items.size() - 1).selected) {
							quit();
							break;
						}
					}
				case Keys.ESCAPE:
					for (Menu menu : traversal.menus) {
						for (Item item : menu.items){
							item.selected = false;
						}
						menu.items.get(menu.items.size() - 1).selected = true;
						break;
					}
					break;
				}
				return false;
			}
			
		});
		super.show();
	}
	
}
