package com.pigmassacre.mbreak;

import java.util.ArrayList;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.pigmassacre.mbreak.Item.Command;

public class MainMenuScreen extends AbstractScreen {

	ArrayList<Menu> allMenus;
	ListMenu menu;
	
	Logo logo;
	
	public MainMenuScreen(MBreak game, Logo logo) {
		super(game);
		
		if (logo == null) {
			this.logo = new Logo();
			this.logo.setX((Gdx.graphics.getWidth() - this.logo.getWidth()) / 2);
			this.logo.setY((Gdx.graphics.getHeight() / 2) + this.logo.getHeight() / 2);
		} else {
			this.logo = logo;
		}
		stage.addActor(this.logo);
		Tween.to(this.logo, WidgetAccessor.POSITION_XY, 1.0f).target(this.logo.getX(), (Gdx.graphics.getHeight() / 2) + this.logo.getHeight() / 2)
			.ease(TweenEquations.easeOutBack)
			.start(getTweenManager());

		allMenus = new ArrayList<Menu>();
		
		menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		allMenus.add(menu);
		
		TextItem textItem;
		textItem = new TextItem("Start");
		textItem.selected = true;
		textItem.setFunction(new Command() {

			@Override
			public void execute(Object data) {
				// TODO: Start prepare menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Options");
		textItem.setFunction(new Command() {

			@Override
			public void execute(Object data) {
				// TODO: Start options menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Help");
		textItem.setFunction(new Command() {

			@Override
			public void execute(Object data) {
				// TODO: Start help menu.
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Quit");
		textItem.setFunction(new Command() {

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
	
	@Override
	public void render(float delta) {
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		stage.getCamera().unproject(worldCoordinates);
		super.render(delta);
	}

}
