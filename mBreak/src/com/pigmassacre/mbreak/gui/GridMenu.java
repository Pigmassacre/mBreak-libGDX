package com.pigmassacre.mbreak.gui;

import com.pigmassacre.mbreak.Settings;

public class GridMenu extends Menu {

	float offset;
	
	int maxColumnCount;
	
	int currentRowSize;
	float currentRowPosition;
	
	public GridMenu(int maxColumnCount) {
		super();
		
		this.maxColumnCount = maxColumnCount;
		
		offset = 2 * Settings.GAME_SCALE;
		
		currentRowSize = 0;
		currentRowPosition = getY();
	}
	
	public void positionItem(Item item) {
		currentRowSize += 1;
		populateGrid(item);
	}
	
	public void populateGrid(Item item) {
		if (currentRowSize > maxColumnCount) {
			currentRowSize = 1;
			currentRowPosition -= item.getHeight() + offset;
		}
		
		if (currentRowSize > 1) {
			item.setX(getX() + (currentRowSize - 1) * item.getWidth() + (currentRowSize - 1) * offset);
		} else {
			item.setX(getX());
		}
		item.setY(currentRowPosition);
	}
	
	public void cleanup() {
		currentRowSize = 0;
		currentRowPosition = getY();
		
		super.cleanup();
	}
	
}