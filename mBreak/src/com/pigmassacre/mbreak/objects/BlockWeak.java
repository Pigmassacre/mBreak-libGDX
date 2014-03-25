package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;

public class BlockWeak extends Block {

	public BlockWeak(float x, float y, Player owner, Color color) {
		super(x, y, owner, color);
		
		image = getAtlas().findRegion("block_weak");
		
		maxHealth = 10;
		health = maxHealth;
	}

}
