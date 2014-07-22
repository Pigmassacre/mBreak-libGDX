package com.pigmassacre.mbreak.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.pigmassacre.mbreak.MBreak;
import com.pigmassacre.mbreak.gui.ActorAccessor;
import com.pigmassacre.mbreak.gui.Traversal;

public class AbstractScreen implements Screen {

	protected final MBreak game;
	
	protected Stage stage;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private TweenManager tweenManager;
	
	private InputMultiplexer inputMultiplexer;
	protected Traversal traversal;
	
	protected String getName() {
		return getClass().getSimpleName();
	}
	
	public AbstractScreen(MBreak game) {
		this.game = game;
		this.stage = new Stage();
		registerTweenAccessor();
		this.traversal = new Traversal(stage.getCamera());
	}
	
	protected void registerTweenAccessor() {
		Tween.registerAccessor(Actor.class, new ActorAccessor());
	}

	public SpriteBatch getBatch() {
		if (batch == null)
			batch = new SpriteBatch();
		return batch;
	}
	
	public TextureAtlas getAtlas() {
		if (atlas == null)
			atlas = new TextureAtlas(Gdx.files.internal("images/packedtextures.atlas"));
		return atlas;
	}
	
	public TweenManager getTweenManager() {
		if (tweenManager == null)
			tweenManager = new TweenManager();
		return tweenManager;
	}
	
	@Override
	public void render(float delta) {
		stage.act(delta);
		
		getTweenManager().update(delta);
		
		renderClearScreen(delta);
        renderBackground(delta);
        
        stage.draw();
	}
	
	public void renderClearScreen(float delta) {
		Gdx.gl.glClearColor(1f, 0.5f, 0.25f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}
	
	public void renderBackground(float delta) {
		
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	public InputMultiplexer getInputMultiplexer() {
		if (inputMultiplexer == null)
			inputMultiplexer = new InputMultiplexer();
		return inputMultiplexer;
	}
	
	@Override
	public void show() {
		getInputMultiplexer().addProcessor(traversal);
		getInputMultiplexer().addProcessor(stage);
		Gdx.input.setInputProcessor(getInputMultiplexer());
	}

	@Override
	public void hide() {
		Gdx.app.log(MBreak.LOG, "Hiding screen: " + getName());
		dispose();
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
		if (batch != null)
			batch.dispose();
		if (atlas != null)
			atlas.dispose();
	}

}
