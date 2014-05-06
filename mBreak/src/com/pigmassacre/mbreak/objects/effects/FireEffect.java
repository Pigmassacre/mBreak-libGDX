package com.pigmassacre.mbreak.objects.effects;

import com.pigmassacre.mbreak.objects.Block;
import com.pigmassacre.mbreak.objects.GameActor;

public class FireEffect extends Effect {

	private float damagePerSecond = 2f;
	
	public FireEffect(FireEffect fireEffect) {
		super(fireEffect);
	}
	
	public FireEffect(GameActor parent, float duration) {
		super(parent, duration);
	}

	@Override
	public void onHitBlock(Block block) {
		if (block.owner != owner) {
			new FireEffect(block, duration - stateTime);
		}
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		if (parentActor instanceof Block) {
			((Block) parentActor).damage(damagePerSecond* delta);
		}
	}
	
}
