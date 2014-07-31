package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Timeline;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenEquations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.MusicHandler;
import com.pigmassacre.mbreak.Settings;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.GridMenu;
import com.pigmassacre.mbreak.gui.Item;
import com.pigmassacre.mbreak.gui.Item.ItemCallback;
import com.pigmassacre.mbreak.gui.ListMenu;
import com.pigmassacre.mbreak.gui.Logo;
import com.pigmassacre.mbreak.gui.Menu;
import com.pigmassacre.mbreak.gui.RectItem;
import com.pigmassacre.mbreak.gui.Sunrays;
import com.pigmassacre.mbreak.gui.TextItem;
import com.pigmassacre.mbreak.gui.TweenHelp;

public class PrepareMenuScreen extends AbstractScreen {

	Logo logo;
	Sunrays sunrays;
	
	Color leftColor, rightColor;
	
	GridMenu leftColorMenu, rightColorMenu;
	Item back, start;
	
	public PrepareMenuScreen(MBreak game) {
		this(game, null, null);
	}
	
	public PrepareMenuScreen(MBreak game, Logo givenLogo, Sunrays givenSunrays) {
		super(game);
		
		if (givenSunrays == null) {
			sunrays = new Sunrays();
		} else {
			sunrays = givenSunrays;
		}
		stage.addActor(sunrays);
		
		if (givenLogo == null) {
			logo = new Logo();
			logo.setX((Gdx.graphics.getWidth() - logo.getWidth()) / 2);
			logo.setY(Gdx.graphics.getHeight() + logo.getHeight());
		} else {
			logo = givenLogo;
		}
		stage.addActor(logo);
		Tween.to(logo, ActorAccessor.POSITION_XY, 0.5f).target(logo.getX(), (Gdx.graphics.getHeight() / 2) + logo.getHeight() * 0.75f)
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		sunrays.attachTo(logo, 0, -logo.getHeight() / 6);

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
		menu.setX(textItem.getHeight() + textItem.getWidth() / 2);
		menu.setY(textItem.getHeight());
		menu.add(textItem);
		traversal.menus.add(menu);
		stage.addActor(menu);
		Tween.from(textItem, ActorAccessor.POSITION_XY, 0.5f).target(-textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		textItem = new TextItem("Start");
		start = textItem;
		textItem.setCallback(new ItemCallback() {

			@Override
			public void execute(Item data) {
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
		Tween.from(textItem, ActorAccessor.POSITION_XY, 0.5f).target(Gdx.graphics.getWidth() + textItem.getWidth(), -textItem.getHeight())
			.ease(TweenEquations.easeOutExpo)
			.start(getTweenManager());
		
		GridMenu colorMenu = new GridMenu(3);
		leftColorMenu = colorMenu;
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
			TweenHelp.setupSingleItemTweenFrom(item, getTweenManager(), TweenEquations.easeOutExpo, 0.5f, true, false, true, true);
			item.setCallback(new ItemCallback() {
				
				@Override
				public void execute(Item data) {
					if (!data.getDisabled()) {
						leftColor = data.getColor();

						data.setChosen(!data.getChosen());
						
						for (Item item : leftColorMenu.items) {
							if (!item.equals(data)) {
								item.setChosen(false);
							}
						}
						
						for (Item item : rightColorMenu.items) {
							item.setDisabled(false);
						}
						
						for (Item item : rightColorMenu.items) {
							if (item.getColor().equals(data.getColor())) {
								item.setDisabled(data.getChosen());
							}
						}
					}
				}
				
			});
		}
		
		traversal.menus.add(colorMenu);
		stage.addActor(colorMenu);
		
		colorMenu = new GridMenu(3);
		rightColorMenu = colorMenu;
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 0.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 0.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 0.0f, 1.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 1.0f, 0.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorItem = new RectItem();
		colorItem.setColor(1.0f, 0.0f, 1.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorItem = new RectItem();
		colorItem.setColor(0.0f, 1.0f, 1.0f, 1.0f);
		colorMenu.add(colorItem);
		stage.addActor(colorItem);
		
		colorMenu.setX(Gdx.graphics.getWidth() - 2 * colorMenu.getWidth());
		colorMenu.setY(colorMenu.getHeight() * 2);
		colorMenu.cleanup();
		
		for (Item item : colorMenu.items) {
			TweenHelp.setupSingleItemTweenFrom(item, getTweenManager(), TweenEquations.easeOutExpo, 0.5f, false, true, true, true);
			item.setCallback(new ItemCallback() {
				
				@Override
				public void execute(Item data) {
					if (!data.getDisabled()) {
						rightColor = data.getColor();

						data.setChosen(!data.getChosen());
						
						for (Item item : rightColorMenu.items) {
							if (!item.equals(data)) {
								item.setChosen(false);
							}
						}
						
						for (Item item : leftColorMenu.items) {
							item.setDisabled(false);
						}
						
						for (Item item : leftColorMenu.items) {
							if (item.getColor().equals(data.getColor())) {
								item.setDisabled(data.getChosen());
							}
						}
					}
				}
				
			});
		}
		
		traversal.menus.add(colorMenu);
		stage.addActor(colorMenu);
		
		if (!MusicHandler.isPlaying()) {
			MusicHandler.setSong("music/title/goluigi-nonuniform.ogg");
			MusicHandler.setLooping(true);
			MusicHandler.play();
		}
	}
	
	protected void registerInputProcessors() {
		getInputMultiplexer().addProcessor(new InputAdapter() {
			
			@Override
			public boolean keyDown(int keycode) {
				switch(keycode) {
				case Keys.ESCAPE:
				case Keys.BACK:
					if (!startingGame) {
						back();
					}
					break;
				}
				return false;
			}
			
		});
	};
	
	public void back() {
		game.setScreen(new MainMenuScreen(game, logo, sunrays));
		dispose();
	}
	
	// For the ugly hack below.
	private boolean finished = false, startingGame = false;;
	
	public void start() {
		if (leftColor != null && rightColor != null) {
			getTweenManager().killAll();
			
			Timeline timeline = Timeline.createSequence();
			
			timeline.beginParallel();
			
			for (Item item : leftColorMenu.items) {
				item.setCallback(null);
				timeline.push(TweenHelp.setupSingleItemTweenTo(item, getTweenManager(), TweenEquations.easeInExpo, 0.5f, true, false, true, true));
			}
			
			for (Item item : rightColorMenu.items) {
				item.setCallback(null);
				timeline.push(TweenHelp.setupSingleItemTweenTo(item, getTweenManager(), TweenEquations.easeInExpo, 0.5f, false, true, true, true));
			}
			
			back.setCallback(null);
			start.setCallback(null);
			
			timeline.push(Tween.to(back, ActorAccessor.POSITION_XY, 0.5f).target(-back.getWidth(), -back.getHeight())
				.ease(TweenEquations.easeInExpo));
			
			timeline.push(Tween.to(start, ActorAccessor.POSITION_XY, 0.5f).target(Gdx.graphics.getWidth() + start.getWidth(), -start.getHeight())
				.ease(TweenEquations.easeInExpo));
			
			timeline.end();
			
			timeline.push(Tween.to(logo, ActorAccessor.POSITION_Y, 1f)
						.target((Gdx.graphics.getHeight() - logo.getHeight()) / 2 - sunrays.offsetY + Settings.getLevelYOffset())
						.ease(TweenEquations.easeInOutExpo));
			
			timeline.setCallback(new TweenCallback() {
				
				@Override
				public void onEvent(int type, BaseTween<?> source) {
					finished = true;
				}
				
			});
			
			startingGame = true;
			timeline.start(getTweenManager());
		} else {
			game.setScreen(new ToastScreen(game, this, "You must choose colors for both players before you can start the game."));
		}
	}
	
	@Override
	public void postRender(float delta) {
		// This is an ugly hack in order to not display one empty frame in between this screen and the next screen.
		if (finished) {
			dispose();
			game.setScreen(new GameLoadingScreen(game, logo, sunrays, leftColor, rightColor));
		}
	}
	
}
