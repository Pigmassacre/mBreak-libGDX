package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.InputAdapter;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.MusicHandler;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;

public class PauseScreen extends AbstractScreen {
	
	AbstractScreen pausedScreen;
	
	public PauseScreen(MBreak game, AbstractScreen pausedScreen) {
		super(game);
		Gdx.input.setCursorCatched(false);
		this.pausedScreen = pausedScreen;
		
		Menu menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(Gdx.graphics.getHeight() / 2);
		traversal.menus.add(menu);
		
		TextItem textItem;
		textItem = new TextItem("Resume");
		textItem.setSelected(true);
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Item data) {
				back(); 
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, ActorAccessor.POSITION_X, 0.75f).target(Gdx.graphics.getWidth()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		textItem = new TextItem("Quit");
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Item data) {
				quit();
			}
			
		});
		menu.add(textItem);
		Tween.from(textItem, ActorAccessor.POSITION_X, 0.75f).target(-textItem.getWidth()).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		
		menu.setY((Gdx.graphics.getHeight() + textItem.getHeight()) / 2);
		menu.cleanup();
		
		stage.addActor(menu);
	}
	
	@Override
	protected void registerInputProcessors() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
				case Keys.BACK:
					game.setScreen(pausedScreen);
					break;
				}
				return false;
			}
			
		});
	}
	
	private void back() {
		game.setScreen(pausedScreen);
	}

	private void quit() {
		pausedScreen.dispose();
		game.setScreen(new MainMenuScreen(game));
	}
	
	@Override
	public void render(float delta) {
		pausedScreen.draw(0);
		super.render(delta);
	}
	
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	@Override
	public void renderClearScreen(float delta) {
		// So we don't clear the screen each frame, we let the pausdScreens .draw() method do that.
		Gdx.gl.glEnable(GL20.GL_BLEND);
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		shapeRenderer.end();
		Gdx.gl.glDisable(GL20.GL_BLEND);
	}
	
	private float oldVolume;
	
	@Override
	public void show() {
		super.show();
		oldVolume = MusicHandler.getVolume();
		MusicHandler.setVolume(0.25f);
		Timer.instance().stop();
	}
	
	@Override
	public void hide() {
		super.hide();
		MusicHandler.setVolume(oldVolume);
		Timer.instance().start();
		dispose();
	}
	
}
