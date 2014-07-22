package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {

	private static AssetManager assetManager;
	
	public static void loadMenuAssets() {
		if (assetManager == null) {
			assetManager = new AssetManager();
		}
		
		if (!assetManager.isLoaded("images/menutextures.atlas")) {
			assetManager.load("images/menutextures.atlas", TextureAtlas.class);
		}
	}
	
	public static void loadGameAssets() {
		if (assetManager == null) {
			assetManager = new AssetManager();
		}
		
		if (!assetManager.isLoaded("images/gametextures.atlas")) {
			assetManager.load("images/gametextures.atlas", TextureAtlas.class);
		}
		if (!assetManager.isLoaded("sound/ball.ogg")) {
			assetManager.load("sound/ball.ogg", Sound.class);
		}
	}
	
	public static void unloadGameAssets() {
		if (assetManager == null) {
			assetManager = new AssetManager();
		}
		
		if (!assetManager.isLoaded("images/gametextures.atlas")) {
			assetManager.unload("images/gametextures.atlas");
		}
		if (!assetManager.isLoaded("sound/ball.ogg")) {
			assetManager.unload("sound/ball.ogg");
		}
	}
	
	public static AssetManager getAssetManager() {
		return assetManager;
	}
	
	private static TextureAtlas getMenuTextureAtlas() {
		return assetManager.get("images/menutextures.atlas", TextureAtlas.class);
	}
	
	private static TextureAtlas getGameTextureAtlas() {
		return assetManager.get("images/gametextures.atlas", TextureAtlas.class);
	}
	
	public static TextureRegion getTextureRegion(String name) {
		TextureRegion region;
		region = getMenuTextureAtlas().findRegion(name);
		if (region != null) {
			return region;
		}
		region = getGameTextureAtlas().findRegion(name);
		if (region != null) {
			return region;
		}
		return null;
	}

	public static Sound getSound(String name) {
		return assetManager.get(name, Sound.class);
	}
	
}
