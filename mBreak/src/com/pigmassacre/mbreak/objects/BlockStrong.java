package com.pigmassacre.mbreak.objects;

import com.badlogic.gdx.graphics.Color;

public class BlockStrong extends Block {

	public BlockStrong(float x, float y, Player owner, Color color) {
		super(x, y, owner, color);
		
		image = getAtlas().findRegion("block_strong");
		
		maxHealth = 30;
		health = maxHealth;
	}
	
}
