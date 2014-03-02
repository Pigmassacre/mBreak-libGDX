package com.pigmassacre.mbreak;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;

public class AbstractScreen implements Screen {

	protected final MBreak game;
	
	protected Stage stage;
	private SpriteBatch batch;
	private TextureAtlas atlas;
	private TweenManager tweenManager;
	
	protected String getName() {
		return getClass().getSimpleName();
	}
	
	public AbstractScreen(MBreak game) {
		this.game = game;
		this.stage = new Stage();
		Tween.registerAccessor(Widget.class, new WidgetAccessor());
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
		
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		stage.setViewport(width, height, true);
	}

	@Override
	public void show() {
		InputMultiplexer inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(new Traversal(stage.getCamera()));
		inputMultiplexer.addProcessor(stage);
		Gdx.input.setInputProcessor(inputMultiplexer);
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
