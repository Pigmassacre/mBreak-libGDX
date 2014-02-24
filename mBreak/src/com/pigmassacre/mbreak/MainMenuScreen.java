package com.pigmassacre.mbreak;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class MainMenuScreen extends AbstractScreen {

	Table menuTable;
	
	public MainMenuScreen(MBreak game) {
		super(game);

		Table table = new Table();
		table.setFillParent(true);
		stage.addActor(table);
		
		menuTable = new Table();
		
		RectItem rectItem;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				rectItem = new RectItem();
				menuTable.add(rectItem).padLeft(rectItem.getWidth() + 2 * Settings.GAME_SCALE).padBottom(rectItem.getWidth() + 2 * Settings.GAME_SCALE);
			}
			menuTable.row();
		}
		table.add(menuTable);

	}
	
	@Override
	public void render(float delta) {
//		Random random = new Random();
//		menuTable.setX(random.nextFloat() * 10);
		super.render(delta);
		Table.drawDebug(stage);
	}

}
