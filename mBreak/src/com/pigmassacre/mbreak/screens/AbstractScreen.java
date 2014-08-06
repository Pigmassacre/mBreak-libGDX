package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.GameActorAccessor;
import com.pigmassacre.mbreak.gui.Traversal;
import com.pigmassacre.mbreak.objects.GameActor;

public class AbstractScreen implements Screen {

	protected final MBreak game;
	
	protected Stage stage;
	private TweenManager tweenManager;
	
	private InputMultiplexer inputMultiplexer;
	protected Traversal traversal;
	
	public float timeScale = 1f;
	
	protected String getName() {
		return getClass().getSimpleName();
	}
	
	public AbstractScreen(MBreak game) {
		this.game = game;
		this.stage = new Stage();
		// TODO: Fix viewport bullshit? Might end up getting to scrap GAME_SCALE!!!!!! :D
		this.stage.setViewport(new ScreenViewport());
//		this.stage.setViewport(new FitViewport(Settings.BASE_SCREEN_WIDTH, Settings.BASE_SCREEN_HEIGHT));
//		this.stage.setViewport(new ExtendViewport(Settings.BASE_SCREEN_WIDTH, Settings.BASE_SCREEN_HEIGHT));
		registerTweenAccessor();
		this.traversal = new Traversal(stage.getCamera());
		getInputMultiplexer().addProcessor(traversal);
		getInputMultiplexer().addProcessor(stage);
		registerInputProcessors();
	}
	
	protected void registerInputProcessors() {
		
	}
	
	protected void registerTweenAccessor() {
		Tween.registerAccessor(GameActor.class, new GameActorAccessor());
		Tween.registerAccessor(Actor.class, new ActorAccessor());
	}
	
	public TweenManager getTweenManager() {
		if (tweenManager == null) {
			tweenManager = new TweenManager();
		}
		return tweenManager;
	}
	
	@Override
	public void render(float delta) {
		delta *= timeScale;
		act(delta);
		draw(delta);
	}
	
	public void act(float delta) {
		getTweenManager().update(delta);
		stage.act(delta);
	}
	
	public void draw(float delta) {
		renderClearScreen(delta);
		stage.draw();
		postRender(delta);
	}
	
	protected Color backgroundColor = new Color(0.25f, 0.5f, 0.25f, 1f);
	
	public void renderClearScreen(float delta) {
		Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void postRender(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	public InputMultiplexer getInputMultiplexer() {
		if (inputMultiplexer == null) {
			inputMultiplexer = new InputMultiplexer();
		}
		return inputMultiplexer;
	}
	
	@Override
	public void show() {
		Gdx.app.log(MBreak.LOG, "Showing screen: " + getName());
		Gdx.input.setInputProcessor(getInputMultiplexer());
	}

	@Override
	public void hide() {
		Gdx.app.log(MBreak.LOG, "Hiding screen: " + getName());
	}

	@Override
	public void pause() {
		Gdx.app.log(MBreak.LOG, "Pausing screen: " + getName());
	}

	@Override
	public void resume() {
		Gdx.app.log(MBreak.LOG, "Resuming screen: " + getName());
	}

	@Override
	public void dispose() {
		Gdx.app.log(MBreak.LOG, "Disposing screen: " + getName());
		stage.dispose();
	}

}
