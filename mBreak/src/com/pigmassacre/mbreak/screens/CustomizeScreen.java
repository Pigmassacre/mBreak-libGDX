package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.GridMenu;
import com.pigmassacre.mbreak.gui.ImageItem;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.TextItem;

public class CustomizeScreen extends AbstractScreen {

	Item back;
	
	public CustomizeScreen(MBreak game) {
		super(game);
		
		Menu menu = new Menu();
		traversal.menus.add(menu);
		stage.addActor(menu);
		
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
		textItem.setX(Gdx.graphics.getWidth() / 2f - textItem.getWidth() / 2f);
		textItem.setY(textItem.getHeight() * 2f);
		menu.add(textItem);
		Tween.from(textItem, ActorAccessor.POSITION_Y, 0.5f).target(-textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		GridMenu powerupMenu = new GridMenu(6);
		traversal.menus.add(powerupMenu);
		stage.addActor(powerupMenu);
		ImageItem item = new ImageItem("electricity");
		stage.addActor(item);
		powerupMenu.add(item);
		
		item = new ImageItem("fire");
		stage.addActor(item);
		powerupMenu.add(item);
		
		item = new ImageItem("frost");
		stage.addActor(item);
		powerupMenu.add(item);
		
		powerupMenu.setX(Gdx.graphics.getWidth() / 2f - powerupMenu.getWidth() / 2f);
		powerupMenu.setY(Gdx.graphics.getHeight() / 2f - powerupMenu.getHeight() / 2f);
		powerupMenu.cleanup();
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
		PrepareMenuScreen screen = new PrepareMenuScreen(game);
		screen.back.setSelected(false);
		screen.customize.setSelected(true);
		game.setScreen(screen);
		dispose();
	}
	
	private ShapeRenderer shapeRenderer = new ShapeRenderer();
	
//	@Override
//	public void renderClearScreen(float delta) {
//		// So we don't clear the screen each frame, we let the pausdScreens .draw() method do that.
//		Gdx.gl.glEnable(GL20.GL_BLEND);
//		shapeRenderer.begin(ShapeType.Filled);
//		shapeRenderer.setColor(0f, 0f, 0f, 0.5f);
//		shapeRenderer.rect(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//		shapeRenderer.end();
//		Gdx.gl.glDisable(GL20.GL_BLEND);
//	}
	
}
