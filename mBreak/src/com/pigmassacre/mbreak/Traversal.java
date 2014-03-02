package com.pigmassacre.mbreak;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;

public class Traversal implements InputProcessor {
	
	public static List<Menu> menus;
	private Camera camera;
	
	public Traversal(Camera camera) {
		this.camera = camera;
		if (menus == null)
			menus = new ArrayList<Menu>();
	}

	@Override
	public boolean keyDown(int keycode) {
		switch(keycode) {
		case Keys.LEFT:
			traverseSideways(true);
			break;
		case Keys.RIGHT:
			traverseSideways(false);
			break;
		case Keys.UP:
			traverseUpAndDown(true);
			break;
		case Keys.DOWN:
			traverseUpAndDown(false);
			break;
		case Keys.ENTER:
			for (Menu menu : menus) {
				for (Item item : menu.items) {
					if (item.selected)
						item.executeFunction();
				}
			}
			break;
		}
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		selectItems(screenX, screenY);
		Vector3 coords = new Vector3(screenX, screenY, 0);
		camera.unproject(coords);
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				if (item.isPointerOverItem(coords.x, coords.y) && item.selected) {
					item.executeFunction();
				}
			}
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		selectItems(screenX, screenY);
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		selectItems(screenX, screenY);
		return false;
	}

	private void selectItems(int screenX, int screenY) {
		Vector3 coords = new Vector3(screenX, screenY, 0);
		camera.unproject(coords);
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				if (item.isPointerOverItem(coords.x, coords.y)) {
					for (Item otherItem : menu.items) {
						otherItem.selected = false;
					}
					for (Menu otherMenu : menu.otherMenus) {
						for (Item anotherItem : otherMenu.items) {
							anotherItem.selected = false;
						}
					}
					item.selected = true;
				}
			}
		}
	}
	
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}	

	public void traverseSideways(boolean left) {
		Item selectedItem = getSelectedItem();
		List<Item> possibleItems = fillListOfPossibleItems();
		List<Item> temp;
		
		temp = new ArrayList<Item>();
		if (left) {
			for (Item item : possibleItems) {
				if (item.getX() + (item.getWidth() / 2) < (selectedItem.getX() + (selectedItem.getWidth() / 2))) {
					temp.add(item);
				}
			}
		} else {
			for (Item item : possibleItems) {
				if (item.getX() + (item.getWidth() / 2) > (selectedItem.getX() + (selectedItem.getWidth() / 2))) {
					temp.add(item);
				}
			}
		}
		possibleItems = temp;
		
		List<Item> sameMenuItems = new ArrayList<Item>();
		for (Menu menu : menus) {
			if (menu.items.contains(selectedItem)) {
				for (Item item : menu.items) {
					if (possibleItems.contains(item)) {
						sameMenuItems.add(item);
					}
				}
			}
		}
		possibleItems = sameMenuItems;
		
		if (!possibleItems.isEmpty()) {
			float difference = 0f;
			
			float leastDifferenceY = Float.MAX_VALUE;
			for (Item item : possibleItems) {
				difference = Math.abs(item.getY() - (item.getHeight() / 2)) - (selectedItem.getY() - (selectedItem.getHeight() / 2));
				if (difference < leastDifferenceY)
					leastDifferenceY = difference;
			}
			
			temp = new ArrayList<Item>();
			for (Item item : possibleItems) {
				if (Math.abs(item.getY() - (item.getHeight() / 2)) - (selectedItem.getY() - (selectedItem.getHeight() / 2)) == leastDifferenceY)
					temp.add(item);
			}
			possibleItems = temp;
			
			float leastDifferenceX = Float.MAX_VALUE;
			difference = 0f;
			for (Item item : possibleItems) {
				difference = Math.abs(item.getX() + (item.getWidth() / 2) - (selectedItem.getX() + (selectedItem.getWidth() / 2)));
				if (difference < leastDifferenceX)
					leastDifferenceX = difference;
			}
			
			temp = new ArrayList<Item>();
			for (Item item : possibleItems) {
				if (Math.abs(item.getX() + (item.getWidth() / 2) - (selectedItem.getX() + (selectedItem.getWidth() / 2))) == leastDifferenceX)
					temp.add(item);
			}
			possibleItems = temp;
			
			selectedItem.selected = false;
			possibleItems.get(0).selected = true;
		}
	}
	
	public void traverseUpAndDown(boolean up) {
		Item selectedItem = getSelectedItem();
		List<Item> possibleItems = fillListOfPossibleItems();
		List<Item> temp;
		
		temp = new ArrayList<Item>();
		if (up) {
			for (Item item : possibleItems) {
				if (item.getY() - (item.getHeight() / 2) > (selectedItem.getY() - (selectedItem.getHeight() / 2))) {
					temp.add(item);
				}
			}
		} else {
			for (Item item : possibleItems) {
				if (item.getY() - (item.getHeight() / 2) < (selectedItem.getY() - (selectedItem.getHeight() / 2))) {
					temp.add(item);
				}
			}
		}
		possibleItems = temp;
		System.out.println("first check: " + possibleItems);
		
		List<Item> sameMenuItems = new ArrayList<Item>();
		for (Menu menu : menus) {
			if (menu.items.contains(selectedItem)) {
				for (Item item : menu.items) {
					if (possibleItems.contains(item)) {
						sameMenuItems.add(item);
					}
				}
			}
		}
		possibleItems = sameMenuItems;
		
		if (!possibleItems.isEmpty()) {
			float difference = 0f;
			
			float leastDifferenceX = Float.MAX_VALUE;
			for (Item item : possibleItems) {
				difference = Math.abs(item.getX() + (item.getWidth() / 2)) - (selectedItem.getX() + (selectedItem.getWidth() / 2));
				if (difference < leastDifferenceX)
					leastDifferenceX = difference;
			}
			
			temp = new ArrayList<Item>();
			for (Item item : possibleItems) {
				if (Math.abs(item.getX() + (item.getWidth() / 2)) - (selectedItem.getX() + (selectedItem.getWidth() / 2)) == leastDifferenceX)
					temp.add(item);
			}
			possibleItems = temp;
			System.out.println("second check: " + possibleItems);
			
			float leastDifferenceY = Float.MAX_VALUE;
			difference = 0f;
			for (Item item : possibleItems) {
				difference = Math.abs(item.getY() - (item.getHeight() / 2) - (selectedItem.getY() - (selectedItem.getHeight() / 2)));
				System.out.println(difference);
				if (difference < leastDifferenceY)
					leastDifferenceY = difference;
			}
			
			temp = new ArrayList<Item>();
			for (Item item : possibleItems) {
				if (Math.abs(item.getY() - (item.getHeight() / 2) - (selectedItem.getY() - (selectedItem.getHeight() / 2))) == leastDifferenceY)
					temp.add(item);
			}
			possibleItems = temp;
			System.out.println("last check: " + possibleItems);
			
			selectedItem.selected = false;
			possibleItems.get(0).selected = true;
		}
	}
	
	public Item getSelectedItem() {
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				if (item.selected)
					return item;
			}
		}
		return null;
	}
	
	public List<Item> fillListOfPossibleItems() {
		List<Item> possibleItems = new ArrayList<Item>();
		for (Menu menu : menus) {
			for (Item item : menu.items) {
				possibleItems.add(item);
			}
		}
		return possibleItems;
	}
//	def fill_list_of_possible(list_of_menus):
//		"""
//		Returns a list that contains all the items in all the menus in list_of_menus.
//		"""
//		list_of_possible = []
//		for a_menu in list_of_menus:
//			for item in a_menu.items:
//				list_of_possible.append(item)
//		return list_of_possible
	
}
