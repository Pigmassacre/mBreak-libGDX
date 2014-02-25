package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

public class MainMenuScreen extends AbstractScreen {

	ListMenu menu;
	
	public MainMenuScreen(MBreak game) {
		super(game);

		menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		
		TextItem textItem;
		textItem = new TextItem("Start");
		menu.add(textItem);
		stage.addActor(textItem);
		textItem = new TextItem("Options");
		menu.add(textItem);
		stage.addActor(textItem);
		textItem = new TextItem("Help");
		menu.add(textItem);
		stage.addActor(textItem);
		textItem = new TextItem("Quit");
		menu.add(textItem);
		stage.addActor(textItem);
		
		stage.addActor(menu);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}

}
