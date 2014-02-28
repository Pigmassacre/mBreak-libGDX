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

public class MainMenuScreen extends AbstractScreen {

	ArrayList<Menu> allMenus;
	ListMenu menu;
	
	public MainMenuScreen(MBreak game) {
		super(game);

		allMenus = new ArrayList<Menu>();
		
		menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		allMenus.add(menu);
		
		TextItem textItem;
		textItem = new TextItem("Start");
		textItem.selected = true;
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 1.0f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		textItem = new TextItem("Options");
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 1.0f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		textItem = new TextItem("Help");
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 1.0f).target(Gdx.graphics.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		textItem = new TextItem("Quit");
		menu.add(textItem);
		Tween.from(textItem, WidgetAccessor.POSITION_X, 1.0f).target(-textItem.getWidth(), textItem.getY()).ease(TweenEquations.easeOutBack).start(getTweenManager());
		stage.addActor(textItem);
		stage.addActor(menu);
		
	}
	
	@Override
	public void render(float delta) {
		Vector3 worldCoordinates = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
		stage.getCamera().unproject(worldCoordinates);
		Traversal.traverseMenus(worldCoordinates, allMenus);
		super.render(delta);
	}

}
