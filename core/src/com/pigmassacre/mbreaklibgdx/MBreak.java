package com.pigmassacre.mbreaklibgdx;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.pigmassacre.mbreaklibgdx.screens.AbstractScreen;
import com.pigmassacre.mbreaklibgdx.screens.IntroLoadingScreen;

public class MBreak extends Game {

	public static final String LOG = "mBreak";
	
	public SpriteBatch spriteBatch;
	
	private FPSLogger fpsLogger;
	
	public MBreak() {
	}

	@Override
	public void create() {
		Gdx.app.log(MBreak.LOG, "Creating game on " + Gdx.app.getType());
		spriteBatch = new SpriteBatch();
		fpsLogger = new FPSLogger();
	}

	@Override
	public void resize(int width, int height) {
		Gdx.app.log(MBreak.LOG, "Resizing game to: " + width + " x " + height);
		super.resize(width, height);
		
		Gdx.input.setCatchBackKey(true);
		if (getScreen() == null) {
			setScreen(new IntroLoadingScreen(this));
		}
	}

	@Override
	public void render() {
		if (getScreen() != null) {
			AbstractScreen screen = (AbstractScreen) getScreen();
			renderToTexture(screen.stage.getBatch());
			if (fboRegion != null) {
				AbstractScreen.lastTextureRegion = new TextureRegion(fboRegion);
			}
		}
		
		if (Settings.getDebugMode()) {
			fpsLogger.log();
		}
	}

	private float fboScaler = 1f;
	private boolean fboEnabled = true;
	private FrameBuffer fbo = null;
	protected TextureRegion fboRegion = null;

	public void renderToTexture(Batch spriteBatch) {
	    int width = Gdx.graphics.getWidth();
	    int height = Gdx.graphics.getHeight();

	    if (fboEnabled) { 
	        if(fbo == null) {	
	        	fbo = new FrameBuffer(Format.RGB565, (int)(width * fboScaler), (int)(height * fboScaler), false);
	            fboRegion = new TextureRegion(fbo.getColorBufferTexture());
	            fboRegion.flip(false, true);
	        }
	        fbo.begin();
	    }
	    
	    super.render();

	    if (fbo != null) {
	        fbo.end();

	        spriteBatch.begin();         
	        spriteBatch.draw(fboRegion, 0, 0, width, height);  
	        spriteBatch.end();
	    }   
	}
	
	@Override
	public void pause() {
		super.pause();
		Gdx.app.log(MBreak.LOG, "Pausing game");
	}

	@Override
	public void resume() {
		super.resume();
		Gdx.app.log(MBreak.LOG, "Resuming game");
	}

	@Override
	public void dispose() {
		super.dispose();
		Gdx.app.log(MBreak.LOG, "Disposing game");
	}

}
