package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.MusicHandler;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;

public class ToastScreen extends AbstractScreen {
	
	private final AbstractScreen pausedScreen;
	
	public ToastScreen(MBreak game, AbstractScreen pausedScreen, String message) {
		super(game);
		this.pausedScreen = pausedScreen;
		
		TextItem textItem;
		textItem = new TextItem(message);
		textItem.setAlignment(HAlignment.CENTER);
		textItem.setWrapped(true);
		textItem.setColor(0.9f, 0.25f, 0.25f, 1f);
		textItem.setX((Gdx.graphics.getWidth() - textItem.getWrapWidth()) / 2f);
		textItem.setY((Gdx.graphics.getHeight()) / 2 + textItem.getHeight() / 2f);
		stage.addActor(textItem);

		Menu menu = new ListMenu();
		menu.setX(Gdx.graphics.getWidth() / 2);
		menu.setY(textItem.getY() - textItem.getHeight() * 1.5f);
		traversal.menus.add(menu);
		
		textItem = new TextItem("Ok");
		textItem.setSelected(true);
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Item data) {
				back(); 
			}
			
		});
		
		Tween.from(textItem, ActorAccessor.POSITION_Y, 0.75f).target(0).ease(TweenEquations.easeOutExpo).start(getTweenManager());
		stage.addActor(textItem);
		menu.add(textItem);
		
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
	
	@Override
	public void render(float delta) {
		pausedScreen.draw(0);
		super.render(delta);
	}
	
	@Override
	public void renderClearScreen(float delta) {
		// So we don't clear the screen each frame, we let the pausdScreens .draw() method do that.
	}
	
	private float oldVolume;
	
	@Override
	public void show() {
		super.show();
		oldVolume = MusicHandler.getVolume();
		MusicHandler.setVolume(0.25f);
	}
	
	@Override
	public void hide() {
		super.hide();
		MusicHandler.setVolume(oldVolume);
		dispose();
	}
	
}
