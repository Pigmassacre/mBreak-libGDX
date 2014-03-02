package com.pigmassacre.mbreak;

import java.util.ArrayList;
import java.util.List;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pigmassacre.mbreak.Item.ItemCallback;

public class MainMenuScreen extends AbstractScreen {
	
	Logo logo;
	
	public MainMenuScreen(MBreak game, Logo givenLogo) {
		super(game);
		
		if (givenLogo == null) {
			logo = new Logo();
			logo.setX((Gdx.graphics.getWidth() - logo.getWidth()) / 2);
			logo.setY((Gdx.graphics.getHeight() / 2) + logo.getHeight() * 0.75f);
		} else {
			logo = givenLogo;
		}
		stage.addActor(logo);
		Tween.to(logo, WidgetAccessor.POSITION_XY, 1.0f).target(logo.getX(), (Gdx.graphics.getHeight() / 2) + logo.getHeight() * 0.75f)
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());

		List<Menu> allMenus = new ArrayList<Menu>();
		
		Menu menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		allMenus.add(menu);
		
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
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Options");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				// TODO: Start options menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Help");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				// TODO: Start help menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Quit");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Object data) {
				Gdx.app.exit();
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		stage.addActor(menu);
		
		Traversal.menus = allMenus;
	}

	public void startPrepareMenu() {
		game.setScreen(new PrepareMenuScreen(game));
	}
	
}
