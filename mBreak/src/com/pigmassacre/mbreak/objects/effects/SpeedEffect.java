package com.pigmassacre.mbreak.objects.effects;

import com.pigmassacre.mbreak.objects.GameActor;

public class SpeedEffect extends Effect {

	public SpeedEffect(GameActor parent, float duration) {
		super(parent, duration);
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		
		if (parentActor.owner == realOwner) {
			parentActor.act(delta);
		}
	}
	
}
